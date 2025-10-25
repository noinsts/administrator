package com.noinsts.administrator.listeners;

import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public record PlayerDeathListener(DeathLocationManager deathLocationManager) implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        deathLocationManager.saveDeathLocation(
                player.getUniqueId(),
                player.getLocation()
        );
    }
}
