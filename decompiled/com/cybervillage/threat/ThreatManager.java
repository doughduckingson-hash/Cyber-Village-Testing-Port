/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LightningBolt
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 */
package com.cybervillage.threat;

import com.cybervillage.registry.ModEntities;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class ThreatManager {
    public static final int MAX_LEVEL = 5;
    private static final int HEAT_PER_LEVEL = 10;
    private static final int DECAY_INTERVAL = 1200;
    private static final int WAVE_INTERVAL = 800;
    private static final int ORBITAL_INTERVAL = 300;
    private static final Map<UUID, State> STATES = new HashMap<UUID, State>();

    public static int getLevel(ServerPlayer player) {
        State state = STATES.get(player.m_20148_());
        return state == null ? 0 : Math.min(5, state.heat / 10);
    }

    public static void addHeat(ServerPlayer player, int amount) {
        State state = STATES.computeIfAbsent(player.m_20148_(), id -> new State());
        int before = Math.min(5, state.heat / 10);
        state.heat = Math.min(state.heat + amount, 50);
        state.ticksSinceHeat = 0;
        int after = Math.min(5, state.heat / 10);
        if (after > before) {
            player.m_5661_((Component)Component.m_237110_((String)"threat.cybervillage.raised", (Object[])new Object[]{after}).m_130944_(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD}), false);
            player.m_284548_().m_5594_(null, player.m_20183_(), SoundEvents.f_11700_, SoundSource.HOSTILE, 1.5f, 0.6f);
        }
    }

    public static void setLevel(ServerPlayer player, int level) {
        State state = STATES.computeIfAbsent(player.m_20148_(), id -> new State());
        state.heat = Math.max(0, Math.min(5, level)) * 10;
        state.ticksSinceHeat = 0;
    }

    public static void tick(MinecraftServer server) {
        for (ServerPlayer player : server.m_6846_().m_11314_()) {
            State state = STATES.get(player.m_20148_());
            if (state == null) continue;
            int level = Math.min(5, state.heat / 10);
            ThreatManager.updateBar(player, state, level);
            if (level <= 0) continue;
            ++state.ticksSinceHeat;
            if (state.ticksSinceHeat >= 1200) {
                state.ticksSinceHeat = 0;
                state.heat = Math.max(0, state.heat - 10);
                continue;
            }
            if (--state.waveTimer <= 0) {
                state.waveTimer = 800;
                ThreatManager.spawnResponse(player, level);
            }
            if (level < 5 || --state.orbitalTimer > 0) continue;
            state.orbitalTimer = 300;
            ThreatManager.orbitalStrike(player);
        }
    }

    private static void updateBar(ServerPlayer player, State state, int level) {
        if (level <= 0) {
            state.bar.m_6539_(player);
            return;
        }
        state.bar.m_6543_(player);
        state.bar.m_142711_((float)level / 5.0f);
        state.bar.m_6456_((Component)Component.m_237110_((String)"threat.cybervillage.bar", (Object[])new Object[]{"\u25c6".repeat(level), level}).m_130940_(ChatFormatting.LIGHT_PURPLE));
    }

    private static void spawnResponse(ServerPlayer player, int level) {
        ServerLevel serverLevel = player.m_284548_();
        if (level >= 1) {
            ThreatManager.spawn(serverLevel, player, (EntityType)ModEntities.DRONE.get(), 2, true);
        }
        if (level >= 2) {
            ThreatManager.spawn(serverLevel, player, (EntityType)ModEntities.ENFORCER.get(), 2, false);
        }
        if (level >= 3) {
            ThreatManager.spawn(serverLevel, player, (EntityType)ModEntities.NETRUNNER.get(), 1, false);
        }
        if (level >= 4) {
            ThreatManager.spawn(serverLevel, player, (EntityType)ModEntities.MAXTAC.get(), 2, false);
            player.m_5661_((Component)Component.m_237115_((String)"threat.cybervillage.maxtac").m_130940_(ChatFormatting.DARK_RED), true);
        }
    }

    private static <T extends Mob> void spawn(ServerLevel level, ServerPlayer player, EntityType<T> type, int count, boolean airborne) {
        for (int i = 0; i < count; ++i) {
            double angle = level.f_46441_.m_188500_() * Math.PI * 2.0;
            int radius = 15 + level.f_46441_.m_188503_(15);
            int x = player.m_146903_() + (int)(Math.cos(angle) * (double)radius);
            int z = player.m_146907_() + (int)(Math.sin(angle) * (double)radius);
            int y = level.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z);
            Mob mob = (Mob)type.m_20615_((Level)level);
            if (mob == null) continue;
            mob.m_7678_((double)x + 0.5, (double)(y + (airborne ? 6 : 0)), (double)z + 0.5, level.f_46441_.m_188501_() * 360.0f, 0.0f);
            mob.m_6518_((ServerLevelAccessor)level, level.m_6436_(new BlockPos(x, y, z)), MobSpawnType.EVENT, null, null);
            mob.m_6710_((LivingEntity)player);
            mob.m_21530_();
            level.m_7967_((Entity)mob);
        }
    }

    private static void orbitalStrike(ServerPlayer player) {
        ServerLevel level = player.m_284548_();
        double x = player.m_20185_();
        double y = player.m_20186_();
        double z = player.m_20189_();
        LightningBolt bolt = (LightningBolt)EntityType.f_20465_.m_20615_((Level)level);
        if (bolt != null) {
            bolt.m_6027_(x, y, z);
            bolt.m_20874_(true);
            level.m_7967_((Entity)bolt);
        }
        level.m_8767_((ParticleOptions)ParticleTypes.f_235902_, x, y + 1.0, z, 5, 1.0, 1.0, 1.0, 0.0);
        level.m_255391_(null, x, y, z, 6.0f, true, Level.ExplosionInteraction.TNT);
        player.m_5661_((Component)Component.m_237115_((String)"threat.cybervillage.orbital").m_130944_(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD}), true);
    }

    private static class State {
        int heat;
        int ticksSinceHeat;
        int waveTimer = 100;
        int orbitalTimer = 300;
        final ServerBossEvent bar = new ServerBossEvent((Component)Component.m_237119_(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_10);

        private State() {
        }
    }
}

