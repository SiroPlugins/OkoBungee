package com.github.siroshun09.okobungee.commands;

import com.github.siroshun09.okobungee.OkoBungee;
import com.github.siroshun09.okobungee.utils.MessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.jetbrains.annotations.NotNull;

public class OkoBungeeCommand extends Command {
    public OkoBungeeCommand() {
        super("okobungee", null, "ob");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            MessageUtils.send(sender, "&b/ob reload &8- &7config を再読み込みします。");
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) reload(sender);

    }

    private void reload(@NotNull CommandSender sender) {
        if (!sender.hasPermission("okobungee.reload")) return;

        OkoBungee.getInstance().getConfig().reload();
        MessageUtils.send(sender, "&8[&6OkoBungee&8] &7config.yml を再読み込みしました。");
    }
}
