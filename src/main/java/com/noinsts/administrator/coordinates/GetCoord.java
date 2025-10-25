package com.noinsts.administrator.coordinates;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Оброблює команду /getcoord [player].
 *
 * <p>Команда показує координати гравця або відправника, якщо нік не вказаний.</p>
 *
 * <ul>
 *     <li><b>/getcoord [player]</b> - координати вказаного гравця</li>
 *     <li><b>/getcoord</b> - координати відправника</li>
 * </ul>
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public class GetCoord implements CommandExecutor {
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
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage("§c❌ Гравця з ніком §f" + args[0] + "§ cне знайдено");
                return true;
            }
        }
        else if (sender instanceof Player) {
            target = (Player) sender;
        }
        else {
            sender.sendMessage("§c❌ Цю команду може використовувати лише гравець.");
            return true;
        }

        Location location = target.getLocation();
        sender.sendMessage(String.format(
                "§a📍 Координати §e%s§a [%s]: §fX: %.1f, Y: %.1f, Z: %.1f",
                target.getName(),
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        ));

        return true;
    }
}
