/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.cybervillage.engram;

import com.cybervillage.engram.EngramData;
import com.cybervillage.entity.DroneEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EngramEvents {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.m_9236_().f_46443_ || event.player.f_19797_ % 20 != 0) {
            return;
        }
        Player player = event.player;
        switch (EngramData.get(player)) {
            case HACKER: {
                for (Monster monster : player.m_9236_().m_45976_(Monster.class, player.m_20191_().m_82400_(24.0))) {
                    monster.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 40, 0, true, false));
                }
                break;
            }
            case NINJA: {
                player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 40, 0, true, false));
                player.m_7292_(new MobEffectInstance(MobEffects.f_19603_, 40, 1, true, false));
                break;
            }
            case DESTROYER: {
                player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 40, 0, true, false));
                player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 40, 0, true, false));
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        Player player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player && EngramData.isOverloadActive(player = (Player)livingEntity) && player.m_21223_() - event.getAmount() < 2.0f) {
            event.setAmount(Math.max(0.0f, player.m_21223_() - 2.0f));
        }
    }

    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        ServerPlayer player;
        Entity entity = event.getSource().m_7639_();
        if (!(entity instanceof ServerPlayer) || !EngramData.isOverloadActive((Player)(player = (ServerPlayer)entity))) {
            return;
        }
        ServerLevel level = player.m_284548_();
        LivingEntity victim = event.getEntity();
        level.m_8767_((ParticleOptions)ParticleTypes.f_175830_, victim.m_20185_(), victim.m_20186_() + 1.0, victim.m_20189_(), 40, 2.0, 1.0, 2.0, 0.2);
        for (Mob mob : level.m_6443_(Mob.class, victim.m_20191_().m_82400_(8.0), m -> m.m_6084_() && m != victim)) {
            float damage = mob instanceof DroneEntity ? 20.0f : 6.0f;
            mob.m_6469_(level.m_269111_().m_269548_(), damage);
            mob.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 2));
        }
    }
}

