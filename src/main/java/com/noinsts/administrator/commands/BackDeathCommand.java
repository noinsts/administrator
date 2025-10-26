package com.noinsts.administrator.commands;

import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.Bukkit;
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
     * <h1>Поведінка</h1>
     *
     * <ol>
     *     <li>
     *         Визначається гравець, якого треба телепортувати:
     *         якщо аргумент вказаний - той гравець, якщо нік - сам відправник
     *     </li>
     *     <li>Перевіряється, чи гравець онлайн.</li>
     *     <li>Перевіряється, чи збережена точка смерті.</li>
     *     <li>Якщо так - виконується телепортація до координат смерті.</li>
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
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        Player target;
        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cГравця з таким ніком не знайдено.");
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("§cЦю команду можна використовувати лише гравцю.");
            return true;
        } else {
            target = (Player) sender;
        }

        Location deathLocation = deathLocationManager.getPlayerLocation(target.getUniqueId());

        if (target.isOnline()) {
            if (deathLocation == null) {
                sender.sendMessage("§cВибачте, місце вашої смерті не збережене. :(");
                return true;
            }
            try {
                target.teleport(deathLocation);
                sender.sendMessage(String.format(
                        "§aГравця §e§l%s§r§a телепортовано до точки смерті.",
                        target.getName()
                ));
            } catch (Exception e) {
                sender.sendMessage("§cСталась помилка при телепорті. Спробуйте знову");
            }
        } else {
            sender.sendMessage("§cГравець не онлайн");
        }
        return true;
    }
}
