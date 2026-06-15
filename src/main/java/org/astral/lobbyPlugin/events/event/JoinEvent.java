package org.astral.lobbyPlugin.events.event;

import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import org.astral.lobbyPlugin.LobbyPlugin;
import org.astral.lobbyPlugin.config.LobbyPluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jspecify.annotations.NonNull;

public final class JoinEvent implements Listener {

    private final LobbyPlugin plugin = LobbyPlugin.getInstance();

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onPlayerSpawnLocation(@NonNull AsyncPlayerSpawnLocationEvent event) {
        Location location = plugin.getConfigManager().getSpawnLocation();

        if (location != null) {
            event.setSpawnLocation(location);
        }
    }

    @EventHandler
    public void onPlayerJoin(@NonNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setCollidable(false);

        LobbyPluginConfig config = plugin.getConfigManager();

        if (!config.isJoinActionsEnabled()) {
            return;
        }

        if (config.isJoinClearInventory()) {
            player.getInventory().clear();
        }

        for (String command : config.getJoinConsoleCommands()) {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    command.replace("{player}", player.getName())
            );
        }

        for (String command : config.getJoinPlayerCommands()) {
            player.performCommand(
                    command.replace("{player}", player.getName())
            );
        }
    }
}