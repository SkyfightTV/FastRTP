package net.skyfighttv.fastrtp;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getCommand("rtp").setExecutor(new RTPCommand());
    }
}
