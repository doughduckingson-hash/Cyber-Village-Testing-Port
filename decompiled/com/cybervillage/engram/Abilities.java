/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.player.Player
 */
package com.cybervillage.engram;

import com.cybervillage.engram.Engram;
import com.cybervillage.engram.EngramData;
import com.cybervillage.entity.DroneEntity;
import com.cybervillage.implant.Implants;
import com.cybervillage.network.NetworkHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class Abilities {
    public static void useEngramAbility(ServerPlayer player) {
        if (!EngramData.isAbilityReady((Player)player)) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.ability_cooldown"), true);
            return;
        }
        ServerLevel level = player.m_284548_();
        switch (EngramData.get((Player)player)) {
            case HACKER: {
                int frozen = 0;
                for (Mob mob : level.m_6443_(Mob.class, player.m_20191_().m_82400_(20.0), LivingEntity::m_6084_)) {
                    mob.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 160, 10));
                    mob.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 160, 4));
                    mob.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 160));
                    if (mob instanceof DroneEntity) {
                        DroneEntity drone = (DroneEntity)mob;
                        drone.m_20242_(false);
                        drone.m_20334_(0.0, -0.5, 0.0);
                    }
                    ++frozen;
                }
                level.m_5594_(null, player.m_20183_(), SoundEvents.f_11823_, SoundSource.PLAYERS, 1.0f, 0.6f);
                player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.network_glitch", (Object[])new Object[]{frozen}).m_130940_(ChatFormatting.AQUA), true);
                EngramData.setAbilityCooldown((Player)player, 600);
                break;
            }
            case NINJA: {
                for (LivingEntity entity : level.m_6443_(LivingEntity.class, player.m_20191_().m_82400_(30.0), e -> e != player && e.m_6084_())) {
                    entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 6));
                }
                player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 100, 2));
                player.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 100, 2));
                level.m_8767_((ParticleOptions)ParticleTypes.f_123810_, player.m_20185_(), player.m_20186_() + 1.0, player.m_20189_(), 30, 1.0, 1.0, 1.0, 0.1);
                level.m_5594_(null, player.m_20183_(), SoundEvents.f_11736_, SoundSource.PLAYERS, 1.0f, 2.0f);
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.sandevistan").m_130940_(ChatFormatting.GREEN), true);
                EngramData.setAbilityCooldown((Player)player, 900);
                break;
            }
            case DESTROYER: {
                EngramData.setOverload((Player)player, 240);
                player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 240, 2));
                player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 240, 1));
                level.m_8767_((ParticleOptions)ParticleTypes.f_175830_, player.m_20185_(), player.m_20186_() + 1.0, player.m_20189_(), 60, 1.5, 1.0, 1.5, 0.3);
                level.m_5594_(null, player.m_20183_(), SoundEvents.f_215769_, SoundSource.PLAYERS, 0.8f, 1.5f);
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.overload").m_130940_(ChatFormatting.DARK_RED), true);
                EngramData.setAbilityCooldown((Player)player, 1200);
            }
        }
    }

    public static void switchEngram(ServerPlayer player) {
        Engram next = EngramData.get((Player)player).next();
        EngramData.set((Player)player, next);
        player.m_284548_().m_8767_((ParticleOptions)ParticleTypes.f_123760_, player.m_20185_(), player.m_20186_() + 1.0, player.m_20189_(), 40, 0.5, 1.0, 0.5, 0.5);
        player.m_284548_().m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 0.7f, 1.6f);
        player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.engram_switch", (Object[])new Object[]{Component.m_237115_((String)("engram.cybervillage." + next.getSerializedName()))}).m_130940_(ChatFormatting.LIGHT_PURPLE), true);
        NetworkHandler.syncToClient(player);
    }

    public static void useOpticalCamo(ServerPlayer player) {
        if (!Implants.has((Player)player, "optical_camo")) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_camo"), true);
            return;
        }
        if (!EngramData.isCamoReady((Player)player)) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.ability_cooldown"), true);
            return;
        }
        player.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 300, 0, false, false));
        for (Mob mob : player.m_284548_().m_6443_(Mob.class, player.m_20191_().m_82400_(32.0), m -> m.m_5448_() == player)) {
            mob.m_6710_(null);
        }
        player.m_284548_().m_5594_(null, player.m_20183_(), SoundEvents.f_12052_, SoundSource.PLAYERS, 0.8f, 1.4f);
        player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.camo_active"), true);
        EngramData.setCamoCooldown((Player)player, 1200);
    }
}

