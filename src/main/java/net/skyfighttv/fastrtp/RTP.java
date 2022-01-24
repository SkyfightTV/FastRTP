package net.skyfighttv.fastrtp;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import net.skyfighttv.fastrtp.utils.file.FileManager;
import net.skyfighttv.fastrtp.utils.file.Files;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public final class RTP {
    private static final String PERMISSION;
    private static final int MIN_RADIUS;
    private static final int MAX_RADIUS;
    private static final int RADIUS;
    private static final Random RANDOM;
    private static final List<>

    static {
        YamlConfiguration config = FileManager.getValues().get(Files.Config);
        PERMISSION = config.getString("permission");
        MIN_RADIUS = config.getInt("minRadius");
        MAX_RADIUS = config.getInt("maxRadius");
        RADIUS = MAX_RADIUS - MIN_RADIUS;
        RANDOM = new Random();
    }

    public static void apply(Player player) {
        YamlConfiguration lang = FileManager.getValues().get(Files.Lang);
        int x;
        int z;
        Material material;
        Location location = player.getLocation();

        do {
            x = RANDOM.nextInt(RADIUS) + MIN_RADIUS;
            z = RANDOM.nextInt(RADIUS) + MIN_RADIUS;
            if (RANDOM.nextBoolean())
                x *= -1;
            if (RANDOM.nextBoolean())
                z *= -1;

            location.setX(x);
            location.setY(player.getWorld().getHighestBlockYAt(x, z, HeightMap.MOTION_BLOCKING_NO_LEAVES));
            location.setZ(z);

            material = location.add(0, -1, 0).getBlock().getType();
        } while (!material.equals(Material.LAVA)
                && !material.equals(Material.WATER)
                && !Board.getInstance().getFactionAt(new FLocation(location)).isWilderness());

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 255));
        player.teleport(location.add(0.5, 2, 0.5), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(lang.getString("SuccessfulRTP"));
    }

    public static String getPERMISSION() {
        return PERMISSION;
    }
}
