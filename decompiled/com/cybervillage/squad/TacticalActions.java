/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package com.cybervillage.squad;

import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.warfare.CyberSamuraiEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class TacticalActions {
    private TacticalActions() {
    }

    public static void empPing(ServerPlayer player) {
        List hostiles = player.m_284548_().m_6443_(LivingEntity.class, player.m_20191_().m_82400_(48.0), e -> e instanceof NetrunnerEntity || e instanceof EnforcerEntity || e instanceof MaxTacEntity || e instanceof DroneEntity || e instanceof CyberSamuraiEntity);
        for (LivingEntity hostile : hostiles) {
            hostile.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 200));
        }
        player.m_284548_().m_5594_(null, player.m_20183_(), SoundEvents.f_215771_, SoundSource.PLAYERS, 0.4f, 2.0f);
        player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.ping", (Object[])new Object[]{hostiles.size()}).m_130940_(ChatFormatting.AQUA), true);
    }

    public static void toggleStealth(ServerPlayer player) {
        List captains = player.m_284548_().m_6443_(CaptainEntity.class, player.m_20191_().m_82400_(64.0), c -> c.isOwnedBy((Player)player));
        if (captains.isEmpty()) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_captains"), true);
            return;
        }
        boolean newState = !((CaptainEntity)((Object)captains.get(0))).isStealth();
        for (CaptainEntity captain : captains) {
            captain.setStealth(newState);
        }
        player.m_5661_((Component)Component.m_237115_((String)(newState ? "message.cybervillage.stealth_on" : "message.cybervillage.stealth_off")).m_130940_(ChatFormatting.AQUA), true);
    }
}

