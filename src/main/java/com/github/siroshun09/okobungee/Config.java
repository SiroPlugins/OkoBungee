package com.github.siroshun09.okobungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private final Path file;
    private final String fileName;
    private final boolean fromResource;
    private Configuration configuration;

    Config(@NotNull OkoBungee plugin, @NotNull String fileName, boolean fromResource) {
        this.file = plugin.getDataFolder().toPath().resolve(fileName);
        this.fileName = fileName;
        this.fromResource = fromResource;
        reload();
    }

    public void reload() {
        try {
            checkFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OkoBungee.getInstance().getLogger().info(fileName + " をロードしました。");
    }

    private void checkFile() throws IOException {
        if (!Files.exists(file)) {
            Files.createDirectories(OkoBungee.getInstance().getDataFolder().toPath());
            if (fromResource) Files.copy(OkoBungee.getInstance().getResourceAsStream(fileName), file);
            else Files.createFile(file);

            OkoBungee.getInstance().getLogger().info(fileName + " を作成しました。");
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
