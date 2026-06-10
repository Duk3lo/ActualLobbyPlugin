package org.astral.lobbyPlugin.commands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.command.PluginCommand;

public final class RegisterCommands {

    public static void registerAll(LobbyPlugin plugin) {
        LobbyCommand lobbyCommand = new LobbyCommand(plugin);
        PluginCommand command = plugin.getCommand("lobby");

        if (command != null) {
            command.setExecutor(lobbyCommand);
            command.setTabCompleter(lobbyCommand);
        } else {
            plugin.getLogger().severe("Error: the 'lobby' command is not defined in plugin.yml");
        }
    }
}