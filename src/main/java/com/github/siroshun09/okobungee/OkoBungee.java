package com.github.siroshun09.okobungee;

import com.github.siroshun09.okobungee.commands.OkoBungeeCommand;
import com.github.siroshun09.okobungee.commands.SlashServerCommand;
import com.github.siroshun09.okobungee.listeners.ServerListener;
import com.github.siroshun09.okobungee.zihou.Zihou;
import com.github.siroshun09.sirolibrary.bungeeutils.BungeeUtil;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class OkoBungee extends Plugin {
    private static OkoBungee instance;

    public OkoBungee() {
        super();
        instance = this;
    }

    public static OkoBungee get() {
        return instance;
    }

    @Override
    public void onEnable() {
        Zihou.get();
        BungeeUtil.registerListener(this, ServerListener.get());
        BungeeUtil.registerCommand(this, OkoBungeeCommand.get());
        for (ServerInfo server : getProxy().getServers().values()) {
            BungeeUtil.registerCommand(this, new SlashServerCommand(server));
        }
    }

    @Override
    public void onDisable() {
        Zihou.get().stop();
        BungeeUtil.unregisterCommands(this);
        BungeeUtil.unregisterListeners(this);
    }
}
