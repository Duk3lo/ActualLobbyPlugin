package org.astral.lobbyPlugin.commands.subcommands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class SpawnCmd implements SubCommand {

    private final LobbyPlugin plugin;

    public SpawnCmd(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getName() {
        return "spawn";
    }

    @Override
    public void execute(@NonNull CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSolo un jugador puede usar este comando.");
            return;
        }


        Location spawnLoc = plugin.getConfigManager().getSpawnLocation();

        if (spawnLoc == null) {
            player.sendMessage("§cEl spawn no ha sido configurado todavía.");
            return;
        }

        player.teleportAsync(spawnLoc).thenAccept(success -> {
            if (success) {
                player.sendMessage("§aHas sido teletransportado al spawn.");
            }
        });
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}