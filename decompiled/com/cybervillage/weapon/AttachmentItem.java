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
package com.cybervillage.weapon;

import com.cybervillage.weapon.AttachmentType;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class AttachmentItem
extends Item {
    private final AttachmentType type;
    private final Effect effect;

    public AttachmentItem(Item.Properties properties, AttachmentType type, Effect effect) {
        super(properties);
        this.type = type;
        this.effect = effect;
    }

    public AttachmentType getType() {
        return this.type;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)("tooltip.cybervillage.slot." + this.type.getSerializedName())).m_130940_(ChatFormatting.LIGHT_PURPLE));
        tooltip.add((Component)Component.m_237115_((String)("tooltip.cybervillage.attachment." + this.effect.name().toLowerCase())).m_130940_(ChatFormatting.GRAY));
    }

    public static enum Effect {
        KIROSHI_OPTIC,
        SMART_PROJECTOR,
        QUANTUM_SILENCER,
        MAGNETIC_ACCELERATOR,
        EM_CAPACITOR,
        MIRMIDON_CHIP,
        SKIPPY_AI;

    }
}

