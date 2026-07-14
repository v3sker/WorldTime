package com.v3sker.worldtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class MotdWorldTimePlugin extends JavaPlugin {

    private static volatile MotdWorldTimePlugin instance;
    private volatile Map<String, WorldTimeSnapshot> worldTimeCache = Map.of();
    private BukkitTask cacheTask;
    private MotdWorldTimeExpansion expansion;

    public static MotdWorldTimePlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        updateWorldTimeCache();

        expansion = new MotdWorldTimeExpansion();
        if (!expansion.register()) {
            getLogger().warning("Failed to register PlaceholderAPI expansion motdworldtime.");
        }

        cacheTask = new BukkitRunnable() {
            @Override
            public void run() {
                updateWorldTimeCache();
            }
        }.runTaskTimer(this, 20L, 20L);

        getLogger().info("MotdWorldTime enabled. PlaceholderAPI expansion motdworldtime is active and cached values refresh every 20 ticks.");
        getLogger().info("Cached worlds: " + String.join(", ", worldTimeCache.keySet()));
    }

    @Override
    public void onDisable() {
        if (cacheTask != null) {
            cacheTask.cancel();
            cacheTask = null;
        }
        if (expansion != null) {
            expansion.unregister();
            expansion = null;
        }
        worldTimeCache = Map.of();
        instance = null;
        getLogger().info("MotdWorldTime disabled.");
    }

    public WorldTimeSnapshot getCachedWorldTime(String worldName) {
        String normalized = normalizeWorldName(worldName);
        if (normalized == null) {
            return null;
        }
        return worldTimeCache.get(normalized);
    }

    private void updateWorldTimeCache() {
        try {
            Map<String, WorldTimeSnapshot> snapshot = new HashMap<>();
            for (World world : Bukkit.getWorlds()) {
                long fullTime = world.getFullTime();
                WorldTimeSnapshot worldSnapshot = new WorldTimeSnapshot(
                        world.getTime(),
                        fullTime,
                        fullTime / 24000L);
                snapshot.put(world.getName().toLowerCase(Locale.ROOT), worldSnapshot);
            }
            worldTimeCache = Collections.unmodifiableMap(snapshot);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Failed to refresh world time cache.", ex);
        }
    }

    private static String normalizeWorldName(String worldName) {
        if (worldName == null) {
            return null;
        }
        String trimmed = worldName.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toLowerCase(Locale.ROOT);
    }

    public static final class WorldTimeSnapshot {
        private final long ticks;
        private final long fullTime;
        private final long day;

        WorldTimeSnapshot(long ticks, long fullTime, long day) {
            this.ticks = ticks;
            this.fullTime = fullTime;
            this.day = day;
        }

        public long ticks() {
            return ticks;
        }

        public long fullTime() {
            return fullTime;
        }

        public long day() {
            return day;
        }
    }
}
