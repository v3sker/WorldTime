# WorldTime

MotdWorldTime is a small Minecraft Paper plugin that exposes safe PlaceholderAPI placeholders for Minecraft world time in MOTD and server-list ping contexts.

Repository: https://github.com/v3sker/WorldTime

It exists for setups like AdvancedServerList where placeholders are resolved during server-list pings and there is no real Bukkit `Player`. This plugin never casts to `Player`, and it does not read live world state from the PlaceholderAPI request path. World values are cached once per second on the main server thread.

## Build

```bash
mvn clean package
```

## Install

1. Build the plugin.
2. Copy the generated jar from `target/motd-worldtime-1.0.0.jar` into your server's `plugins/` folder.
3. Install PlaceholderAPI on the server.
4. Start or restart the server.

## Example AdvancedServerList usage

Use these placeholders in your MOTD or ping formatting:

```text
%motdworldtime_12_world%
%motdworldtime_24_world%
%motdworldtime_ticks_world%
%motdworldtime_day_world%
```

World names with underscores are supported:

```text
%motdworldtime_12_world_nether%
%motdworldtime_24_world_the_end%
%motdworldtime_ticks_world_nether%
%motdworldtime_day_world_the_end%
```

## Placeholders

- `%motdworldtime_12_<world>%` - 12-hour time, such as `6:00 AM`
- `%motdworldtime_24_<world>%` - 24-hour time, such as `13:23`
- `%motdworldtime_ticks_<world>%` - Raw `World#getTime()` ticks
- `%motdworldtime_day_<world>%` - Full day count from `World#getFullTime() / 24000`

## Notes

- World lookup is case-insensitive and stored in lowercase with `Locale.ROOT`.
- Unknown worlds return an empty string.
- Values are refreshed once per second, every 20 ticks.
- Do not use PlaceholderAPI's external `World` expansion for MOTD/server-list
  world-time placeholders. Some versions cast the PlaceholderAPI
  `OfflinePlayer` to a live Bukkit `Player`, which fails during server-list
  pings because there is no real player for that request. Use this plugin's
  `%motdworldtime_*%` placeholders instead.
