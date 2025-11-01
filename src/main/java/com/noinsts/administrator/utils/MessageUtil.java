package com.noinsts.administrator.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Utility клас для форматування та відправки повідомлень гравцям.
 *
 * @since 1.1-SNAPSHOT
 * @author noinsts
 */
public final class MessageUtil {

    private static final String ERROR_PREFIX = "❌ ";

    /**
     * Приватний конструктор для запобігання створенню екземплярів.
     */
    private MessageUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Відправляє повідомлення про помилку.
     *
     * @param sender Отримувач повідомлення.
     * @param message Текст помилка.
     */
    public static void sendError(
            @NotNull final CommandSender sender,
            @NotNull final String message
    ) {
        final Component component = Component.text()
                .append(Component.text(ERROR_PREFIX, NamedTextColor.RED))
                .append(Component.text(message, NamedTextColor.RED))
                .build();

        sender.sendMessage(component);
    }

    /**
     * Відправляє повідомлення про те, що гравця не знвйдено.
     *
     * @param sender Отримувач повідомлення.
     * @param playerName Нік гравця.
     */
    public static void sendPlayerNotFound(
            @NotNull final CommandSender sender,
            @NotNull final String playerName
    ) {
        final Component component = Component.text()
                .append(Component.text(ERROR_PREFIX, NamedTextColor.RED))
                .append(Component.text("Гравця з ніком", NamedTextColor.RED))
                .append(Component.text(playerName, NamedTextColor.WHITE, TextDecoration.BOLD))
                .append(Component.text(" не знайдено", NamedTextColor.RED))
                .build();

        sender.sendMessage(component);
    }

    /**
     * Відправляє повідомлення про те, що команда доступна лише гравцям.
     *
     * @param sender Отримувач повідомлення.
     */
    public static void sendPlayerOnly(@NotNull final CommandSender sender) {
        sendError(sender, "Цю команду може використовувати лише гравець.");
    }
}
