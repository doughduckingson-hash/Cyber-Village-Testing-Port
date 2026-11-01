/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.energy;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class EnergyGrid {
    private static final double LINK_RANGE = 16.0;
    private static final Map<String, LevelGrid> GRIDS = new HashMap<String, LevelGrid>();

    private EnergyGrid() {
    }

    private static LevelGrid grid(ServerLevel level) {
        return GRIDS.computeIfAbsent(level.m_46472_().m_135782_().toString(), k -> new LevelGrid());
    }

    public static void addGenerator(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).generators.add(pos.m_7949_());
        EnergyGrid.grid((ServerLevel)level).dirty = true;
    }

    public static void removeGenerator(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).generators.remove(pos);
        EnergyGrid.grid((ServerLevel)level).dirty = true;
    }

    public static void addRelay(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).relays.add(pos.m_7949_());
        EnergyGrid.grid((ServerLevel)level).dirty = true;
    }

    public static void removeRelay(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).relays.remove(pos);
        EnergyGrid.grid((ServerLevel)level).dirty = true;
    }

    public static void addPad(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).pads.add(pos.m_7949_());
    }

    public static void removePad(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).pads.remove(pos);
    }

    @Nullable
    public static BlockPos nearestPad(ServerLevel level, Vec3 from, double maxRange) {
        BlockPos best = null;
        double bestDist = maxRange * maxRange;
        for (BlockPos pad : EnergyGrid.grid((ServerLevel)level).pads) {
            double dist = pad.m_203198_(from.f_82479_, from.f_82480_, from.f_82481_);
            if (!(dist < bestDist)) continue;
            bestDist = dist;
            best = pad;
        }
        return best;
    }

    public static void addJammer(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).jammers.add(pos.m_7949_());
    }

    public static void removeJammer(ServerLevel level, BlockPos pos) {
        EnergyGrid.grid((ServerLevel)level).jammers.remove(pos);
    }

    public static boolean isJammed(ServerLevel level, Vec3 pos) {
        for (BlockPos jammer : EnergyGrid.grid((ServerLevel)level).jammers) {
            if (!(jammer.m_203198_(pos.f_82479_, pos.f_82480_, pos.f_82481_) < 256.0)) continue;
            return true;
        }
        return false;
    }

    public static boolean isPowered(ServerLevel level, BlockPos pos) {
        LevelGrid grid = EnergyGrid.grid(level);
        long now = level.m_46467_();
        if (grid.dirty || now - grid.lastCompute >= 20L) {
            EnergyGrid.recompute(level, grid);
            grid.lastCompute = now;
            grid.dirty = false;
        }
        for (BlockPos node : grid.energized) {
            if (!(node.m_123331_((Vec3i)pos) <= 256.0) || !EnergyGrid.hasLineOfSight(level, node, pos)) continue;
            return true;
        }
        return false;
    }

    private static void recompute(ServerLevel level, LevelGrid grid) {
        grid.energized.clear();
        ArrayDeque<BlockPos> queue = new ArrayDeque<BlockPos>(grid.generators);
        grid.energized.addAll(grid.generators);
        while (!queue.isEmpty()) {
            BlockPos node = queue.poll();
            for (BlockPos relay : grid.relays) {
                if (grid.energized.contains(relay) || !(relay.m_123331_((Vec3i)node) <= 256.0) || !EnergyGrid.hasLineOfSight(level, node, relay)) continue;
                grid.energized.add(relay);
                queue.add(relay);
            }
        }
    }

    private static boolean hasLineOfSight(ServerLevel level, BlockPos from, BlockPos to) {
        Vec3 end;
        Vec3 start = Vec3.m_82512_((Vec3i)from).m_82520_(0.0, 0.4, 0.0);
        return level.m_45547_(new ClipContext(start, end = Vec3.m_82512_((Vec3i)to).m_82520_(0.0, 0.4, 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).m_6662_() == HitResult.Type.MISS || level.m_45547_(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).m_82425_().equals((Object)to);
    }

    private static class LevelGrid {
        final Set<BlockPos> generators = new HashSet<BlockPos>();
        final Set<BlockPos> relays = new HashSet<BlockPos>();
        final Set<BlockPos> jammers = new HashSet<BlockPos>();
        final Set<BlockPos> pads = new HashSet<BlockPos>();
        final Set<BlockPos> energized = new HashSet<BlockPos>();
        long lastCompute = -100L;
        boolean dirty = true;

        private LevelGrid() {
        }
    }
}

