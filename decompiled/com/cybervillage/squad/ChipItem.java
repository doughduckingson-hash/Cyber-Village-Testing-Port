/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.squad;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ChipItem
extends Item {
    private final String chipId;

    public ChipItem(Item.Properties properties, String chipId) {
        super(properties);
        this.chipId = chipId;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)("tooltip.cybervillage.chip." + this.chipId)).m_130940_(ChatFormatting.GRAY));
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.chip_help").m_130940_(ChatFormatting.DARK_GRAY));
    }
}

