package com.noinsts.administrator.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Утиліта для визначення цільового грвавця у командах.
 *
 * @since 1.1-SNAPSHOT
 * @author noinsts
 */
public final class PlayerResolverUtil {

    /**
     * Приватний конструктор для запобігання створенню екземпляра.
     * @throws UnsupportedOperationException Оскільки це утилітарний клас.
     */
    private PlayerResolverUtil() {
        throw new UnsupportedOperationException("Utility class cannot be initialized");
    }

    /**
     * Визначає цільового гравця на основі аргументів команд.
     *
     * <hr>
     *
     * <h1>Поведінка</h1>
     *
     * <ul>
     *     <li>
     *         Якщо передано аргументи - повертає гравця з вказаним іменем,
     *         якщо він існує та перебуває онлайн.
     *     </li>
     *     <li>
     *         Якщо аргументи відсутні - повертає самого відправника,
     *         якщо він є гравцем.
     *     </li>
     *     <li>
     *         Якщо гравця не знайдено або команду викликав не гравець -
     *         надсилає відповідне повідомлення та повертає порожній {@code Optional}.
     *     </li>
     * </ul>
     *
     * @param sender Відправник команди.
     * @param args Аргументи команди
     * @return Optional з гравцем або порожній, якщо гравця не знайдено.
     */
    public static Optional<Player> resolveTargetPlayer(
            @NotNull final CommandSender sender,
            @NotNull final String[] args
    ) {
        if (args.length > 0) {
            final Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                MessageUtil.sendPlayerNotFound(sender, args[0]);
                return Optional.empty();
            }
            return Optional.of(target);
        }

        if (sender instanceof Player player) {
            return Optional.of(player);
        }

        MessageUtil.sendPlayerOnly(sender);
        return Optional.empty();
    }
}
