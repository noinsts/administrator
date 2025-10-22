package com.noinsts.administrator;

import com.noinsts.administrator.coordinates.GetCoord;
import com.noinsts.administrator.listeners.ColoredNameListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("getcoords")).setExecutor(new GetCoord());
        getServer().getPluginManager().registerEvents(new ColoredNameListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
