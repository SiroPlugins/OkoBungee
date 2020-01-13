package com.github.siroshun09.okobungee.commands;

import com.github.siroshun09.okobungee.Configuration;
import com.github.siroshun09.sirolibrary.message.BungeeMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.jetbrains.annotations.NotNull;

public class OkoBungeeCommand extends Command {
    private static OkoBungeeCommand instance;

    private OkoBungeeCommand() {
        super("okobungee", null, "ob");
        instance = this;
    }

    public static OkoBungeeCommand get() {
        if (instance == null) {
            new OkoBungeeCommand();
        }
        return instance;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (1 <= args.length && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("okobungee.reload")) {
                BungeeMessage.sendMessageWithColor(sender, "&8[&6OkoBungee&8] &cそのコマンドを実行する権限がありません。");
                return;
            }
            Configuration.get().reload();
            BungeeMessage.sendMessageWithColor(sender, "&8[&6OkoBungee&8] &7config.yml を再読み込みしました。");
            return;
        }

        BungeeMessage.sendMessageWithColor(sender, "&b/ob reload &8- &7config を再読み込みします。");
    }
}
