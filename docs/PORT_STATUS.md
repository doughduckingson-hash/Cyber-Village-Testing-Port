# Cyber Village 2077 — Port Status

Target: Minecraft 1.21.1 with NeoForge (Java 21).

## Retained scope
- Hostile mobs and their AI/combat behavior and animations.
- World-generation structures and the data/assets they require, if confirmed in the source artifact or companion content.

## Removed scope
- Player-facing weapons, tools, armor, cyberware, ingredients, recipes, progression items, and unwanted acquisition paths.

## Source of truth
- `Memory-Deposit/cybervillage-1.4.0.jar` is the read-only source reference.
- The same archived JAR is present in this development repository for traceability.
- All implementation changes belong in this repository; the archive repository must not be modified.

## Current implementation state
- [x] Source JAR located and archive inventory recorded.
- [x] NeoForge 1.21.1 / Java 21 Gradle project scaffold added.
- [ ] Extract exact source metadata and dependency versions.
- [ ] Map source entity registrations, constructors, attributes, AI goals, renderers, and networking dependencies.
- [ ] Confirm whether structures are generated in code or supplied by a companion datapack.
- [ ] Port retained entities and their minimum required systems.
- [ ] Remove player-facing content and acquisition paths without breaking retained NPC behavior.
- [ ] Run Gradle compile, dedicated-server startup, and in-game validation.

No successful compile or runtime validation is claimed yet. The scaffold is not the completed mod port.
