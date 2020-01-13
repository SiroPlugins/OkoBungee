package com.github.siroshun09.okobungee;

import com.github.siroshun09.sirolibrary.config.BungeeConfig;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class Configuration extends BungeeConfig {
    private static Configuration instance;

    private Configuration() {
        super(OkoBungee.get(), "config.yml", true);
        instance = this;
    }

    public static Configuration get() {
        if (instance == null) {
            new Configuration();
        }
        return instance;
    }

    @NotNull
    public String getOnlyPlayerMsg() {
        return getString("SlashServer.messages.onlyPlayer", "* このコマンドはプレイヤーのみ実行できます。");
    }

    @NotNull
    public String getAlreadyConnectedMsg() {
        return getString("SlashServer.messages.alreadyConnected", "&7* &cすでに接続しています。");
    }

    @NotNull
    public String getMovingMsg(@NotNull ServerInfo server) {
        return getString("SlashServer.messages.moving", "&7* &b%server%&7 へ移動します。").replace("%server%", server.getName());
    }

    @NotNull
    public String getCannotMoveMsg(@NotNull ServerInfo server) {
        return getString("SlashServer.messages.cannotMove", "&7* &c%server%&7 に移動できませんでした。").replace("%server%", server.getName());
    }

    @NotNull
    public String getSendToServerName() {
        return getString("BungeeKick.sendTo", "hub");
    }

    @NotNull
    public String getKickMsg(@NotNull ServerInfo from, @NotNull StringBuilder reason) {
        return getString("BungeeKick.message", "&c* &b%from%&7 から kick されました: &f%reason%")
                .replace("%from%", from.getName()).replace("%reason%", reason.toString());
    }

    @NotNull
    public String getServerConnectedMsg(@NotNull ProxiedPlayer player) {
        return getString("ServerConnectMsg", "&6* &r%player%&6 がおこ鯖にログインしました。").replace("%player%", player.getName());
    }

    @NotNull
    public String getServerDisconnectedMsg(@NotNull ProxiedPlayer player) {
        return getString("ServerDisconnectMsg", "&7* &r%player%&7 がおこ鯖からログアウトしました。").replace("%player%", player.getName());
    }

    @NotNull
    public String getServerSwitchMsg(@NotNull ProxiedPlayer player, @NotNull ServerInfo to) {
        return getString("ServerSwitchMsg", "&7* &b%player%&7 が &b%server%&7 へ移動しました。")
                .replace("%player%", player.getName()).replace("%server%", to.getName());
    }

    @NotNull
    public String getZihouMsg() {
        LocalTime time = LocalTime.now();
        return getString("ZihouMessage", "&8[&9時報&8] &7%h時%m分%s秒になりました。")
                .replace("%h", String.valueOf(time.getHour()))
                .replace("%m", String.valueOf(time.getMinute()))
                .replace("%s", String.valueOf(time.getSecond()));
    }
}
