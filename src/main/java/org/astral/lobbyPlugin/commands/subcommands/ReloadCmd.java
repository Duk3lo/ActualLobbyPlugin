package org.astral.lobbyPlugin.commands.subcommands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class ReloadCmd implements SubCommand {

    private final LobbyPlugin plugin;

    public ReloadCmd(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getName() {
        return "reload";
    }

    @Override
    public void execute(@NonNull CommandSender sender, String[] args) {
        if (!sender.hasPermission("lobbyplugin.admin")) {
            sender.sendMessage("§cNo tienes permiso para usar esto.");
            return;
        }

        try {
            plugin.reloadPlugin();
            sender.sendMessage("§aConfiguración recargada correctamente.");
        } catch (Exception e) {
            sender.sendMessage("§cError al recargar la configuración.");
            plugin.getLogger().severe("Error reloading config: " + e.getMessage());
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}