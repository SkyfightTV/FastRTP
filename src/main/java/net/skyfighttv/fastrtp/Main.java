package net.skyfighttv.fastrtp;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    {
        instance = this;
    }

    @Override
    public void onEnable() {
        getCommand("rtp").setExecutor(new RTPCommand());
    }

    public static Main getInstance() {
        return instance;
    }
}
