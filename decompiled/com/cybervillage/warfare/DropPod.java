/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.cybervillage.warfare;

import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.registry.ModEntities;
import com.cybervillage.warfare.CorporateWarfareManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class DropPod {
    private DropPod() {
    }

    public static void deploy(ServerLevel level, BlockPos near) {
        int dz;
        int dx;
        BlockPos impact = CorporateWarfareManager.findSpawnPos(level, near);
        for (int y = 0; y < 30; ++y) {
            level.m_8767_((ParticleOptions)ParticleTypes.f_123744_, (double)impact.m_123341_() + 0.5, (double)(impact.m_123342_() + y), (double)impact.m_123343_() + 0.5, 2, 0.2, 0.2, 0.2, 0.0);
        }
        level.m_8767_((ParticleOptions)ParticleTypes.f_123812_, (double)impact.m_123341_() + 0.5, (double)impact.m_123342_() + 0.5, (double)impact.m_123343_() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
        level.m_5594_(null, impact, SoundEvents.f_11913_, SoundSource.HOSTILE, 2.0f, 0.6f);
        for (dx = -1; dx <= 1; ++dx) {
            for (dz = -1; dz <= 1; ++dz) {
                for (int dy = 0; dy <= 2; ++dy) {
                    BlockPos pos = impact.m_7918_(dx, dy, dz);
                    BlockState state = level.m_8055_(pos);
                    if (state.m_60795_() || !(state.m_60800_((BlockGetter)level, pos) >= 0.0f) || state.m_60804_((BlockGetter)level, pos)) continue;
                    level.m_46961_(pos, false);
                }
            }
        }
        for (dx = -2; dx <= 2; ++dx) {
            for (dz = -2; dz <= 2; ++dz) {
                BlockPos pos;
                if (Math.abs(dx) != 2 && Math.abs(dz) != 2 || (dx + dz + 100) % 2 != 0 || !level.m_8055_(pos = impact.m_7918_(dx, 0, dz)).m_60795_()) continue;
                level.m_46597_(pos, Blocks.f_50203_.m_49966_());
            }
        }
        for (int i = 0; i < 3; ++i) {
            EnforcerEntity heavy = (EnforcerEntity)((EntityType)ModEntities.ENFORCER.get()).m_20615_((Level)level);
            if (heavy == null) continue;
            heavy.m_7678_((double)impact.m_123341_() + 0.5 + (double)i * 0.3, impact.m_123342_(), (double)impact.m_123343_() + 0.5, 0.0f, 0.0f);
            heavy.m_6518_((ServerLevelAccessor)level, level.m_6436_(impact), MobSpawnType.EVENT, null, null);
            heavy.m_21153_(heavy.m_21233_());
            heavy.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 600, 1));
            level.m_7967_((Entity)heavy);
        }
    }
}

