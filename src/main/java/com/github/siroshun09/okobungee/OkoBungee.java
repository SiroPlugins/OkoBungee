package com.github.siroshun09.okobungee;

import com.github.siroshun09.okobungee.commands.OkoBungeeCommand;
import com.github.siroshun09.okobungee.commands.SlashServerCommand;
import com.github.siroshun09.okobungee.listeners.ServerListener;
import com.github.siroshun09.okobungee.zihou.Zihou;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class OkoBungee extends Plugin {
    private static OkoBungee instance;
    private Config config;
    private Zihou zihou;

    public static OkoBungee getInstance() {
        if (instance == null)
            instance = (OkoBungee) ProxyServer.getInstance().getPluginManager().getPlugin("OkoBungee");
        return instance;
    }

    @Override
    public void onLoad() {
        config = new Config(this, "config.yml", true);
    }

    @Override
    public void onEnable() {
        zihou = new Zihou();

        getProxy().getPluginManager().registerListener(this, new ServerListener());
        getProxy().getPluginManager().registerCommand(this, new OkoBungeeCommand());
        getProxy().getServers().values().forEach(
                s -> getProxy().getPluginManager().registerCommand(this, new SlashServerCommand(s)));
    }

    @Override
    public void onDisable() {
        zihou.stop();
        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().getPluginManager().unregisterCommands(this);
    }

    public Config getConfig() {
        return config;
    }
}
