package com.noinsts.administrator.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Слухач подій, що автоматично змінює колір імені в гравці залежно від виміру світу.
 *
 * <p>Колірна схема:</p>
 * <ul>
 *     <li>Звичайний світ (Overworld) - {@link NamedTextColor#GREEN зелений}</li>
 *     <li>Пекло (Nether) - {@link NamedTextColor#RED червоний}</li>
 *     <li>Край (The End) - {@link NamedTextColor#LIGHT_PURPLE світло-фіолетовий}</li>
 * </ul>
 *
 * <p>Колір застосовується до:</p>
 * <ul>
 *     <li>Імені гравця в списку гравців (Tab)</li>
 *     <li>Імені гравця в повідомленнях чату</li>
 * </ul>
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public class ColoredNameListener implements Listener {

    /**
     * Обробляє подію приєднання гравця до сервера.
     * Оновлює колір імені гравця відповідно до виміру, в якому він з'явився.
     *
     * @param event Подія приєднання гравця.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateColor(event.getPlayer());
    }

    /**
     * Обробляє подію зміни світу гравцем.
     * Оновлює колір імені гравця при переході між вимірами.
     *
     * @param event Подія зміни світу.
     */
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        updateColor(event.getPlayer());
    }

    /**
     * Обробляє подію відправлення сповіщення користувачем.
     * Оновлює колір гравця у чаті залежно від виміру світу.
     *
     * @param event Подія відправлення сповіщення.
     */
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        NamedTextColor color = getColor(player.getWorld().getEnvironment());

        event.renderer((source, displayName, message, viewer) ->
                Component.text("<")
                        .append(Component.text(player.getName(), color))
                        .append(Component.text("> "))
                        .append(message.color(NamedTextColor.WHITE))
        );
    }

    /**
     * Повертає колір залежно від виміру світу.
     *
     * <ul>
     *     <li>Звичайний світ (Overworld) - {@link NamedTextColor#GREEN зелений}</li>
     *     <li>Пекло (Nether) - {@link NamedTextColor#RED червоний}</li>
     *     <li>Край (The End) - {@link NamedTextColor#LIGHT_PURPLE світло-фіолетовий}</li>
     * </ul>
     *
     * @param env Вимір світу.
     * @return Колір
     */
    @SuppressWarnings("EnhancedSwitchMigration")
    private NamedTextColor getColor(World.Environment env) {
        switch (env) {
            case NETHER:
                return NamedTextColor.RED;
            case THE_END:
                return NamedTextColor.LIGHT_PURPLE;
            case NORMAL:
            default:
                return NamedTextColor.GREEN;
        }
    }

    /**
     * Оновлює колір нікнейму гравця в списку гравців (Tab).
     * @param player Гравець, якому треба змінити колір.
     */
    private void updateColor(Player player) {
        World.Environment env = player.getWorld().getEnvironment();
        NamedTextColor color = getColor(env);

        player.playerListName(Component.text(player.getName()).color(color));
    }
}
