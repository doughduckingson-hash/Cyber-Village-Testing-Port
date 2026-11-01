/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.saveddata.SavedData
 */
package com.cybervillage.warfare;

import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.registry.ModEntities;
import com.cybervillage.warfare.CyberSamuraiEntity;
import com.cybervillage.warfare.DropPod;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.SavedData;

public class CorporateWarfareManager
extends SavedData {
    private static final String DATA_NAME = "cybervillage_warfare";
    public static final int MAX_ALERT = 5;
    private static final int DECAY_INTERVAL = 1200;
    private static final int RESPONSE_INTERVAL = 300;
    private final Map<Long, Integer> chunkAlert = new HashMap<Long, Integer>();
    private final Map<Long, Boolean> bossDeployed = new HashMap<Long, Boolean>();
    private int decayTimer;
    private int responseTimer;

    public static CorporateWarfareManager get(ServerLevel level) {
        return (CorporateWarfareManager)level.m_8895_().m_164861_(CorporateWarfareManager::load, CorporateWarfareManager::new, DATA_NAME);
    }

    public static CorporateWarfareManager load(CompoundTag tag) {
        CorporateWarfareManager manager = new CorporateWarfareManager();
        ListTag list = tag.m_128437_("Alerts", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag entry = list.m_128728_(i);
            manager.chunkAlert.put(entry.m_128454_("Chunk"), entry.m_128451_("Alert"));
        }
        return manager;
    }

    public CompoundTag m_7176_(CompoundTag tag) {
        ListTag list = new ListTag();
        this.chunkAlert.forEach((chunk, alert) -> {
            CompoundTag entry = new CompoundTag();
            entry.m_128356_("Chunk", chunk.longValue());
            entry.m_128405_("Alert", alert.intValue());
            list.add((Object)entry);
        });
        tag.m_128365_("Alerts", (Tag)list);
        return tag;
    }

    public int getAlert(ChunkPos pos) {
        return this.chunkAlert.getOrDefault(pos.m_45588_(), 0);
    }

    public void raiseAlert(ChunkPos pos, int amount) {
        long key = pos.m_45588_();
        int newAlert = Math.max(0, Math.min(5, this.chunkAlert.getOrDefault(key, 0) + amount));
        if (newAlert == 0) {
            this.chunkAlert.remove(key);
            this.bossDeployed.remove(key);
        } else {
            this.chunkAlert.put(key, newAlert);
        }
        this.m_77762_();
    }

    public void tick(ServerLevel level) {
        if (++this.decayTimer >= 1200) {
            this.decayTimer = 0;
            Iterator<Map.Entry<Long, Integer>> it = this.chunkAlert.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, Integer> entry = it.next();
                if (entry.getValue() <= 1) {
                    this.bossDeployed.remove(entry.getKey());
                    it.remove();
                    continue;
                }
                entry.setValue(entry.getValue() - 1);
            }
            this.m_77762_();
        }
        if (++this.responseTimer >= 300) {
            this.responseTimer = 0;
            for (ServerPlayer player : level.m_6907_()) {
                this.respond(level, player);
            }
        }
    }

    private void respond(ServerLevel level, ServerPlayer player) {
        ChunkPos chunk = player.m_146902_();
        int alert = this.getAlert(chunk);
        if (alert <= 0) {
            return;
        }
        if (alert <= 2) {
            this.spawnPatrol(level, player);
        } else if (alert <= 4) {
            DropPod.deploy(level, player.m_20183_());
        } else if (!this.bossDeployed.getOrDefault(chunk.m_45588_(), false).booleanValue()) {
            this.bossDeployed.put(chunk.m_45588_(), true);
            CyberSamuraiEntity boss = (CyberSamuraiEntity)((EntityType)ModEntities.CYBER_SAMURAI.get()).m_20615_((Level)level);
            if (boss != null) {
                BlockPos pos = CorporateWarfareManager.findSpawnPos(level, player.m_20183_());
                boss.m_7678_((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, 0.0f, 0.0f);
                boss.m_6518_((ServerLevelAccessor)level, level.m_6436_(pos), MobSpawnType.EVENT, null, null);
                level.m_7967_((Entity)boss);
            }
        }
    }

    private void spawnPatrol(ServerLevel level, ServerPlayer player) {
        for (int i = 0; i < 2; ++i) {
            EnforcerEntity enforcer = (EnforcerEntity)((EntityType)ModEntities.ENFORCER.get()).m_20615_((Level)level);
            if (enforcer == null) continue;
            BlockPos pos = CorporateWarfareManager.findSpawnPos(level, player.m_20183_());
            enforcer.m_7678_((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, 0.0f, 0.0f);
            enforcer.m_6518_((ServerLevelAccessor)level, level.m_6436_(pos), MobSpawnType.EVENT, null, null);
            level.m_7967_((Entity)enforcer);
        }
    }

    static BlockPos findSpawnPos(ServerLevel level, BlockPos center) {
        RandomSource random = level.m_213780_();
        for (int attempt = 0; attempt < 8; ++attempt) {
            int z;
            int x = center.m_123341_() + random.m_188503_(24) - 12;
            int y = level.m_6924_(Heightmap.Types.MOTION_BLOCKING, x, z = center.m_123343_() + random.m_188503_(24) - 12);
            if (Math.abs(y - center.m_123342_()) >= 16) continue;
            return new BlockPos(x, y, z);
        }
        return center.m_7918_(8, 0, 8);
    }
}

