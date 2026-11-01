/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 */
package com.cybervillage.block;

import com.cybervillage.squad.CaptainEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class AnchorBlock
extends Block {
    public AnchorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.f_46443_) {
            List captains = level.m_6443_(CaptainEntity.class, new AABB(pos).m_82400_(24.0), c -> c.isOwnedBy(player));
            for (CaptainEntity captain : captains) {
                captain.setAnchor(pos);
            }
            level.m_5594_(null, pos, SoundEvents.f_12326_, SoundSource.BLOCKS, 0.7f, 1.4f);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.anchor_bound", (Object[])new Object[]{captains.size()}).m_130940_(ChatFormatting.AQUA), true);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }
}

