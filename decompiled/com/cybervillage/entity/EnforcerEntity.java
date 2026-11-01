/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.entity;

import com.cybervillage.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnforcerEntity
extends Zombie {
    private int dashCooldown;

    public EnforcerEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)Items.f_42740_));
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42383_));
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 35.0).m_22268_(Attributes.f_22281_, 6.0).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22284_, 8.0).m_22268_(Attributes.f_22277_, 48.0);
    }

    protected boolean m_5884_() {
        return false;
    }

    protected boolean m_7593_() {
        return false;
    }

    public void m_8107_() {
        super.m_8107_();
        if (!this.m_9236_().f_46443_) {
            double distance;
            LivingEntity target;
            if (this.dashCooldown > 0) {
                --this.dashCooldown;
            }
            if ((target = this.m_5448_()) != null && this.dashCooldown <= 0 && (distance = (double)this.m_20270_((Entity)target)) > 5.0 && distance < 16.0 && this.m_142582_((Entity)target)) {
                Vec3 direction = target.m_20182_().m_82546_(this.m_20182_()).m_82541_();
                this.m_20334_(direction.f_82479_ * 1.2, 0.3, direction.f_82481_ * 1.2);
                this.dashCooldown = 80;
            }
        }
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHit) {
        super.m_7472_(source, looting, recentlyHit);
        this.m_19983_(new ItemStack((ItemLike)ModItems.EUROCREDIT.get(), 2 + this.f_19796_.m_188503_(5 + looting * 3)));
        this.m_19983_(new ItemStack((ItemLike)ModItems.ENERGY_CELL.get(), 1 + this.f_19796_.m_188503_(4)));
    }
}

