/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.weapon;

import com.cybervillage.entity.SmartBulletEntity;
import com.cybervillage.implant.Implants;
import com.cybervillage.registry.ModItems;
import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.GunAttachments;
import com.cybervillage.weapon.WeaponUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SmartGunItem
extends Item {
    private final float damage;
    private final int cooldown;
    private final int magSize;
    private final int pellets;

    public SmartGunItem(Item.Properties properties, float damage, int cooldown, int magSize, int pellets) {
        super(properties.m_41487_(1));
        this.damage = damage;
        this.cooldown = cooldown;
        this.magSize = magSize;
        this.pellets = pellets;
    }

    public int getMagSize() {
        return this.magSize;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack gun = player.m_21120_(hand);
        if (player.m_6144_()) {
            if (!level.f_46443_) {
                WeaponUtil.reload(level, player, gun, this.magSize, (Item)ModItems.ENERGY_CELL.get());
            }
            return InteractionResultHolder.m_19096_((Object)gun);
        }
        if (GunAttachments.getAmmo(gun) <= 0) {
            if (!level.f_46443_) {
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_charge"), true);
            }
            return InteractionResultHolder.m_19100_((Object)gun);
        }
        if (!level.f_46443_) {
            GunAttachments.setAmmo(gun, GunAttachments.getAmmo(gun) - 1);
            boolean homing = Implants.has(player, "smart_link");
            double homingRange = GunAttachments.has(gun, AttachmentItem.Effect.SMART_PROJECTOR) ? 17.0 : 12.0;
            float velocity = GunAttachments.has(gun, AttachmentItem.Effect.MAGNETIC_ACCELERATOR) ? 5.0f : 2.5f;
            boolean silenced = GunAttachments.has(gun, AttachmentItem.Effect.QUANTUM_SILENCER);
            float shotDamage = this.damage;
            if (silenced && player.m_6047_()) {
                shotDamage *= 2.5f;
            }
            if (GunAttachments.has(gun, AttachmentItem.Effect.SKIPPY_AI) && player.m_217043_().m_188501_() < 0.1f) {
                shotDamage *= 2.0f;
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.skippy_crit"), false);
            }
            for (int i = 0; i < this.pellets; ++i) {
                SmartBulletEntity bullet = new SmartBulletEntity(level, (LivingEntity)player, (double)shotDamage / 2.0, homing, homingRange);
                bullet.m_37251_((Entity)player, player.m_146909_(), player.m_146908_(), 0.0f, velocity, this.pellets > 1 ? 6.0f : 2.0f);
                level.m_7967_((Entity)bullet);
            }
            if (!silenced) {
                level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11738_, SoundSource.PLAYERS, 0.5f, 2.0f);
            }
            WeaponUtil.applyMirmidon(player, gun, shotDamage * (float)this.pellets);
        }
        player.m_36335_().m_41524_((Item)this, this.cooldown);
        return InteractionResultHolder.m_19092_((Object)gun, (boolean)level.m_5776_());
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.smart_weapon").m_130940_(ChatFormatting.AQUA));
        WeaponUtil.appendWeaponTooltip(stack, tooltip, this.damage * (float)this.pellets, this.magSize);
    }
}

