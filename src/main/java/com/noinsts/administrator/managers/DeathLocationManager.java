package com.noinsts.administrator.managers;

import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DeathLocationManager {

    private final Map<UUID, Location> deathLocations = new ConcurrentHashMap<>();

    public void saveDeathLocation(UUID uuid, Location location) {
        deathLocations.put(uuid, location.clone());
    }

    public Location getPlayerLocation(UUID uuid) {
        return deathLocations.get(uuid);
    }
}
