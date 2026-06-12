package org.astral.lobbyPlugin.events.event;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jspecify.annotations.NonNull;

public final class ProtectionListener implements Listener {

    private final LobbyPlugin plugin = LobbyPlugin.getInstance();

    private boolean canBypass(@NonNull Player player) {
        var config = plugin.getConfigManager();
        if (!config.isProtectionEnabled()) return true;
        if (config.isBypassCreative() && player.getGameMode() == GameMode.CREATIVE) return true;
        if (player.isOp()) return config.isBypassOp();
        return player.hasPermission(config.getAdminPermission());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(@NonNull BlockBreakEvent event) {
        if (canBypass(event.getPlayer())) return;
        if (plugin.getConfigManager().isBlockBreakEnabled()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(@NonNull BlockPlaceEvent event) {
        if (canBypass(event.getPlayer())) return;
        if (plugin.getConfigManager().isBlockPlaceEnabled()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodChange(@NonNull FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (canBypass(player)) return;
        event.setCancelled(true);
        player.setFoodLevel(20);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(@NonNull PlayerInteractEvent event) {
        if (canBypass(event.getPlayer())) return;
        if (!plugin.getConfigManager().isInteractEnabled()) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onTrample(@NonNull EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        if (event.getEntity() instanceof Player player && canBypass(player)) return;
        if (plugin.getConfigManager().isTrampleEnabled()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(@NonNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (canBypass(player)) return;
        if (plugin.getConfigManager().isDamageEnabled()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamageByEntity(@NonNull EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (canBypass(player)) return;
        if (plugin.getConfigManager().isDamageEnabled()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(@NonNull PlayerMoveEvent event) {
        if (!plugin.getConfigManager().isBoundsEnabled()) return;

        Location to = event.getTo();
        if (!plugin.getConfigManager().isInsideBounds(to)) {
            Location spawn = plugin.getConfigManager().getSpawnLocation();
            if (spawn != null) {
                event.getPlayer().teleportAsync(spawn);
            }
        }
    }
}