package org.astral.lobbyPlugin.events.event;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jspecify.annotations.NonNull;

public final class JoinEvent implements Listener {

    private final LobbyPlugin plugin = LobbyPlugin.getInstance();

    @EventHandler
    public void onPlayerJoin(@NonNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location location = plugin.getConfigManager().getSpawnLocation();
        if (location != null) {
            player.teleportAsync(location);
        }
        player.setCollidable(false);
    }

}