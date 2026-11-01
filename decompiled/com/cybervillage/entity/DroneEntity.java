/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.monster.Vex
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.entity;

import com.cybervillage.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class DroneEntity
extends Vex {
    public DroneEntity(EntityType<? extends Vex> type, Level level) {
        super(type, level);
        this.m_33987_(12000);
    }

    public static AttributeSupplier.Builder m_34040_() {
        return Vex.m_34040_().m_22268_(Attributes.f_22276_, 12.0).m_22268_(Attributes.f_22281_, 1.0);
    }

    public boolean m_7327_(Entity target) {
        boolean hit = super.m_7327_(target);
        if (hit && target instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)target;
            living.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 100));
            living.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 1));
        }
        return hit;
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHit) {
        super.m_7472_(source, looting, recentlyHit);
        this.m_19983_(new ItemStack((ItemLike)ModItems.ENERGY_CELL.get(), 2 + this.f_19796_.m_188503_(4)));
        if (this.f_19796_.m_188501_() < 0.25f) {
            this.m_19983_(new ItemStack((ItemLike)ModItems.DATA_DRIVE.get()));
        }
    }
}

