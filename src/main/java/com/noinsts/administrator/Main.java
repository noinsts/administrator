package com.noinsts.administrator;

import com.noinsts.administrator.commands.BackDeathCommand;
import com.noinsts.administrator.commands.GetCoordsCommand;
import com.noinsts.administrator.listeners.ColoredNameListener;
import com.noinsts.administrator.listeners.PlayerDeathListener;
import com.noinsts.administrator.managers.DeathLocationManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("getcoords")).setExecutor(new GetCoordsCommand());
        getServer().getPluginManager().registerEvents(new ColoredNameListener(), this);

        DeathLocationManager deathLocationManager = new DeathLocationManager();
        Objects.requireNonNull(this.getCommand("backdeath")).setExecutor(new BackDeathCommand(deathLocationManager));
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(deathLocationManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
