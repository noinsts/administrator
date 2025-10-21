package com.noinsts.administrator.coordinates;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCoord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

        double x = target.getLocation().getX();
        double y = target.getLocation().getY();
        double z = target.getLocation().getZ();

        sender.sendMessage(String.format(
                "§aКоординати %s: §eX: %.1f, Y: %.1f, Z: %.1f ",
                target.getName(), x, y, z
        ));

        return true;
    }
}
