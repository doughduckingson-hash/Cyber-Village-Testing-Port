/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package com.cybervillage.weapon;

import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.AttachmentType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunAttachments {
    public static final String TAG_ATTACHMENTS = "Attachments";
    public static final String TAG_AMMO = "Ammo";

    public static List<ItemStack> get(ItemStack weapon) {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        CompoundTag tag = weapon.m_41783_();
        if (tag != null && tag.m_128425_(TAG_ATTACHMENTS, 9)) {
            ListTag list = tag.m_128437_(TAG_ATTACHMENTS, 10);
            for (int i = 0; i < list.size(); ++i) {
                ItemStack stack = ItemStack.m_41712_((CompoundTag)list.m_128728_(i));
                if (stack.m_41619_()) continue;
                result.add(stack);
            }
        }
        return result;
    }

    public static Map<AttachmentType, ItemStack> byType(ItemStack weapon) {
        EnumMap<AttachmentType, ItemStack> map = new EnumMap<AttachmentType, ItemStack>(AttachmentType.class);
        for (ItemStack stack : GunAttachments.get(weapon)) {
            Item item = stack.m_41720_();
            if (!(item instanceof AttachmentItem)) continue;
            AttachmentItem attachment = (AttachmentItem)item;
            map.put(attachment.getType(), stack);
        }
        return map;
    }

    public static void set(ItemStack weapon, List<ItemStack> attachments) {
        ListTag list = new ListTag();
        for (ItemStack stack : attachments) {
            if (stack.m_41619_()) continue;
            list.add((Object)stack.m_41739_(new CompoundTag()));
        }
        CompoundTag tag = weapon.m_41784_();
        if (list.isEmpty()) {
            tag.m_128473_(TAG_ATTACHMENTS);
        } else {
            tag.m_128365_(TAG_ATTACHMENTS, (Tag)list);
        }
    }

    public static boolean has(ItemStack weapon, AttachmentItem.Effect effect) {
        for (ItemStack stack : GunAttachments.get(weapon)) {
            AttachmentItem attachment;
            Item item = stack.m_41720_();
            if (!(item instanceof AttachmentItem) || (attachment = (AttachmentItem)item).getEffect() != effect) continue;
            return true;
        }
        return false;
    }

    public static int getAmmo(ItemStack weapon) {
        CompoundTag tag = weapon.m_41783_();
        return tag == null ? 0 : tag.m_128451_(TAG_AMMO);
    }

    public static void setAmmo(ItemStack weapon, int ammo) {
        weapon.m_41784_().m_128405_(TAG_AMMO, Math.max(0, ammo));
    }
}

