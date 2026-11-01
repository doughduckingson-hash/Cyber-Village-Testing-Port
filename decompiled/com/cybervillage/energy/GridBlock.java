/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.cybervillage.energy;

import com.cybervillage.energy.EnergyGrid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GridBlock
extends Block {
    private final Role role;

    public GridBlock(BlockBehaviour.Properties properties, Role role) {
        super(properties);
        this.role = role;
    }

    public void m_6807_(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.m_6807_(state, level, pos, oldState, isMoving);
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (!state.m_60713_(oldState.m_60734_())) {
                this.register(serverLevel, pos);
                serverLevel.m_186460_(pos, (Block)this, 40);
            }
        }
    }

    public void m_213897_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.register(level, pos);
        level.m_186460_(pos, (Block)this, 40);
    }

    private void register(ServerLevel level, BlockPos pos) {
        switch (this.role) {
            case GENERATOR: {
                EnergyGrid.addGenerator(level, pos);
                break;
            }
            case RELAY: {
                EnergyGrid.addRelay(level, pos);
                break;
            }
            case JAMMER: {
                EnergyGrid.addJammer(level, pos);
            }
        }
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (!state.m_60713_(newState.m_60734_())) {
                switch (this.role) {
                    case GENERATOR: {
                        EnergyGrid.removeGenerator(serverLevel, pos);
                        break;
                    }
                    case RELAY: {
                        EnergyGrid.removeRelay(serverLevel, pos);
                        break;
                    }
                    case JAMMER: {
                        EnergyGrid.removeJammer(serverLevel, pos);
                    }
                }
            }
        }
        super.m_6810_(state, level, pos, newState, isMoving);
    }

    public static enum Role {
        GENERATOR,
        RELAY,
        JAMMER;

    }
}

