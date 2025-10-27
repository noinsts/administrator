package com.noinsts.administrator;

import com.noinsts.administrator.commands.BackDeathCommand;
import com.noinsts.administrator.commands.GetCoordsCommand;
import com.noinsts.administrator.listeners.ColoredNameListener;
import com.noinsts.administrator.listeners.PlayerDeathListener;
import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Головний клас плагіну "Administrator".
 *
 * <hr>
 *
 * <h1>Відповідає за:</h1>
 *
 * <ul>
 *     <li>Ініціалізацію менеджерів ({@link DeathLocationManager})</li>
 *     <li>Реєстрацію команд (/backdeath, /getcoords)</li>
 *     <li>Реєстрацію слухачів подій ({@link PlayerDeathListener}, {@link ColoredNameListener})</li>
 *     <li>Логування старту та вимкнення плагіну</li>
 * </ul>
 *
 * @since 1.0-SNAPSHOT
 * @author noinsts
 */
public final class Main extends JavaPlugin {

    /** Менеджер, який зберігає координати останньої смерті гравців */
    private DeathLocationManager deathLocationManager;

    /**
     * Метод, який викликається при ініціалізації плагіну.
     * <p>
     * Тут відбувається ініціалізація менеджерів, команд та слухачів подій.
     */
    @Override
    public void onEnable() {
        getLogger().info("⚙️ Administrator plugin is starting...");

        initializeManagers();
        registerCommands();
        registerListeners();

        getLogger().info("✅ Administrator plugin successfully enabled!");
    }

    /**
     * Метод, який викликається при вимкненні плагіну.
     * <p>
     * Використовується для логування завершення роботи плагіну.
     */
    @Override
    public void onDisable() {
        getLogger().info("\uD83D\uDED1 Administrator plugin disabled.");
    }

    /**
     * Ініціалізує всі менеджери плагіну
     */
    private void initializeManagers() {
        this.deathLocationManager = new DeathLocationManager();
    }

    /**
     * Реєструє всі команди плагіну
     */
    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("getcoords")).setExecutor(new GetCoordsCommand());
        Objects.requireNonNull(this.getCommand("backdeath")).setExecutor(new BackDeathCommand(deathLocationManager));
    }

    /**
     * Реєструє всі слухачі подій плагіну
     */
    private void registerListeners() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new ColoredNameListener(), this);
        pm.registerEvents(new PlayerDeathListener(deathLocationManager), this);
    }
}
