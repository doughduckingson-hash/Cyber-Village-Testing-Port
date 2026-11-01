/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.event.TickEvent$LevelTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.cybervillage.warfare;

import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.warfare.CorporateWarfareManager;
import com.cybervillage.warfare.CyberSamuraiEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WarfareEvents {
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        Level level = killed.m_9236_();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        if (!(event.getSource().m_7639_() instanceof ServerPlayer || event.getSource().m_7639_() instanceof CaptainEntity || event.getSource().m_7639_() instanceof SquadUnitEntity)) {
            return;
        }
        if (killed instanceof DroneEntity || killed instanceof EnforcerEntity || killed instanceof NetrunnerEntity || killed instanceof MaxTacEntity) {
            CorporateWarfareManager.get(level2).raiseAlert(killed.m_146902_(), 1);
        } else if (killed instanceof CyberSamuraiEntity) {
            CorporateWarfareManager manager = CorporateWarfareManager.get(level2);
            manager.raiseAlert(killed.m_146902_(), -5);
        }
    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level;
        if (event.phase == TickEvent.Phase.END && (level = event.level) instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            CorporateWarfareManager.get(level2).tick(level2);
        }
    }
}

