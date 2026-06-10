package org.astral.lobbyPlugin.events;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.astral.lobbyPlugin.events.event.JoinEvent;
import org.astral.lobbyPlugin.events.event.ProtectionListener;
import org.bukkit.plugin.PluginManager;
import org.jspecify.annotations.NonNull;

public final class RegisterEvents {

    public static void registerAll(@NonNull LobbyPlugin plugin) {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new JoinEvent(), plugin);
        manager.registerEvents(new ProtectionListener(), plugin);
    }
}