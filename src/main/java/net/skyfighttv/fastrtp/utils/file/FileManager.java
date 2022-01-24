package net.skyfighttv.fastrtp.utils.file;

import net.skyfighttv.fastrtp.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;

public final class FileManager {
    private static final HashMap<Files, YamlConfiguration> values;

    static {
        values = new HashMap<>();
        Main.getInstance().saveDefaultConfig();

        for (Files files : Files.values()) {
            File file = new File(Main.getInstance().getDataFolder() + "/" + files.getName() + ".yml");
            if (!file.exists()) {
                try {
                    InputStream fileStream = Main.getInstance().getResource(files.getName() + ".yml");
                    byte[] buffer = new byte[fileStream.available()];
                    fileStream.read(buffer);

                    file.createNewFile();
                    OutputStream outStream = new FileOutputStream(file);
                    outStream.write(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            values.put(files, YamlConfiguration.loadConfiguration(file));
        }
    }

    public static void save(Files files) {
        File file = new File(Main.getInstance().getDataFolder() + "/" + files.getName() + ".yml");
        if (!file.exists()) {
            System.out.println("ERROR : File " + files.getName() + " not found !");
            return;
        }
        try {
            values.get(files).save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put(files, YamlConfiguration.loadConfiguration(file));
    }

    public static void reload(Files files) {
        File file = new File(Main.getInstance().getDataFolder() + "/" + files.getName() + ".yml");

        if (!file.exists()) {
            System.out.println("ERROR : File " + files.getName() + " not found !");
            return;
        }

        values.put(files, YamlConfiguration.loadConfiguration(file));
    }

    public static HashMap<Files, YamlConfiguration> getValues() {
        return values;
    }
}
