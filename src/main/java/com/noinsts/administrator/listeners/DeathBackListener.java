package com.noinsts.administrator.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Це клас обробляє збереження місця смерті та команду для телепортації для нього.
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public class DeathBackListener implements Listener, CommandExecutor {

    /** Зберігає останнє відоме місце смерті кожного гравця за його UUID. */
    private final Map<UUID, Location> lastDeathLocation = new ConcurrentHashMap<>();

    /**
     * Обробник смерті гравця.
     * Зберігає координати місця смерті в lastDeathLocation.
     *
     * @param event Евент смерті гравця.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation().clone();
        lastDeathLocation.put(uuid, location);
    }

    /**
     * Виконує команду /backdeath.
     *
     * <hr>
     *
     * <h1>Поведінка</h1>
     *
     * <ul>
     *     <li>Якщо аргументи команди відсутні - телепортує відправника команди (якщо це гравець)</li>
     *     <li>Якщо вказано ім'я гравця - телепортує цього гравця на його точку смерті.</li>
     * </ul>
     *
     * @param sender Відправник команди.
     * @param command Об'єкт команди.
     * @param label Псевдонім, під яким була викликана команда.
     * @param args Аргументи команди (опціонально - нік гравця).
     *
     * @return {@code true}, якщо команда виконанна успішно.
     */
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        Player target;
        if (args.length >= 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cГравця з таким ніком не знайдено");
                return true;
            }
        }
        else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cЦю команду може викликати лише гравець.");
                return true;
            }
            target = (Player) sender;
        }

        Location deathLoc = lastDeathLocation.get(target.getUniqueId());

        if (target.isOnline()) {
            if (deathLoc == null) {
                sender.sendMessage("§cВибачте, місце вашої смерті не збережене. :(");
                return true;
            }
            try {
                target.teleport(deathLoc);
                sender.sendMessage(String.format(
                        "§aГравця §e§l%s§r§a телепортовано до точки смерті.",
                        target.getName()
                ));
            }
            catch (Exception e) {
                sender.sendMessage("§cСталась помилка при телепорті.");
            }
        }
        else {
            sender.sendMessage("§cГравець не онлайн");
        }

        return true;
    }
}
