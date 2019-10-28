package com.github.siroshun09.okobungee.listeners;

import com.github.siroshun09.okobungee.OkoBungee;
import com.github.siroshun09.okobungee.utils.MessageUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServerListener implements Listener {
    private final StringBuilder reasonBuilder = new StringBuilder();
    private final List<ProxiedPlayer> ignoreList = new ArrayList<>();

    @EventHandler
    public void onKick(@NotNull ServerKickEvent e) {
        ServerInfo from = e.getKickedFrom();

        if (from == null) from = ProxyServer.getInstance().getReconnectHandler().getServer(e.getPlayer());

        ServerInfo to = ProxyServer.getInstance().getServerInfo(
                OkoBungee.getInstance().getConfig().getConfiguration().getString("BungeeKick.sendTo"));

        if (to == null || to.equals(from)) return;

        e.setCancelled(true);
        e.setCancelServer(to);

        reasonBuilder.setLength(0);
        for (BaseComponent c : e.getKickReasonComponent()) reasonBuilder.append(c.toLegacyText());

        MessageUtils.send(e.getPlayer(), OkoBungee.getInstance().getConfig().getConfiguration()
                .getString("BungeeKick.message").replace("%from%", from.getName()).replace("%reason%", reasonBuilder.toString()));
    }

    @EventHandler
    public void onLogin(@NotNull PostLoginEvent e) {
        MessageUtils.broadcast(OkoBungee.getInstance().getConfig().getConfiguration()
                .getString("ServerConnectMsg", "&6* &r%player%&6 がおこ鯖にログインしました。")
                .replace("%player%", e.getPlayer().getName()));
        ignoreList.add(e.getPlayer());
    }

    @EventHandler
    public void onDisconnect(@NotNull ServerDisconnectEvent e) {
        if (e.getPlayer().getServer() == null) {
            ignoreList.remove(e.getPlayer());
            MessageUtils.broadcast(OkoBungee.getInstance().getConfig().getConfiguration()
                    .getString("ServerDisconnectMsg", "&7* &r%player%&7 がおこ鯖からログアウトしました。")
                    .replace("%player%", e.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onSwitch(@NotNull ServerSwitchEvent e) {
        if (ignoreList.contains(e.getPlayer())) {
            ignoreList.remove(e.getPlayer());
            return;
        }

        MessageUtils.broadcast(OkoBungee.getInstance().getConfig().getConfiguration()
                .getString("ServerSwitchMsg", "&7* &b%player%&7 が &b%server%&7 へ移動しました。")
                .replace("%player%", e.getPlayer().getName()).replace("%server%", e.getPlayer().getServer().getInfo().getName()));
    }
}
