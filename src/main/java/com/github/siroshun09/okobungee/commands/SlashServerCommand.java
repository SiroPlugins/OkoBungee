package com.github.siroshun09.okobungee.commands;

import com.github.siroshun09.okobungee.Configuration;
import com.github.siroshun09.sirolibrary.message.BungeeMessage;
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
            BungeeMessage.sendMessageWithColor(sender, Configuration.get().getOnlyPlayerMsg());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (player.getServer().getInfo().getName().equalsIgnoreCase(server.getName())) {
            BungeeMessage.sendMessageWithColor(player, Configuration.get().getAlreadyConnectedMsg());
            return;
        }

        if (!server.canAccess(player)) {
            BungeeMessage.sendMessageWithColor(player, Configuration.get().getCannotMoveMsg(server));
            return;
        }

        BungeeMessage.sendMessageWithColor(player, Configuration.get().getMovingMsg(server));
        player.connect(server);
    }
}
