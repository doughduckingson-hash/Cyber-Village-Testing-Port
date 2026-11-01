/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.cybervillage.threat;

import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.threat.ThreatManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThreatEvents {
    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        Entity entity = event.getSource().m_7639_();
        if (!(entity instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player = (ServerPlayer)entity;
        LivingEntity victim = event.getEntity();
        if (victim instanceof Villager) {
            ThreatManager.addHeat(player, 10);
        } else if (victim instanceof IronGolem) {
            ThreatManager.addHeat(player, 15);
        } else if (victim instanceof DroneEntity) {
            ThreatManager.addHeat(player, 8);
        } else if (victim instanceof EnforcerEntity) {
            ThreatManager.addHeat(player, 10);
        } else if (victim instanceof NetrunnerEntity) {
            ThreatManager.addHeat(player, 12);
        } else if (victim instanceof MaxTacEntity) {
            ThreatManager.addHeat(player, 15);
        }
    }
}

