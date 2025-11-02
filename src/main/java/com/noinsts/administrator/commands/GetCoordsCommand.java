package com.noinsts.administrator.commands;

import com.noinsts.administrator.utils.PlayerResolverUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        final Optional<Player> target = PlayerResolverUtil.resolveTargetPlayer(sender, args);
        if (target.isEmpty()) {
            return true;
        }

        sendCoordinates(sender, target.get());
        return true;
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
