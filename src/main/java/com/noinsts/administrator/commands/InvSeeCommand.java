package com.noinsts.administrator.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InvSeeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЦією командою можуть користуватись лише гравці.");
            return true;
        }

        Player target;
        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cГравця не знайдено, спробуйте пізніше.");
                return true;
            }
        }
        else {
            target = (Player) sender;
        }

        Inventory inventory = Bukkit.createInventory(
                null,
                54,
                Component.text("Інвентар " + target.getName())
        );

        ItemStack[] storage = target.getInventory().getStorageContents();
        for (int i = 0; i < storage.length; i++) {
            inventory.setItem(i, storage[i]);
        }

        ((Player) sender).openInventory(inventory);

        return true;
    }
}
