/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.weapon;

import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.GunAttachments;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WeaponUtil {
    public static void reload(Level level, Player player, ItemStack weapon, int magSize, Item ammoItem) {
        int current = GunAttachments.getAmmo(weapon);
        int needed = magSize - current;
        if (needed <= 0) {
            return;
        }
        int found = 0;
        for (int i = 0; i < player.m_150109_().m_6643_() && found < needed; ++i) {
            ItemStack slot = player.m_150109_().m_8020_(i);
            if (!slot.m_150930_(ammoItem)) continue;
            int take = Math.min(needed - found, slot.m_41613_());
            if (!player.m_150110_().f_35937_) {
                slot.m_41774_(take);
            }
            found += take;
        }
        if (player.m_150110_().f_35937_) {
            found = needed;
        }
        if (found > 0) {
            GunAttachments.setAmmo(weapon, current + found);
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11841_, SoundSource.PLAYERS, 1.0f, 1.4f);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.reloaded", (Object[])new Object[]{GunAttachments.getAmmo(weapon), magSize}), true);
        } else {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_cells"), true);
        }
    }

    public static void applyMirmidon(Player player, ItemStack weapon, float damageDealt) {
        if (GunAttachments.has(weapon, AttachmentItem.Effect.MIRMIDON_CHIP)) {
            float absorption = Math.min(player.m_6103_() + damageDealt * 0.1f, 10.0f);
            player.m_7911_(absorption);
        }
    }

    public static void appendWeaponTooltip(ItemStack stack, List<Component> tooltip, float damage, int magSize) {
        tooltip.add((Component)Component.m_237110_((String)"tooltip.cybervillage.ammo", (Object[])new Object[]{GunAttachments.getAmmo(stack), magSize}).m_130940_(ChatFormatting.YELLOW));
        tooltip.add((Component)Component.m_237110_((String)"tooltip.cybervillage.damage", (Object[])new Object[]{String.format("%.1f", Float.valueOf(damage))}).m_130940_(ChatFormatting.GRAY));
        List<ItemStack> attachments = GunAttachments.get(stack);
        if (!attachments.isEmpty()) {
            tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.attachments").m_130940_(ChatFormatting.LIGHT_PURPLE));
            for (ItemStack attachment : attachments) {
                tooltip.add((Component)Component.m_237113_((String)"  ").m_7220_(attachment.m_41786_()).m_130940_(ChatFormatting.AQUA));
            }
        }
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.gun_help").m_130940_(ChatFormatting.DARK_GRAY));
    }
}

