package com.noinsts.administrator.commands;

import com.noinsts.administrator.managers.DeathLocationManager;
import com.noinsts.administrator.utils.PlayerResolverUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Команда, що телепортує гравця до місця його останньої смерті.
 *
 * <p>
 *
 * <ul>
 *     <li>Якщо аргумент [player] вказаний - виконується телепортація його</li>
 *     <li>Якщо без аргументів - телепортується відправник</li>
 * </ul>
 *
 * @param deathLocationManager Менеджер управліннями координат смерті.
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public record BackDeathCommand(DeathLocationManager deathLocationManager) implements CommandExecutor {

    /**
     * Оброблює команду /backdeath.
     *
     * <hr>
     *
     * <h1>Поведінка:</h1>
     *
     * <ol>
     *     <li>
     *         Визначається гравець, якого потрібно телепортувати
     *         за допомогою утилітарного класу {@link PlayerResolverUtil}.
     *     </li>
     *     <li>
     *         Викликається метод <i>teleport</i> з цього класу, який
     *         за можливості телепортує гравця до точки смерті та надсилає повідомлення.
     *     </li>
     * </ol>
     *
     * <hr>
     *
     * @param sender Відправник команди.
     * @param command Об'єкт команди.
     * @param label Під яким псевдонімом викликалась команда.
     * @param args Аргументи команди.
     *
     * @return {@code true}, якщо команда виконалась успішно.
     */
    @Override
    public boolean onCommand(
            @NotNull final CommandSender sender,
            @NotNull final Command command,
            @NotNull final String label,
            @NotNull final String[] args
    ) {
        PlayerResolverUtil.resolveTargetPlayer(sender, args)
                .map(target -> {
                    teleport(sender, target);
                    return true;
                });
        return true;
    }

    /**
     * Оброблює телепортацію гравця до точки його смерті.
     *
     * <hr>
     *
     * <h1>Поведінка:</h1>
     *
     * <ol>
     *     <li>
     *         З класу {@link DeathLocationManager} викликається метод,
     *         що отримує координати гравця
     *     </li>
     *     <li>Перевірка на наявність гравця в мережі</li>
     *     <li>Перевірка на наявність координат</li>
     *     <li>Телепортується гравець</li>
     * </ol>
     *
     * <hr>
     *
     * @param sender Відправник команди.
     * @param target Той, хто телепортується.
     */
    private void teleport(
            @NotNull final CommandSender sender,
            @NotNull final Player target
    ) {
        final Location deathLocation = deathLocationManager.getPlayerLocation(target.getUniqueId());

        if (deathLocation == null) {
            final String message = sender.equals(target)
                    ? "§cВибачте, місце вашої смерті не збережене. :("
                    : String.format("§cМісце смерті гравця §e%s§c не збережене.", target.getName());
            sender.sendMessage(message);
            return;
        }

        if (deathLocation.getWorld() == null) {
            sender.sendMessage("§cСвіту, де відбулася смерть, більше не існує");
            return;
        }

        if (!target.isOnline()) {
            sender.sendMessage(String.format("§cГравець §e%s§c не в мережі", target.getName()));
            return;
        }

        try {
            target.teleport(deathLocation);

            final String successMessage = sender.equals(target)
                    ? "§aВас телепортовано до точки вашої смерті."
                    : String.format("§aГарвця §e§l%s§r§a телепортовано до точки смерті.", target.getName());

            sender.sendMessage(successMessage);

            if (!sender.equals(target)) {
                target.sendMessage("§aВас телепортовано до точки вашої смерті.");
            }
        }
        catch (Exception e) {
            sender.sendMessage("§cСталась помилка при телепорті. Спробуйте знову");
        }
    }
}