/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package com.cybervillage.energy;

import com.cybervillage.energy.EnergyGrid;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ChargePadBlock
extends Block {
    public ChargePadBlock(BlockBehaviour.Properties properties) {
        super(properties.m_60977_());
    }

    public void m_213897_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        EnergyGrid.addPad(level, pos);
        this.charge(level, pos);
        level.m_186460_(pos, (Block)this, 20);
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (!state.m_60713_(newState.m_60734_())) {
                EnergyGrid.removePad(serverLevel, pos);
            }
        }
        super.m_6810_(state, level, pos, newState, isMoving);
    }

    public void m_6807_(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.m_6807_(state, level, pos, oldState, isMoving);
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_186460_(pos, (Block)this, 20);
        }
    }

    private void charge(ServerLevel level, BlockPos pos) {
        if (!EnergyGrid.isPowered(level, pos)) {
            return;
        }
        List units = level.m_45976_(SquadUnitEntity.class, new AABB(pos).m_82377_(1.5, 2.0, 1.5));
        for (SquadUnitEntity unit : units) {
            if (!unit.chargeEnergy(10)) continue;
            level.m_8767_((ParticleOptions)ParticleTypes.f_175830_, unit.m_20185_(), unit.m_20186_() + 1.0, unit.m_20189_(), 4, 0.3, 0.5, 0.3, 0.05);
        }
    }
}

