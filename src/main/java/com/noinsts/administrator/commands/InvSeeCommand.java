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

/**
 * Команда {@code /invsee [player]} дозволяє переглядати вміст інвентарю інших гравців.
 *
 * <p>
 *
 * Ця команда відкриває інтерфейс великої скрині у якому відображається:
 *
 * <ul>
 *     <li>Весь основний інвентар гравця (36 слотів)</li>
 *     <li>Броня гравця (4 слоти, починаючи з {@link #ARMOR_SLOT_START})</li>
 *     <li>Предмет у другій ручі (слот {@link #OFFHAND_SLOT})</li>
 * </ul>
 *
 * <p>
 *
 * Гравець, який відкриває інвентар, не може змінювати його вміст.
 * Захист реалізовано через {@link com.noinsts.administrator.listeners.InvSeeListener}
 *
 * @since 1.1-SNAPSHOT
 * @author noinsts
 */
public class InvSeeCommand implements CommandExecutor {

    /** Розмір інвентарю користувача (як велика скриня) */
    private static final int INVENTORY_SIZE = 54;

    /** Початковий індекс слотів для броні у вікні (з 0 до 53). */
    private static final int ARMOR_SLOT_START = 45;

    /** Індекс слота другої руки у вікні. */
    private static final int OFFHAND_SLOT = 50;

    /**
     * Викликається при введенні команди {@code /invsee [player]}.
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
                inventory.setItem(i + ARMOR_SLOT_START, armor[i]);
            }
        }

        // Друга рука
        ItemStack offhand = target.getInventory().getItemInOffHand();
        inventory.setItem(OFFHAND_SLOT, offhand);

        ((Player) sender).openInventory(inventory);

        return true;
    }
}
