package org.astral.lobbyPlugin.commands;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.astral.lobbyPlugin.commands.subcommands.ReloadCmd;
import org.astral.lobbyPlugin.commands.subcommands.SetSpawnCmd;
import org.astral.lobbyPlugin.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LobbyCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public LobbyCommand(LobbyPlugin plugin) {
        register(new ReloadCmd(plugin));
        register(new SetSpawnCmd(plugin));
    }

    private void register(SubCommand cmd) {
        subCommands.put(cmd.getName().toLowerCase(), cmd);
    }

    @Override
    public boolean onCommand(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String label,
            @NonNull String @NonNull [] args
    ) {
        if (args.length == 0) {
            sender.sendMessage("§eUsa: §f/lobby <reload|setspawn>");
            return true;
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub == null) {
            sender.sendMessage("§cSubcomando no encontrado.");
            return true;
        }

        sub.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String label,
            @NonNull String @NonNull [] args
    ) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        return sub != null ? sub.tabComplete(sender, args) : List.of();
    }
}