package com.v3sker.worldtime;

import java.util.Locale;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public final class MotdWorldTimeExpansion extends PlaceholderExpansion {

    private static final String PREFIX_12 = "12_";
    private static final String PREFIX_24 = "24_";
    private static final String PREFIX_TICKS = "ticks_";
    private static final String PREFIX_DAY = "day_";

    public MotdWorldTimeExpansion() {
    }

    @Override
    public String getIdentifier() {
        return "worldtimeplaceholders";
    }

    @Override
    public String getAuthor() {
        return "v3sker";
    }

    @Override
    public String getVersion() {
        MotdWorldTimePlugin current = MotdWorldTimePlugin.getInstance();
        return current != null ? current.getDescription().getVersion() : "unknown";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String input = normalizeParams(params);
        if (input == null) {
            return "";
        }

        if (input.startsWith(PREFIX_12)) {
            return format12Hour(resolveSnapshot(input.substring(PREFIX_12.length())));
        }
        if (input.startsWith(PREFIX_24)) {
            return format24Hour(resolveSnapshot(input.substring(PREFIX_24.length())));
        }
        if (input.startsWith(PREFIX_TICKS)) {
            return formatTicks(resolveSnapshot(input.substring(PREFIX_TICKS.length())));
        }
        if (input.startsWith(PREFIX_DAY)) {
            return formatDay(resolveSnapshot(input.substring(PREFIX_DAY.length())));
        }
        return "";
    }

    private MotdWorldTimePlugin.WorldTimeSnapshot resolveSnapshot(String worldName) {
        MotdWorldTimePlugin current = MotdWorldTimePlugin.getInstance();
        if (current == null) {
            return null;
        }
        return current.getCachedWorldTime(worldName);
    }

    private static String normalizeParams(String params) {
        if (params == null) {
            return null;
        }
        String trimmed = params.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toLowerCase(Locale.ROOT);
    }

    private static String format12Hour(MotdWorldTimePlugin.WorldTimeSnapshot snapshot) {
        if (snapshot == null) {
            return "";
        }
        long totalMinutes = ((snapshot.ticks() + 6000L) % 24000L) * 1440L / 24000L;
        long hour24 = totalMinutes / 60L;
        long minute = totalMinutes % 60L;
        String period = hour24 >= 12L ? "PM" : "AM";
        long hour12 = hour24 % 12L;
        if (hour12 == 0L) {
            hour12 = 12L;
        }
        return hour12 + ":" + pad2(minute) + " " + period;
    }

    private static String format24Hour(MotdWorldTimePlugin.WorldTimeSnapshot snapshot) {
        if (snapshot == null) {
            return "";
        }
        long totalMinutes = ((snapshot.ticks() + 6000L) % 24000L) * 1440L / 24000L;
        long hour = totalMinutes / 60L;
        long minute = totalMinutes % 60L;
        return pad2(hour) + ":" + pad2(minute);
    }

    private static String formatTicks(MotdWorldTimePlugin.WorldTimeSnapshot snapshot) {
        return snapshot == null ? "" : Long.toString(snapshot.ticks());
    }

    private static String formatDay(MotdWorldTimePlugin.WorldTimeSnapshot snapshot) {
        return snapshot == null ? "" : Long.toString(snapshot.day());
    }

    private static String pad2(long value) {
        return value < 10L ? "0" + value : Long.toString(value);
    }
}
