package com.noinsts.administrator;

import com.noinsts.administrator.commands.BackDeathCommand;
import com.noinsts.administrator.commands.GetCoordsCommand;
import com.noinsts.administrator.listeners.ColoredNameListener;
import com.noinsts.administrator.listeners.PlayerDeathListener;
import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private DeathLocationManager deathLocationManager;

    @Override
    public void onEnable() {
        getLogger().info("⚙️ Administrator plugin is starting...");

        initializeManagers();
        registerCommands();
        registerListeners();

        getLogger().info("✅ Administrator plugin successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("\uD83D\uDED1 Administrator plugin disabled.");
    }

    private void initializeManagers() {
        this.deathLocationManager = new DeathLocationManager();
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("getcoords")).setExecutor(new GetCoordsCommand());
        Objects.requireNonNull(this.getCommand("backdeath")).setExecutor(new BackDeathCommand(deathLocationManager));
    }

    private void registerListeners() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new ColoredNameListener(), this);
        pm.registerEvents(new PlayerDeathListener(deathLocationManager), this);
    }
}
