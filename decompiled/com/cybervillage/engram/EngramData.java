/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package com.cybervillage.engram;

import com.cybervillage.engram.Engram;
import com.cybervillage.implant.Implants;
import net.minecraft.world.entity.player.Player;

public class EngramData {
    private static final String TAG_ENGRAM = "Engram";
    private static final String TAG_ABILITY_READY_AT = "AbilityReadyAt";
    private static final String TAG_OVERLOAD_UNTIL = "OverloadUntil";
    private static final String TAG_CAMO_READY_AT = "CamoReadyAt";

    public static Engram get(Player player) {
        return Engram.byOrdinal(Implants.root(player).m_128451_(TAG_ENGRAM));
    }

    public static void set(Player player, Engram engram) {
        Implants.root(player).m_128405_(TAG_ENGRAM, engram.ordinal());
    }

    public static boolean isAbilityReady(Player player) {
        return player.m_9236_().m_46467_() >= Implants.root(player).m_128454_(TAG_ABILITY_READY_AT);
    }

    public static void setAbilityCooldown(Player player, int ticks) {
        Implants.root(player).m_128356_(TAG_ABILITY_READY_AT, player.m_9236_().m_46467_() + (long)ticks);
    }

    public static boolean isOverloadActive(Player player) {
        return player.m_9236_().m_46467_() < Implants.root(player).m_128454_(TAG_OVERLOAD_UNTIL);
    }

    public static void setOverload(Player player, int ticks) {
        Implants.root(player).m_128356_(TAG_OVERLOAD_UNTIL, player.m_9236_().m_46467_() + (long)ticks);
    }

    public static boolean isCamoReady(Player player) {
        return player.m_9236_().m_46467_() >= Implants.root(player).m_128454_(TAG_CAMO_READY_AT);
    }

    public static void setCamoCooldown(Player player, int ticks) {
        Implants.root(player).m_128356_(TAG_CAMO_READY_AT, player.m_9236_().m_46467_() + (long)ticks);
    }

    private EngramData() {
    }
}

