package org.astral.lobbyPlugin.config;

import org.astral.lobbyPlugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public final class LobbyPluginConfig {

    private final LobbyPlugin plugin;
    private Location spawnLocation;
    private boolean protectionEnabled;
    private String adminPermission;
    private boolean bypassOp;
    private boolean bypassCreative;
    private boolean blockBreak;
    private boolean blockPlace;
    private boolean interact;
    private boolean trample;
    private boolean damage;

    private boolean boundsEnabled;
    private boolean xEnabled, yEnabled, zEnabled;
    private double minX, maxX, minY, maxY, minZ, maxZ;

    public LobbyPluginConfig(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        String worldName = config.getString("spawn.world", "world");
        World world = Bukkit.getWorld(worldName);
        if (world == null && !Bukkit.getWorlds().isEmpty()) world = Bukkit.getWorlds().getFirst();

        if (world != null) {
            spawnLocation = new Location(world,
                    config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"),
                    (float) config.getDouble("spawn.yaw"), (float) config.getDouble("spawn.pitch"));
        }

        protectionEnabled = config.getBoolean("protection.enabled", true);
        adminPermission = config.getString("protection.admin-permission", "lobbyplugin.admin");
        bypassOp = config.getBoolean("protection.bypass-op", false);
        bypassCreative = config.getBoolean("protection.bypass-creative", false);
        blockBreak = config.getBoolean("protection.block-break", true);
        blockPlace = config.getBoolean("protection.block-place", true);
        interact = config.getBoolean("protection.interact", true);
        trample = config.getBoolean("protection.trample", true);
        damage = config.getBoolean("protection.damage", true);

        boundsEnabled = config.getBoolean("spawn.bounds.enabled", true);

        xEnabled = config.getBoolean("spawn.bounds.x.enabled", false);
        minX = config.getDouble("spawn.bounds.x.min", -50.0);
        maxX = config.getDouble("spawn.bounds.x.max", 50.0);

        yEnabled = config.getBoolean("spawn.bounds.y.enabled", false);
        minY = config.getDouble("spawn.bounds.y.min", 0.0);
        maxY = config.getDouble("spawn.bounds.y.max", 255.0);

        zEnabled = config.getBoolean("spawn.bounds.z.enabled", false);
        minZ = config.getDouble("spawn.bounds.z.min", -50.0);
        maxZ = config.getDouble("spawn.bounds.z.max", 50.0);
    }

    public boolean isInsideBounds(Location loc) {
        if (!boundsEnabled || loc == null || spawnLocation == null) return true;
        if (!loc.getWorld().equals(spawnLocation.getWorld())) return false;

        if (xEnabled) {
            if (loc.getX() < minX || loc.getX() > maxX) return false;
        }
        if (yEnabled) {
            if (loc.getY() < minY || loc.getY() > maxY) return false;
        }
        if (zEnabled) {
            return !(loc.getZ() < minZ) && !(loc.getZ() > maxZ);
        }

        return true;
    }

    public void saveSpawn(Location location) {
        if (location == null || location.getWorld() == null) return;
        plugin.getConfig().set("spawn.world", location.getWorld().getName());
        plugin.getConfig().set("spawn.x", location.getX());
        plugin.getConfig().set("spawn.y", location.getY());
        plugin.getConfig().set("spawn.z", location.getZ());
        plugin.getConfig().set("spawn.yaw", location.getYaw());
        plugin.getConfig().set("spawn.pitch", location.getPitch());
        plugin.saveConfig();
        spawnLocation = location.clone();
    }

    public boolean isProtectionEnabled() { return protectionEnabled; }
    public String getAdminPermission() { return adminPermission; }
    public boolean isBypassOp() { return bypassOp; }
    public boolean isBypassCreative() { return bypassCreative; }
    public boolean isBlockBreakEnabled() { return blockBreak; }
    public boolean isBlockPlaceEnabled() { return blockPlace; }
    public boolean isInteractEnabled() { return interact; }
    public boolean isTrampleEnabled() { return trample; }
    public boolean isDamageEnabled() { return damage; }
    public boolean isBoundsEnabled() { return boundsEnabled; }
    public Location getSpawnLocation() { return spawnLocation; }
}