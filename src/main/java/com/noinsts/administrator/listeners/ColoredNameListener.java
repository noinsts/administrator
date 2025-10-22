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

public class ColoredNameListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateColor(event.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        updateColor(event.getPlayer());
    }

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

    private void updateColor(Player player) {
        World.Environment env = player.getWorld().getEnvironment();
        NamedTextColor color = getColor(env);

        player.playerListName(Component.text(player.getName()).color(color));
    }
}
