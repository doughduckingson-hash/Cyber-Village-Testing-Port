# Cyber Village 2077 — Binary Inventory

Source artifact: `Memory-Deposit/cybervillage-1.4.0.jar`
Artifact size: 217,220 bytes
Git blob: `1e63787390f30ff66831482a5acf1f89ff68451c`
Target port: Minecraft 1.21.1 / NeoForge

## Confirmed archive contents

The source archive is a Java mod JAR containing 205 ZIP entries. It includes:

- Forge-style metadata at `META-INF/mods.toml` (4,801 uncompressed bytes).
- Main mod entry classes under `com/cybervillage/`.
- Entity implementations: Drone, Enforcer, MaxTac, Netrunner, SmartBullet, Captain, SquadUnit, SpiderMech, and CyberSamurai.
- Entity/model support: MaxTac model and renderer; additional client renderers and screens.
- Combat, threat, squad, warfare, energy-grid, implant, engram, and networking systems.
- Registries for blocks, block entities, effects, entities, items, menus, and creative tabs.
- Resource assets under `assets/cybervillage/`.
- Data resources include block loot tables and recipes.

## Initial scope assessment

Likely retained entity/combat candidates:
- Drone, Enforcer, MaxTac, Netrunner
- Captain and SquadUnit
- SpiderMech and CyberSamurai
- Threat, combat, squad, and warfare logic only where required for hostile NPC behavior

Likely excluded player-facing systems:
- Weapon item classes and weapon crafting recipes
- Implant/cyberware and engram acquisition systems
- Tactical tablet/contracts/chips where they exist only as player progression or interaction items
- Weapon terminal UI/menu and player-facing crafting/progression content

Internal blocks, effects, items, packets, and registries must be evaluated by reference before removal; some may be dependencies of NPC behavior.

## Important finding: structures

The archive inventory contains no obvious `data/cybervillage/worldgen/`, structure NBT, structure template, or structure-set entries. The visible data tree contains block loot tables and recipes. Therefore structure generation is **not yet confirmed to exist in this artifact**. This must be checked against the mod's bytecode and any companion datapacks before implementing or claiming structure preservation.

## Next technical steps

1. Extract and inspect `mods.toml` and `MANIFEST.MF` to establish the exact Minecraft version, loader, mod ID, and dependencies.
2. Decompile class files to map registrations and entity AI/goal dependencies.
3. Determine whether structures are generated in code or supplied separately.
4. Build a minimal NeoForge 1.21.1 project and port only the retained dependency graph.
5. Compile and run client/server validation before calling the port complete.

This is an inventory of the archived binary, not a claim that the port has been implemented or built.
