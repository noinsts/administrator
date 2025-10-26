package com.noinsts.administrator.listeners;

import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Слухач події смерті гравців.
 * <p>
 * Відстежує момент, коли гравець помирає, і зберігає його останні координати
 * за допомогою {@link DeathLocationManager}.
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public record PlayerDeathListener(DeathLocationManager deathLocationManager) implements Listener {

    /**
     * Оброблює подію {@link PlayerDeathEvent}.
     * <p>
     * Коли гравець помирає, метод отримує його об'єкт {@link Player}
     * і передає UUID та поточну локацію до {@link DeathLocationManager}
     * для майбутнього використання.
     *
     * @param event Евент смерті гравця.
     */
    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        Player player = event.getPlayer();
        deathLocationManager.saveDeathLocation(
                player.getUniqueId(),
                player.getLocation()
        );
    }
}
