/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package com.cybervillage.block;

import com.cybervillage.block.DataServerBlockEntity;
import com.cybervillage.registry.ModBlockEntities;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DataServerBlock
extends BaseEntityBlock {
    public DataServerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new DataServerBlockEntity(pos, state);
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity;
        if (!level.f_46443_ && (blockEntity = level.m_7702_(pos)) instanceof DataServerBlockEntity) {
            DataServerBlockEntity server = (DataServerBlockEntity)blockEntity;
            server.collect(player);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return level.f_46443_ ? null : DataServerBlock.m_152132_(type, (BlockEntityType)((BlockEntityType)ModBlockEntities.DATA_SERVER.get()), DataServerBlockEntity::serverTick);
    }
}

