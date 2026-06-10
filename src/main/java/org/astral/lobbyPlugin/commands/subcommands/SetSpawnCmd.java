package org.astral.lobbyPlugin.commands.subcommands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class SetSpawnCmd implements SubCommand {

    private final LobbyPlugin plugin;

    public SetSpawnCmd(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getName() {
        return "setspawn";
    }

    @Override
    public void execute(@NonNull CommandSender sender, String[] args) {
        if (!sender.hasPermission("lobbyplugin.admin")) {
            sender.sendMessage("§cNo tienes permiso para usar esto.");
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSolo un jugador puede usar este comando.");
            return;
        }

        Location base = player.getLocation();
        Location newLoc = base.clone();

        if (args.length >= 4) {
            newLoc.setX(parseCoord(args[1], base.getX()));
            newLoc.setY(parseCoord(args[2], base.getY()));
            newLoc.setZ(parseCoord(args[3], base.getZ()));

            if (args.length >= 5) {
                newLoc.setYaw((float) parseCoord(args[4], base.getYaw()));
            }

            if (args.length >= 6) {
                newLoc.setPitch((float) parseCoord(args[5], base.getPitch()));
            }
        }

        plugin.getConfigManager().saveSpawn(newLoc);
        sender.sendMessage("§aSpawn guardado correctamente.");
    }

    private double parseCoord(@NonNull String value, double relative) {
        if (value.startsWith("~")) {
            if (value.length() == 1) return relative;
            return relative + Double.parseDouble(value.substring(1));
        }
        return Double.parseDouble(value);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}