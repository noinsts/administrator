package com.noinsts.administrator.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
 *     <li>Броня гравця</li>
 *     <li>Предмет у другій руці</li>
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
            @NotNull String[] args
    ) {
        if (!(sender instanceof Player viewer)) {
            sender.sendMessage(
                    Component.text("Цією командою можуть користуватись лише гравці.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        Player target = resolveTarget(viewer, args);
        if (target == null) {
            viewer.sendMessage(
                    Component.text("Гравця не знайдено, спробуйте пізніше")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        if (target.equals(viewer)) {
            viewer.sendMessage(
                    Component.text("Ви відкрили власний інвентар")
                            .color(NamedTextColor.YELLOW)
            );
            return true;
        }

        Inventory inventory = InvSeeInventoryBuilder.createInventory(target);
        viewer.openInventory(inventory);

        return true;
    }

    /**
     * Визначає цільового гравця на основі аргументів команди.
     *
     * @param viewer Гравець, який виконує команду.
     * @param args Аргументи команди.
     * @return Цільовий гравець або {@code null}, якщо не знайдено.
     */
    private Player resolveTarget(Player viewer, String[] args) {
        return args.length == 0 ? viewer : Bukkit.getPlayerExact(args[0]);
    }
}

/**
 * Створює {@link Inventory} з речами вказаного гравця.
 *
 * @since 1.1-SNAPSHOT
 * @author noinsts
 */
final class InvSeeInventoryBuilder {

    /** Розмір інвентарю користувача (як велика скриня) */
    private static final int INVENTORY_SIZE = 54;

    /** Початковий індекс слотів для броні у вікні (з 0 до 53). */
    private static final int ARMOR_SLOT_START = 45;

    /** Індекс слота другої руки у вікні. */
    private static final int OFFHAND_SLOT = 50;

    /**
     * Приватний конструктор.
     *
     * @throws UnsupportedOperationException Оскільки це utility-клас.
     */
    private InvSeeInventoryBuilder() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Створює інвентар для відображення з вмістом цільового гравця.
     *
     * @param target Гравець, чий інвентар потрібно відобразити.
     * @return Створений інвентар з вмістом.
     */
    public static Inventory createInventory(Player target) {
        Inventory inventory = Bukkit.createInventory(
                null,
                INVENTORY_SIZE,
                Component.text("Інвентар " + target.getName())
        );

        PlayerInventory targetInventory = target.getInventory();

        populateMainInventory(inventory, targetInventory);
        populateArmorSlots(inventory, targetInventory);
        populateOffHandSlot(inventory, targetInventory);

        return inventory;
    }

    /**
     * Заповнює основний інвентар у вікні перегляду.
     *
     * @param display Інвентар для перегляду.
     * @param source Інвентар гравця-джерела.
     */
    private static void populateMainInventory(Inventory display, PlayerInventory source) {
        ItemStack[] storage = source.getStorageContents();
        for (int i = 0; i < storage.length; i++) {
            display.setItem(i, storage[i]);
        }
    }

    /**
     * Заповнює слоти броні у вікні перегляду.
     *
     * @param display Інвентар для перегляду.
     * @param source Інвентар гравця-джерела.
     */
    private static void populateArmorSlots(Inventory display, PlayerInventory source) {
        ItemStack[] armor = source.getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            display.setItem(ARMOR_SLOT_START + i, armor[i]);
        }
    }

    /**
     * Заповнює слот другої руки у вікні перегляду.
     *
     * @param display Інвентар для перегляду.
     * @param source Інвентар гравця-джерела.
     */
    private static void populateOffHandSlot(Inventory display, PlayerInventory source) {
        display.setItem(OFFHAND_SLOT, source.getItemInOffHand());
    }
}