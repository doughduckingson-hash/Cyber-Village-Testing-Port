/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingFallEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$Clone
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.cybervillage.implant;

import com.cybervillage.implant.Implants;
import com.cybervillage.network.NetworkHandler;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ImplantEvents {
    private static final String TAG_HEART_COOLDOWN = "SecondHeartReadyAt";

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.m_9236_().f_46443_ || event.player.f_19797_ % 20 != 0) {
            return;
        }
        Player player = event.player;
        if (Implants.has(player, "kiroshi_eyes")) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 400, 0, true, false));
        }
        if (Implants.has(player, "sapsan_stims")) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 40, 1, true, false));
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        Player player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player && Implants.has(player = (Player)livingEntity, "reinforced_tendons")) {
            event.setDamageMultiplier(event.getDamageMultiplier() * 0.2f);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        ServerPlayer player;
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof ServerPlayer) || !Implants.has((Player)(player = (ServerPlayer)livingEntity), "second_heart")) {
            return;
        }
        CompoundTag tag = Implants.root((Player)player);
        long readyAt = tag.m_128454_(TAG_HEART_COOLDOWN);
        long now = player.m_9236_().m_46467_();
        if (now < readyAt) {
            return;
        }
        tag.m_128356_(TAG_HEART_COOLDOWN, now + 12000L);
        event.setCanceled(true);
        player.m_21153_(player.m_21233_() * 0.5f);
        player.m_21219_();
        player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 60, 4));
        ServerLevel level = player.m_284548_();
        for (LivingEntity target : level.m_6443_(LivingEntity.class, player.m_20191_().m_82400_(6.0), e -> e != player && e.m_6084_())) {
            Vec3 away = target.m_20182_().m_82546_(player.m_20182_()).m_82541_();
            target.m_20334_(away.f_82479_ * 1.5, 0.6, away.f_82481_ * 1.5);
            target.m_6469_(level.m_269111_().m_269548_(), 4.0f);
        }
        level.m_8767_((ParticleOptions)ParticleTypes.f_123747_, player.m_20185_(), player.m_20186_() + 1.0, player.m_20189_(), 3, 0.0, 0.0, 0.0, 0.0);
        level.m_5594_(null, player.m_20183_(), SoundEvents.f_12513_, SoundSource.PLAYERS, 1.0f, 1.5f);
        player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.second_heart"), false);
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            NetworkHandler.syncToClient(player2);
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        CompoundTag oldData = event.getOriginal().getPersistentData();
        if (oldData.m_128441_("PlayerPersisted")) {
            event.getEntity().getPersistentData().m_128365_("PlayerPersisted", (Tag)oldData.m_128469_("PlayerPersisted").m_6426_());
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            NetworkHandler.syncToClient(player2);
        }
    }
}

