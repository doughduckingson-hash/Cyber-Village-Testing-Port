/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.entity;

import com.cybervillage.registry.ModEffects;
import com.cybervillage.registry.ModItems;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.vehicle.SpiderMechEntity;
import java.util.Iterator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NetrunnerEntity
extends Zombie {
    private int hackCooldown;

    public NetrunnerEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 30.0).m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22277_, 48.0);
    }

    protected boolean m_5884_() {
        return false;
    }

    protected boolean m_7593_() {
        return false;
    }

    public void m_8107_() {
        Player player;
        super.m_8107_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.hackCooldown > 0) {
            --this.hackCooldown;
            return;
        }
        Iterator iterator = this.m_9236_().m_6443_(LivingEntity.class, this.m_20191_().m_82400_(24.0), e -> (e instanceof SquadUnitEntity || e instanceof CaptainEntity || e instanceof SpiderMechEntity) && !e.m_21023_((MobEffect)ModEffects.HACKED.get())).iterator();
        if (iterator.hasNext()) {
            LivingEntity ally = (LivingEntity)iterator.next();
            this.hackCooldown = 100;
            ally.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HACKED.get(), 200));
            Level level = this.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                Vec3 from = this.m_146892_();
                Vec3 to = ally.m_146892_();
                for (double d = 0.0; d <= 1.0; d += 0.1) {
                    Vec3 p = from.m_165921_(to, d);
                    serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_175830_, p.f_82479_, p.f_82480_, p.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
            return;
        }
        LivingEntity target = this.m_5448_();
        if (target instanceof Player && (double)this.m_20270_((Entity)(player = (Player)target)) <= 40.0) {
            this.hackCooldown = 100;
            if (this.f_19796_.m_188499_()) {
                player.m_20254_(4);
                player.m_6469_(this.m_269291_().m_269425_(), 3.0f);
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.overheat").m_130940_(ChatFormatting.RED), true);
            } else {
                ItemStack held = player.m_21205_();
                if (!held.m_41619_()) {
                    player.m_36176_(held.m_41777_(), true);
                    held.m_41764_(0);
                }
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.short_circuit").m_130940_(ChatFormatting.RED), true);
            }
        }
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHit) {
        super.m_7472_(source, looting, recentlyHit);
        this.m_19983_(new ItemStack((ItemLike)ModItems.DATA_DRIVE.get(), 1 + this.f_19796_.m_188503_(2)));
        this.m_19983_(new ItemStack((ItemLike)ModItems.EUROCREDIT.get(), 5 + this.f_19796_.m_188503_(10)));
    }
}

