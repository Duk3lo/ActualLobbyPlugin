package org.astral.lobbyPlugin;

import org.astral.lobbyPlugin.commands.RegisterCommands;
import org.astral.lobbyPlugin.config.LobbyPluginConfig;
import org.astral.lobbyPlugin.events.RegisterEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyPlugin extends JavaPlugin {

    private static LobbyPlugin INSTANCE;
    private LobbyPluginConfig configManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        configManager = new LobbyPluginConfig(this);
        configManager.load();

        RegisterEvents.registerAll(this);
        RegisterCommands.registerAll(this);
    }

    public void reloadPlugin() {
        this.reloadConfig();
        configManager.load();
    }

    public LobbyPluginConfig getConfigManager() {
        return configManager;
    }

    public static LobbyPlugin getInstance() {
        return INSTANCE;
    }
}