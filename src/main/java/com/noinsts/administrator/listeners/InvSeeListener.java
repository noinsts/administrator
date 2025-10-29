package com.noinsts.administrator.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Слухай подій пов'язаних з командою {@code invsee}.
 *
 * <p>
 *
 * Забороняє будь-яку взаємодію з інвентарем, відкритим через команду
 * {@code invsee}, щоб гравець не міг брати речі з чужого інвентарю.
 *
 * <p>
 *
 * Виконує блокування кліків і перетягувань предметів у вікнах,
 * заголовок яких починається з {@code "Інвентар "}.
 *
 * @since 1.1-SNAPSHOT
 * @author noinsts
 */
public class InvSeeListener implements Listener {

    /**
     * Обробляє події кліку в інвентарі та блокує будь-яку взаємодію
     * у вікнах, відкритих через команду {@code invsee}.
     *
     * @param event Подія {@link InventoryClickEvent}, що виникає при кліку в інвентарі.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        if (title.startsWith("Інвентар ")) {
            event.setCancelled(true);
        }
    }

    /**
     * Обробляє події перетягування предметів у інвентарі та блокує
     * будь-які зміни в інвентарях, відкритих через команду {@code invsee}.
     *
     * @param event Подія {@link InventoryDragEvent}, що виникає при перетягуванні предметів.
     */
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        if (title.startsWith("Інвентар ")) {
            event.setCancelled(true);
        }
    }
}
