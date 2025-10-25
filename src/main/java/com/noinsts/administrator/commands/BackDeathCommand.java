package com.noinsts.administrator.commands;

import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record BackDeathCommand(DeathLocationManager deathLocationManager) implements CommandExecutor {

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
