package com.github.siroshun09.okobungee.listeners;

import com.github.siroshun09.okobungee.Configuration;
import com.github.siroshun09.sirolibrary.message.BungeeMessage;
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
    private static ServerListener instance;
    private final StringBuilder reasonBuilder = new StringBuilder();
    private final List<ProxiedPlayer> ignoreList = new ArrayList<>();

    private ServerListener() {
        instance = this;
    }

    public static ServerListener get() {
        if (instance == null) {
            new ServerListener();
        }
        return instance;
    }

    @EventHandler
    public void onKick(@NotNull ServerKickEvent e) {
        ServerInfo from = e.getKickedFrom();
        if (from == null) {
            from = ProxyServer.getInstance().getReconnectHandler().getServer(e.getPlayer());
        }

        ServerInfo to = ProxyServer.getInstance().getServerInfo(Configuration.get().getSendToServerName());
        if (to == null || to.equals(from)) {
            return;
        }

        e.setCancelled(true);
        e.setCancelServer(to);

        reasonBuilder.setLength(0);
        for (BaseComponent c : e.getKickReasonComponent()) {
            reasonBuilder.append(c.toLegacyText());
        }

        BungeeMessage.sendMessageWithColor(e.getPlayer(), Configuration.get().getKickMsg(from, reasonBuilder));
    }

    @EventHandler
    public void onLogin(@NotNull PostLoginEvent e) {
        BungeeMessage.broadcastWithColor(Configuration.get().getServerConnectedMsg(e.getPlayer()));
        ignoreList.add(e.getPlayer());
    }

    @EventHandler
    public void onDisconnect(@NotNull ServerDisconnectEvent e) {
        if (e.getPlayer().getServer() == null) {
            ignoreList.remove(e.getPlayer());
            BungeeMessage.broadcastWithColor(Configuration.get().getServerDisconnectedMsg(e.getPlayer()));
        }
    }

    @EventHandler
    public void onSwitch(@NotNull ServerSwitchEvent e) {
        if (ignoreList.contains(e.getPlayer())) {
            ignoreList.remove(e.getPlayer());
            return;
        }
        BungeeMessage.broadcastWithColor(Configuration.get().getServerSwitchMsg(e.getPlayer(), e.getPlayer().getServer().getInfo()));
    }
}
