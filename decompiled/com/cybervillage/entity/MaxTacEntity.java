/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.entity;

import com.cybervillage.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class MaxTacEntity
extends Zombie {
    public MaxTacEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42393_));
        this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)Items.f_42480_));
        this.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)Items.f_42481_));
        this.f_21364_ = 40;
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 80.0).m_22268_(Attributes.f_22281_, 10.0).m_22268_(Attributes.f_22279_, 0.35).m_22268_(Attributes.f_22284_, 14.0).m_22268_(Attributes.f_22278_, 0.6).m_22268_(Attributes.f_22277_, 64.0);
    }

    protected boolean m_5884_() {
        return false;
    }

    protected boolean m_7593_() {
        return false;
    }

    public boolean m_6469_(DamageSource source, float amount) {
        if (!this.m_9236_().f_46443_ && source.m_7640_() instanceof Projectile && this.f_19796_.m_188501_() < 0.8f) {
            double dx = (this.f_19796_.m_188500_() - 0.5) * 6.0;
            double dz = (this.f_19796_.m_188500_() - 0.5) * 6.0;
            this.m_20984_(this.m_20185_() + dx, this.m_20186_(), this.m_20189_() + dz, true);
            return false;
        }
        return super.m_6469_(source, amount);
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHit) {
        super.m_7472_(source, looting, recentlyHit);
        this.m_19983_(new ItemStack((ItemLike)ModItems.EUROCREDIT.get(), 10 + this.f_19796_.m_188503_(20)));
        this.m_19983_(new ItemStack((ItemLike)ModItems.DATA_DRIVE.get(), 1 + this.f_19796_.m_188503_(3)));
        if (this.f_19796_.m_188501_() < 0.3f) {
            this.m_19983_(new ItemStack((ItemLike)ModItems.MANTIS_BLADES.get()));
        }
    }

    public boolean m_6785_(double distance) {
        return false;
    }
}

