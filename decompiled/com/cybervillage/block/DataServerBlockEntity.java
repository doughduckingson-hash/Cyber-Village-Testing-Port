/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.cybervillage.block;

import com.cybervillage.registry.ModBlockEntities;
import com.cybervillage.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DataServerBlockEntity
extends BlockEntity {
    private static final int TICKS_PER_CREDIT = 900;
    private static final int MAX_STORED = 64;
    private int progress;
    private int stored;

    public DataServerBlockEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType)ModBlockEntities.DATA_SERVER.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DataServerBlockEntity server) {
        if (server.stored >= 64) {
            return;
        }
        if (++server.progress >= 900) {
            server.progress = 0;
            ++server.stored;
            server.m_6596_();
        }
    }

    public void collect(Player player) {
        if (this.stored <= 0) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.server_empty"), true);
            return;
        }
        ItemStack credits = new ItemStack((ItemLike)ModItems.EUROCREDIT.get(), this.stored);
        if (!player.m_150109_().m_36054_(credits)) {
            player.m_36176_(credits, false);
        }
        player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.server_collect", (Object[])new Object[]{this.stored}), true);
        this.stored = 0;
        this.m_6596_();
    }

    protected void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128405_("Progress", this.progress);
        tag.m_128405_("Stored", this.stored);
    }

    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        this.progress = tag.m_128451_("Progress");
        this.stored = tag.m_128451_("Stored");
    }
}

