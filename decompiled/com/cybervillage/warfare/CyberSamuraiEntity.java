/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.warfare;

import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CyberSamuraiEntity
extends Zombie {
    private final ServerBossEvent bossEvent = new ServerBossEvent(this.m_5446_(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6);
    private int blinkCooldown;

    public CyberSamuraiEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42393_));
        this.m_21530_();
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 200.0).m_22268_(Attributes.f_22279_, 0.36).m_22268_(Attributes.f_22284_, 14.0).m_22268_(Attributes.f_22281_, 12.0).m_22268_(Attributes.f_22277_, 48.0).m_22268_(Attributes.f_22278_, 0.8);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(2, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.2, true));
        this.f_21345_.m_25352_(5, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.9));
        this.f_21345_.m_25352_(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 12.0f));
        this.f_21346_.m_25352_(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal((Mob)this, CaptainEntity.class, false));
        this.f_21346_.m_25352_(3, (Goal)new NearestAttackableTargetGoal((Mob)this, SquadUnitEntity.class, false));
        this.f_21346_.m_25352_(4, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, false));
    }

    protected void m_6878_() {
    }

    protected boolean m_5884_() {
        return false;
    }

    protected boolean m_7593_() {
        return false;
    }

    public void m_8107_() {
        Level level;
        super.m_8107_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
        if (this.f_19797_ % 3 == 0 && (level = this.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123771_, this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_(), 3, 0.3, 0.7, 0.3, 0.0);
        }
        if (this.blinkCooldown > 0) {
            --this.blinkCooldown;
            return;
        }
        LivingEntity victim = null;
        List captains = this.m_9236_().m_6443_(CaptainEntity.class, this.m_20191_().m_82400_(24.0), LivingEntity::m_6084_);
        if (!captains.isEmpty()) {
            victim = (LivingEntity)captains.get(this.f_19796_.m_188503_(captains.size()));
        } else if (this.m_5448_() != null && (double)this.m_20270_((Entity)this.m_5448_()) < 24.0) {
            victim = this.m_5448_();
        }
        if (victim != null) {
            this.blinkCooldown = 100;
            Vec3 behind = victim.m_20182_().m_82546_(victim.m_20154_().m_82490_(1.5));
            Level level2 = this.m_9236_();
            if (level2 instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level2;
                serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123760_, this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_(), 20, 0.3, 0.7, 0.3, 0.1);
            }
            this.m_6021_(behind.f_82479_, victim.m_20186_(), behind.f_82481_);
            this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11852_, SoundSource.HOSTILE, 1.0f, 0.7f);
            this.m_6710_(victim);
            this.m_7327_((Entity)victim);
        }
    }

    public void m_6457_(ServerPlayer player) {
        super.m_6457_(player);
        this.bossEvent.m_6543_(player);
    }

    public void m_6452_(ServerPlayer player) {
        super.m_6452_(player);
        this.bossEvent.m_6539_(player);
    }

    public boolean m_6072_() {
        return false;
    }
}

