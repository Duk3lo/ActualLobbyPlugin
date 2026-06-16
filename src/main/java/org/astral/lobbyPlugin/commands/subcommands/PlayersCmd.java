package org.astral.lobbyPlugin.commands.subcommands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Stream;

public final class PlayersCmd implements SubCommand {

    private final LobbyPlugin plugin;

    public PlayersCmd(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getName() {
        return "players";
    }

    @Override
    public void execute(@NonNull CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSolo un jugador puede usar este comando.");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("§eUsa: §f/lobby players <hide|show>");
            return;
        }

        String action = args[1].toLowerCase();

        if (action.equals("hide")) {
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.equals(player)) continue;
                player.hidePlayer(plugin, target);
            }
            player.sendMessage("§aHas ocultado a los demás jugadores.");

        } else if (action.equals("show")) {
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.equals(player)) continue;
                player.showPlayer(plugin, target);
            }
            player.sendMessage("§aAhora puedes ver a los demás jugadores.");

        } else {
            player.sendMessage("§eUsa: §f/lobby players <hide|show>");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String @NonNull [] args) {
        if (args.length == 2) {
            return Stream.of("hide", "show")
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .toList();
        }
        return List.of();
    }
}