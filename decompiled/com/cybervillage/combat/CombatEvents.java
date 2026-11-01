/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingTickEvent
 *  net.minecraftforge.event.entity.living.LivingHurtEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.cybervillage.combat;

import com.cybervillage.energy.EnergyGrid;
import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.vehicle.SpiderMechEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CombatEvents {
    private static final String TAG_DETECTION = "CyberDetection";
    private static final int DETECTION_FULL = 10;

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (target.m_9236_().f_46443_ || !CombatEvents.isCyberUnit(target)) {
            return;
        }
        Entity entity = event.getSource().m_7640_();
        if (!(entity instanceof Projectile)) {
            return;
        }
        Projectile projectile = (Projectile)entity;
        double frac = (projectile.m_20186_() - target.m_20186_()) / Math.max(0.1, (double)target.m_20206_());
        if (frac < 0.35) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 200, 2));
            if (target instanceof SpiderMechEntity) {
                SpiderMechEntity mech = (SpiderMechEntity)target;
                mech.m_274367_(0.6f);
            }
        } else if (frac > 0.75) {
            if (target.m_217043_().m_188501_() < 0.5f) {
                event.setAmount(event.getAmount() * 1.5f);
            } else {
                target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 5));
                target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 60, 5));
            }
        } else if (frac > 0.5) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 200, 1));
        }
    }

    private static boolean isCyberUnit(LivingEntity entity) {
        return entity instanceof SquadUnitEntity || entity instanceof CaptainEntity || entity instanceof SpiderMechEntity || entity instanceof EnforcerEntity || entity instanceof MaxTacEntity || entity instanceof DroneEntity;
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingTickEvent event) {
        Mob patrol;
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof Mob) || !((patrol = (Mob)livingEntity) instanceof EnforcerEntity) && !(patrol instanceof MaxTacEntity) || patrol.m_9236_().f_46443_ || patrol.f_19797_ % 4 != 0) {
            return;
        }
        ServerLevel level = (ServerLevel)patrol.m_9236_();
        if (EnergyGrid.isJammed(level, patrol.m_20182_())) {
            return;
        }
        for (LivingEntity intruder : level.m_6443_(LivingEntity.class, patrol.m_20191_().m_82400_(24.0), e -> e instanceof SquadUnitEntity || e instanceof CaptainEntity)) {
            if (!CombatEvents.inVisionCone(patrol, intruder) || !patrol.m_142582_((Entity)intruder)) continue;
            boolean stealth = intruder.m_6144_();
            int fill = stealth && intruder.m_217043_().m_188499_() ? 0 : 1;
            int detection = intruder.getPersistentData().m_128451_(TAG_DETECTION) + fill;
            if (detection >= 10) {
                intruder.getPersistentData().m_128405_(TAG_DETECTION, 0);
                patrol.m_6710_(intruder);
                level.m_8767_((ParticleOptions)ParticleTypes.f_123792_, patrol.m_20185_(), patrol.m_20188_() + 0.5, patrol.m_20189_(), 3, 0.2, 0.2, 0.2, 0.0);
                continue;
            }
            intruder.getPersistentData().m_128405_(TAG_DETECTION, detection);
            level.m_8767_((ParticleOptions)ParticleTypes.f_123797_, intruder.m_20185_(), intruder.m_20186_() + (double)intruder.m_20206_() + 0.4, intruder.m_20189_(), Math.max(1, detection / 2), 0.2, 0.05, 0.2, 0.0);
        }
    }

    private static boolean inVisionCone(Mob patrol, LivingEntity target) {
        Vec3 dirFlat;
        Vec3 look = patrol.m_20252_(1.0f);
        Vec3 to = target.m_146892_().m_82546_(patrol.m_146892_());
        double dist = to.m_82553_();
        if (dist > 24.0 || dist < 0.001) {
            return false;
        }
        Vec3 dir = to.m_82490_(1.0 / dist);
        Vec3 lookFlat = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
        if (lookFlat.m_82526_(dirFlat = new Vec3(dir.f_82479_, 0.0, dir.f_82481_).m_82541_()) < Math.cos(Math.toRadians(45.0))) {
            return false;
        }
        double pitch = Math.toDegrees(Math.asin(Math.abs(dir.f_82480_)));
        return pitch <= 45.0;
    }
}

