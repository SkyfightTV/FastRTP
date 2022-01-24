package net.skyfighttv.fastrtp;

import net.skyfighttv.fastrtp.utils.file.FileManager;
import net.skyfighttv.fastrtp.utils.file.Files;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RTP {
    private static final String PERMISSION;
    private static final int MIN_RADIUS;
    private static final int MAX_RADIUS;
    private static final int RADIUS;
    private static final Random RANDOM;
    private static final List<Material> blackList;

    static {
        YamlConfiguration config = FileManager.getValues().get(Files.Config);
        PERMISSION = config.getString("permission");
        MIN_RADIUS = config.getInt("minRadius");
        MAX_RADIUS = config.getInt("maxRadius");
        RADIUS = MAX_RADIUS - MIN_RADIUS;
        RANDOM = new Random();
        blackList = new ArrayList<>();
        config.getStringList("blackListMaterial").forEach(s ->
                blackList.add(Material.matchMaterial(s)));
    }

    public static void apply(Player player) {
        YamlConfiguration lang = FileManager.getValues().get(Files.Lang);
        int x;
        int z;
        Location location = player.getLocation();

        do {
            x = RANDOM.nextInt(RADIUS) + MIN_RADIUS;
            z = RANDOM.nextInt(RADIUS) + MIN_RADIUS;
            if (RANDOM.nextBoolean())
                x *= -1;
            if (RANDOM.nextBoolean())
                z *= -1;

            location.setY(player.getWorld().getHighestBlockYAt(x, z, HeightMap.MOTION_BLOCKING_NO_LEAVES));
        } while (blackList.contains(player.getWorld().getBlockAt(x, (int) location.getY(), z).getType()));

        location.setX(x);
        location.setZ(z);

        player.teleport(location.add(0.5, 1, 0.5), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(lang.getString("SuccessfulRTP"));
    }

    public static String getPERMISSION() {
        return PERMISSION;
    }
}
