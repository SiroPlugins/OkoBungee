package com.github.siroshun09.okobungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class MessageUtils {

    public static void send(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(TextComponent.fromLegacyText(setColor(message)));
    }

    public static void broadcast(@NotNull String message) {
        ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(setColor(message)));
    }

    /**
     * 渡された文字列のカラーコードをセットする。
     *
     * @param message メッセージ
     * @return カラーコードがセットされたメッセージ
     */
    @NotNull
    private static String setColor(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
