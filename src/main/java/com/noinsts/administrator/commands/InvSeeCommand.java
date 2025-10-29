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

    /** Розмір інвентарю користувача (як велика скриня) */
    private static final int INVENTORY_SIZE = 54;

    /** Початковий індекс слотів для броні у вікні (з 0 до 53). */
    private static final int ARMOR_SLOT_STAR = 45;

    /** Індекс слота другої руки у вікні. */
    private static final int OFFHAND_SLOT = 50;

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
                INVENTORY_SIZE,
                Component.text("Інвентар " + target.getName())
        );

        // Основний інвентар
        ItemStack[] storage = target.getInventory().getStorageContents();
        for (int i = 0; i < storage.length; i++) {
            inventory.setItem(i, storage[i]);
        }

        // Броня
        ItemStack[] armor = target.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null) {
                inventory.setItem(i + ARMOR_SLOT_STAR, armor[i]);
            }
        }

        // Друга рука
        ItemStack offhand = target.getInventory().getItemInOffHand();
        inventory.setItem(OFFHAND_SLOT, offhand);

        ((Player) sender).openInventory(inventory);

        return true;
    }
}
