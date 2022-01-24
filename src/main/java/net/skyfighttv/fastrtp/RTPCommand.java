package net.skyfighttv.fastrtp;

import net.skyfighttv.fastrtp.utils.file.FileManager;
import net.skyfighttv.fastrtp.utils.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class RTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final String[] args) {
        YamlConfiguration lang = FileManager.getValues().get(Files.Lang);
        if (sender.hasPermission(RTP.getPERMISSION())) {
            if (args.length == 0
                    && sender instanceof Player)
                RTP.apply((Player) sender);
            else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Objects.requireNonNull(lang.getString("PlayerNotFound")));
                    return true;
                }

                RTP.apply(target);
                sender.sendMessage(Objects.requireNonNull(lang.getString("PlayerTeleport")).replaceAll("%player%", target.getName()));
            }
        } else
            sender.sendMessage(Objects.requireNonNull(lang.getString("DontHavePermission")));
        return true;
    }
}
