/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.item.FallingBlockEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.entity;

import com.cybervillage.registry.ModEntities;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.vehicle.SpiderMechEntity;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SmartBulletEntity
extends AbstractArrow {
    private boolean homing;
    private double homingRange = 12.0;
    private boolean heavy;

    public SmartBulletEntity(EntityType<? extends SmartBulletEntity> type, Level level) {
        super(type, level);
    }

    public SmartBulletEntity(Level level, LivingEntity shooter, double damage, boolean homing, double homingRange) {
        super((EntityType)ModEntities.SMART_BULLET.get(), shooter, level);
        this.m_36781_(damage);
        this.homing = homing;
        this.homingRange = homingRange;
        this.f_36705_ = AbstractArrow.Pickup.DISALLOWED;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    protected void m_8060_(BlockHitResult result) {
        if (this.heavy && !this.m_9236_().f_46443_) {
            BlockPos pos = result.m_82425_();
            BlockState state = this.m_9236_().m_8055_(pos);
            float hardness = state.m_60800_((BlockGetter)this.m_9236_(), pos);
            if (hardness >= 0.0f && hardness <= 3.0f && !state.m_60795_()) {
                FallingBlockEntity.m_201971_((Level)this.m_9236_(), (BlockPos)pos, (BlockState)state);
                this.m_9236_().m_7471_(pos, false);
                this.m_146870_();
                return;
            }
        }
        super.m_8060_(result);
    }

    public void m_8119_() {
        LivingEntity target;
        super.m_8119_();
        if (this.f_19797_ > 60) {
            this.m_146870_();
            return;
        }
        if (!this.m_9236_().f_46443_ && this.homing && !this.f_36703_ && (target = this.findTarget()) != null) {
            Vec3 toTarget = target.m_146892_().m_82546_(this.m_20182_()).m_82541_();
            Vec3 velocity = this.m_20184_();
            double speed = velocity.m_82553_();
            Vec3 steered = velocity.m_82541_().m_165921_(toTarget, 0.3).m_82541_().m_82490_(speed);
            this.m_20256_(steered);
        }
        if (this.m_9236_().f_46443_ && !this.f_36703_) {
            this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_175830_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
        }
    }

    private LivingEntity findTarget() {
        List candidates = this.m_9236_().m_6443_(LivingEntity.class, new AABB(this.m_20183_()).m_82400_(this.homingRange), e -> e.m_6084_() && e != this.m_19749_() && e instanceof Mob && !(e instanceof Player) && !(e instanceof CaptainEntity) && !(e instanceof SquadUnitEntity) && !(e instanceof SpiderMechEntity));
        return candidates.stream().filter(e -> {
            Vec3 to;
            Vec3 dir = this.m_20184_().m_82541_();
            return dir.m_82526_(to = e.m_20182_().m_82546_(this.m_20182_()).m_82541_()) > 0.3;
        }).min(Comparator.comparingDouble(arg_0 -> ((SmartBulletEntity)this).m_20280_(arg_0))).orElse(null);
    }

    protected ItemStack m_7941_() {
        return ItemStack.f_41583_;
    }
}

