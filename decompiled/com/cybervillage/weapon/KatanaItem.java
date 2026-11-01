/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.weapon;

import com.cybervillage.weapon.GunAttachments;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class KatanaItem
extends SwordItem {
    public KatanaItem(Tier tier, int damage, float speed, Item.Properties properties) {
        super(tier, damage, speed, properties);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.m_20254_(5);
        if (target.m_20069_()) {
            target.m_6469_(target.m_269291_().m_269549_(), 3.0f);
        }
        return super.m_7579_(stack, target, attacker);
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.thermal").m_130940_(ChatFormatting.RED));
        List<ItemStack> attachments = GunAttachments.get(stack);
        if (!attachments.isEmpty()) {
            tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.attachments").m_130940_(ChatFormatting.LIGHT_PURPLE));
            for (ItemStack attachment : attachments) {
                tooltip.add((Component)Component.m_237113_((String)"  ").m_7220_(attachment.m_41786_()).m_130940_(ChatFormatting.AQUA));
            }
        }
    }
}

