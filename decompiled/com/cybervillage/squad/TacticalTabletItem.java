/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.squad;

import com.cybervillage.squad.CaptainEntity;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TacticalTabletItem
extends Item {
    private static final String TAG_FIRST = "ZoneFirst";
    private static final String TAG_SECOND = "ZoneSecond";

    public TacticalTabletItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        Player player = context.m_43723_();
        if (level.f_46443_ || player == null) {
            return InteractionResult.SUCCESS;
        }
        CompoundTag tag = context.m_43722_().m_41784_();
        BlockPos pos = context.m_8083_();
        if (!tag.m_128441_(TAG_FIRST) || tag.m_128441_(TAG_SECOND)) {
            tag.m_128365_(TAG_FIRST, (Tag)NbtUtils.m_129224_((BlockPos)pos));
            tag.m_128473_(TAG_SECOND);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.zone_first", (Object[])new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}).m_130940_(ChatFormatting.AQUA), true);
        } else {
            tag.m_128365_(TAG_SECOND, (Tag)NbtUtils.m_129224_((BlockPos)pos));
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.zone_second", (Object[])new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}).m_130940_(ChatFormatting.AQUA), true);
        }
        return InteractionResult.CONSUME;
    }

    public InteractionResult m_6880_(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        CaptainEntity captain;
        if (!(target instanceof CaptainEntity) || !(captain = (CaptainEntity)target).isOwnedBy(player)) {
            return InteractionResult.PASS;
        }
        if (player.m_9236_().f_46443_) {
            return InteractionResult.SUCCESS;
        }
        CompoundTag tag = stack.m_41784_();
        if (!tag.m_128441_(TAG_FIRST) || !tag.m_128441_(TAG_SECOND)) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.zone_missing").m_130940_(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }
        captain.orderMine(NbtUtils.m_129239_((CompoundTag)tag.m_128469_(TAG_FIRST)), NbtUtils.m_129239_((CompoundTag)tag.m_128469_(TAG_SECOND)));
        player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.zone_assigned").m_130940_(ChatFormatting.GREEN), true);
        return InteractionResult.SUCCESS;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.tablet").m_130940_(ChatFormatting.GRAY));
    }
}

