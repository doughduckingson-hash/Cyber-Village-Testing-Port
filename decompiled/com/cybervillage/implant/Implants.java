/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package com.cybervillage.implant;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

public class Implants {
    public static final String TAG_ROOT = "CyberVillage";
    public static final String TAG_IMPLANTS = "Implants";
    public static final String TAG_HUMANITY = "Humanity";
    public static final int HUMANITY_LIMIT = 100;
    public static final String KIROSHI_EYES = "kiroshi_eyes";
    public static final String SMART_LINK = "smart_link";
    public static final String SECOND_HEART = "second_heart";
    public static final String REINFORCED_TENDONS = "reinforced_tendons";
    public static final String SAPSAN_STIMS = "sapsan_stims";
    public static final String OPTICAL_CAMO = "optical_camo";

    public static CompoundTag root(Player player) {
        CompoundTag persisted;
        CompoundTag persistent = player.getPersistentData();
        if (!persistent.m_128441_("PlayerPersisted")) {
            persistent.m_128365_("PlayerPersisted", (Tag)new CompoundTag());
        }
        if (!(persisted = persistent.m_128469_("PlayerPersisted")).m_128441_(TAG_ROOT)) {
            persisted.m_128365_(TAG_ROOT, (Tag)new CompoundTag());
        }
        return persisted.m_128469_(TAG_ROOT);
    }

    public static boolean has(Player player, String implantId) {
        return Implants.root(player).m_128469_(TAG_IMPLANTS).m_128471_(implantId);
    }

    public static boolean install(Player player, String implantId, int humanityCost) {
        CompoundTag tag = Implants.root(player);
        CompoundTag implants = tag.m_128469_(TAG_IMPLANTS);
        if (implants.m_128471_(implantId)) {
            return false;
        }
        int humanity = tag.m_128451_(TAG_HUMANITY);
        if (humanity + humanityCost > 100) {
            return false;
        }
        implants.m_128379_(implantId, true);
        tag.m_128365_(TAG_IMPLANTS, (Tag)implants);
        tag.m_128405_(TAG_HUMANITY, humanity + humanityCost);
        return true;
    }

    public static int getHumanity(Player player) {
        return Implants.root(player).m_128451_(TAG_HUMANITY);
    }
}

