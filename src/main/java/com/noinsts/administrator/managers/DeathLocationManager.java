package com.noinsts.administrator.managers;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер, який зберігає та надає доступ до координат останньої смерті гравців.
 *
 * <p>
 *
 * Використовується для команд або слухачів подій, що потребують інформації
 * про місце, де гравець помер. Дані зберігаються у пам'яті під час роботи сервера
 * і не зберігаються після його перезапуску.
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public class DeathLocationManager {

    /** Потокобезпечна мапа, що зберігає координати смерті гравців. */
    private final Map<UUID, Location> deathLocations = new ConcurrentHashMap<>();

    /**
     * Зберігає координати смерті гравця.
     *
     * @param uuid Унікальний ідентифікатор гравця.
     * @param location Локаціяч смерті гравця.
     */
    public void saveDeathLocation(UUID uuid, @NotNull Location location) {
        deathLocations.put(uuid, location.clone());
    }

    /**
     * Повертає збережені координати смерті гравця.
     *
     * @param uuid Унікальний ідентифікатор гравця.
     * @return {@link Location} точки смерті, або {@code null}, якщо дані відсутні.
     */
    public Location getPlayerLocation(UUID uuid) {
        return deathLocations.get(uuid);
    }
}
