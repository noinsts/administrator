package com.noinsts.administrator.commands;

import com.noinsts.administrator.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Оброблює команду /getcoords [player].
 *
 * <p>Команда показує координати гравця або відправника, якщо нік не вказаний.</p>
 *
 * <ul>
 *     <li><b>/getcoords [player]</b> - координати вказаного гравця</li>
 *     <li><b>/getcoords</b> - координати відправника</li>
 * </ul>
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public class GetCoordsCommand implements CommandExecutor {
    /**
     * Викликається при введенні команди /getcoord.
     *
     * @param sender        Відправник команди.
     * @param command       Об'єкт команди.
     * @param label         Псевдонім, під яким виконана команда.
     * @param args          Аргументи команди.
     *
     * @return {@code true}, якщо команда виконана успішно
     */
    @Override
    public boolean onCommand(
            @NotNull final CommandSender sender,
            @NotNull final Command command,
            @NotNull final String label,
            @NotNull final String[] args
    ) {
        final Optional<Player> target = resolveTargetPlayer(sender, args);
        if (target.isEmpty()) {
            return true;
        }

        sendCoordinates(sender, target.get());
        return true;
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
    private Optional<Player> resolveTargetPlayer(
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

    /**
     * Команда відправляє координати гравця.
     *
     * @param sender Отримувач координат.
     * @param target Гравець, координати якого відправляться.
     */
    private void sendCoordinates(
            @NotNull final CommandSender sender,
            @NotNull final Player target
    ) {
        final Location location = target.getLocation();
        final String worldName = Optional.ofNullable(location.getWorld())
                .map(WorldInfo::getName)
                .orElse("unknown");

        final Component component = Component.text()
                .append(Component.text("Координати: ", NamedTextColor.GREEN))
                .append(Component.text(target.getName(), NamedTextColor.YELLOW))
                .append(Component.text(" [ " + worldName + "]: ", NamedTextColor.GREEN))
                .append(Component.text(String.format(
                        "X: %.1f, Y: %.1f, Z: %.1f",
                        location.getX(), location.getY(), location.getZ()

                ), NamedTextColor.WHITE))
                .build();

        sender.sendMessage(component);
    }
}
