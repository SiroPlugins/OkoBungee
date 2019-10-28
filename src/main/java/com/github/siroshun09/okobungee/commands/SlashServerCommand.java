package com.github.siroshun09.okobungee.commands;

import com.github.siroshun09.okobungee.OkoBungee;
import com.github.siroshun09.okobungee.utils.MessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.jetbrains.annotations.NotNull;

public class SlashServerCommand extends Command {
    private final ServerInfo server;

    public SlashServerCommand(@NotNull ServerInfo server) {
        super(server.getName());
        this.server = server;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            MessageUtils.send(sender, OkoBungee.getInstance().getConfig().getConfiguration()
                    .getString("SlashServer.messages.onlyPlayer", "* このコマンドはプレイヤーのみ実行できます。"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (player.getServer().getInfo().getName().equalsIgnoreCase(server.getName())) {
            MessageUtils.send(player, OkoBungee.getInstance().getConfig().getConfiguration()
                    .getString("SlashServer.messages.alreadyConnected", "&7* &cすでに接続しています。"));
            return;
        }

        MessageUtils.send(player, OkoBungee.getInstance().getConfig().getConfiguration()
                .getString("SlashServer.messages.moving", "&7* &b%server%&7 へ移動します。").replace("%server%", server.getName()));
        player.connect(server);
    }
}
