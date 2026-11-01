/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 */
package com.cybervillage.client;

import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.vehicle.SpiderMechEntity;
import com.cybervillage.warfare.CyberSamuraiEntity;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public final class VisorRenderer {
    private static boolean active;
    private static final Set<Integer> GLOWING;

    private VisorRenderer() {
    }

    public static boolean isActive() {
        return active;
    }

    public static void toggle(LocalPlayer player) {
        active = !active;
        player.m_5661_((Component)Component.m_237115_((String)(active ? "message.cybervillage.visor_on" : "message.cybervillage.visor_off")).m_130940_(ChatFormatting.AQUA), true);
        if (!active) {
            VisorRenderer.clear();
        }
    }

    public static void clientTick(Minecraft mc) {
        if (!active || mc.f_91073_ == null || mc.f_91074_ == null) {
            return;
        }
        if (mc.f_91074_.f_19797_ % 10 != 0) {
            return;
        }
        HashSet<Integer> current = new HashSet<Integer>();
        for (Entity entity : mc.f_91073_.m_6443_(Entity.class, mc.f_91074_.m_20191_().m_82400_(48.0), VisorRenderer::isCyberUnit)) {
            entity.m_146915_(true);
            current.add(entity.m_19879_());
        }
        Iterator<Object> iterator = GLOWING.iterator();
        while (iterator.hasNext()) {
            Entity entity;
            int id = (Integer)iterator.next();
            if (current.contains(id) || (entity = mc.f_91073_.m_6815_(id)) == null) continue;
            entity.m_146915_(false);
        }
        GLOWING.clear();
        GLOWING.addAll(current);
    }

    private static void clear() {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ != null) {
            for (int id : GLOWING) {
                Entity entity = mc.f_91073_.m_6815_(id);
                if (entity == null) continue;
                entity.m_146915_(false);
            }
        }
        GLOWING.clear();
    }

    private static boolean isCyberUnit(Entity entity) {
        return entity instanceof CaptainEntity || entity instanceof SquadUnitEntity || entity instanceof SpiderMechEntity || entity instanceof EnforcerEntity || entity instanceof NetrunnerEntity || entity instanceof MaxTacEntity || entity instanceof DroneEntity || entity instanceof CyberSamuraiEntity;
    }

    static {
        GLOWING = new HashSet<Integer>();
    }
}

