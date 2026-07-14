# CurseForge Release Checklist

Use this when creating the CurseForge project for `WorldTimePlaceholders`.

## Project Fields

- Game: `Minecraft`
- Class: `Plugin`
- Project name: `WorldTimePlaceholders`
- Summary: `Safe PlaceholderAPI world-time placeholders for MOTD and server-list pings.`
- Main category: `Utility`
- Additional categories: `Server Utility` if available, otherwise leave empty
- Experimental: `Off`
- Allow comments: `On`
- License: `GNU GPLv3` or the equivalent GPL-3.0 option

## Recommended Links

- Website: `https://github.com/v3sker/WorldTime`
- Source: `git@github.com:v3sker/WorldTime.git`
- Issues tracker: GitHub issues for the same repository

## File Upload

- Upload the built jar from `target/WorldTimePlaceholders-1.0.0.jar`
- Release type: `Release`
- Supported Minecraft versions: select only the versions you have actually tested

## Description Text

Use a short opening paragraph like this:

`WorldTimePlaceholders exposes safe PlaceholderAPI placeholders for Minecraft world time in MOTD and server-list ping contexts. It caches world values on the main thread so it can be used where there is no real Bukkit player.`

Then include:

- Install requirements: Paper and PlaceholderAPI
- Example placeholders: `%WorldTimePlaceholders_12_world%`, `%WorldTimePlaceholders_24_world%`
- Notes about world names being case-insensitive
- A warning not to use PlaceholderAPI's external `World` expansion for MOTD/server-list pings

## Assets

- Prepare a 400x400 original PNG logo before submission
- Add at least one screenshot if you want the project page to look complete

## Moderation Notes

- Keep the name unique and avoid version numbers in the project title
- Do not mark the project experimental unless you want it excluded from CurseForge ecosystem sync
- Do not claim support for Minecraft versions you have not tested
