/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MoveFunction
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.Spider
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.vehicle;

import com.cybervillage.entity.SmartBulletEntity;
import com.cybervillage.registry.ModEffects;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpiderMechEntity
extends Spider {
    private int turretCooldown;

    public SpiderMechEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
        this.m_274367_(2.5f);
        this.m_21530_();
    }

    public static AttributeSupplier.Builder m_33815_() {
        return Spider.m_33815_().m_22268_(Attributes.f_22276_, 100.0).m_22268_(Attributes.f_22279_, 0.38).m_22268_(Attributes.f_22284_, 16.0).m_22268_(Attributes.f_22278_, 1.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
    }

    protected boolean m_7310_(Entity passenger) {
        return this.m_20197_().size() < 2;
    }

    protected void m_19956_(Entity passenger, Entity.MoveFunction moveFunction) {
        if (!this.m_20363_(passenger)) {
            return;
        }
        int index = this.m_20197_().indexOf(passenger);
        double yawRad = Math.toRadians(this.m_146908_());
        double forward = index == 0 ? 0.4 : -0.7;
        double x = this.m_20185_() - Math.sin(yawRad) * forward;
        double z = this.m_20189_() + Math.cos(yawRad) * forward;
        double y = this.m_20186_() + this.m_6048_() + passenger.m_6049_() + (index == 1 ? 0.4 : 0.0);
        moveFunction.m_20372_(passenger, x, y, z);
    }

    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (this.m_20197_().size() < 2 && !player.m_6144_()) {
            if (!this.m_9236_().f_46443_) {
                player.m_20329_((Entity)this);
            }
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return super.m_6071_(player, hand);
    }

    @Nullable
    public LivingEntity m_6688_() {
        Player player;
        Entity entity = this.m_146895_();
        return entity instanceof Player ? (player = (Player)entity) : null;
    }

    protected Vec3 m_274312_(Player player, Vec3 travelVector) {
        return new Vec3((double)(player.f_20900_ * 0.5f), 0.0, (double)player.f_20902_);
    }

    protected void m_274498_(Player player, Vec3 travelVector) {
        super.m_274498_(player, travelVector);
        this.m_19915_(player.m_146908_(), player.m_146909_() * 0.5f);
        this.f_20883_ = this.f_20885_ = this.m_146908_();
        this.f_19859_ = this.f_20885_;
    }

    protected float m_245547_(Player player) {
        if (this.m_21023_((MobEffect)ModEffects.HACKED.get())) {
            return 0.0f;
        }
        return (float)this.m_21133_(Attributes.f_22279_);
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.m_9236_().f_46443_ && this.m_21023_((MobEffect)ModEffects.HACKED.get()) && this.m_20160_()) {
            this.m_20153_();
        }
        this.tickTurret();
    }

    private void tickTurret() {
        Object e;
        if (this.m_9236_().f_46443_ || this.turretCooldown-- > 0) {
            return;
        }
        if (this.m_20197_().size() <= 1 || !((e = this.m_20197_().get(1)) instanceof Player)) {
            return;
        }
        Player gunner = (Player)e;
        if (this.m_21023_((MobEffect)ModEffects.HACKED.get())) {
            return;
        }
        LivingEntity target = this.m_9236_().m_6443_(Monster.class, this.m_20191_().m_82400_(24.0), m -> m.m_6084_() && m != this && !(m instanceof CaptainEntity) && !(m instanceof SquadUnitEntity) && !(m instanceof SpiderMechEntity) && gunner.m_20154_().m_82526_(m.m_20182_().m_82546_(gunner.m_20182_()).m_82541_()) > 0.5).stream().min(Comparator.comparingDouble(arg_0 -> ((SpiderMechEntity)this).m_20280_(arg_0))).orElse(null);
        if (target == null || !this.m_142582_((Entity)target)) {
            return;
        }
        this.turretCooldown = 10;
        SmartBulletEntity bullet = new SmartBulletEntity(this.m_9236_(), (LivingEntity)gunner, 6.0, false, 0.0);
        bullet.setHeavy(true);
        bullet.m_6034_(this.m_20185_(), this.m_20186_() + 1.6, this.m_20189_());
        Vec3 dir = target.m_146892_().m_82492_(this.m_20185_(), this.m_20186_() + 1.6, this.m_20189_()).m_82541_();
        bullet.m_6686_(dir.f_82479_, dir.f_82480_, dir.f_82481_, 3.0f, 1.0f);
        this.m_9236_().m_7967_((Entity)bullet);
        this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11738_, SoundSource.NEUTRAL, 0.5f, 1.6f);
    }

    public boolean m_20068_() {
        return false;
    }
}

