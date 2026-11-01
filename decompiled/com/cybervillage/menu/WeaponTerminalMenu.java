/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package com.cybervillage.menu;

import com.cybervillage.registry.ModMenus;
import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.AttachmentType;
import com.cybervillage.weapon.GunAttachments;
import com.cybervillage.weapon.KatanaItem;
import com.cybervillage.weapon.SmartGunItem;
import com.cybervillage.weapon.TechGunItem;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WeaponTerminalMenu
extends AbstractContainerMenu {
    public static final int WEAPON_SLOT = 0;
    public static final AttachmentType[] SLOT_TYPES = AttachmentType.values();
    private final SimpleContainer container = new SimpleContainer(1 + SLOT_TYPES.length);
    private boolean syncing;
    private ItemStack lastWeapon = ItemStack.f_41583_;

    public WeaponTerminalMenu(int windowId, Inventory playerInventory) {
        super((MenuType)ModMenus.WEAPON_TERMINAL.get(), windowId);
        this.container.m_19164_(c -> this.onContainerChanged());
        this.m_38897_(new Slot((Container)this.container, 0, 26, 30){

            public boolean m_5857_(ItemStack stack) {
                return WeaponTerminalMenu.isModdableWeapon(stack);
            }

            public int m_6641_() {
                return 1;
            }
        });
        for (int i = 0; i < SLOT_TYPES.length; ++i) {
            final AttachmentType type = SLOT_TYPES[i];
            this.m_38897_(new Slot((Container)this.container, 1 + i, 72 + i * 22, 30){

                public boolean m_5857_(ItemStack stack) {
                    AttachmentItem attachment;
                    Item item;
                    return !this.f_40218_.m_8020_(0).m_41619_() && (item = stack.m_41720_()) instanceof AttachmentItem && (attachment = (AttachmentItem)item).getType() == type;
                }

                public int m_6641_() {
                    return 1;
                }
            });
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.m_38897_(new Slot((Container)playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.m_38897_(new Slot((Container)playerInventory, col, 8 + col * 18, 142));
        }
    }

    public static boolean isModdableWeapon(ItemStack stack) {
        return stack.m_41720_() instanceof SmartGunItem || stack.m_41720_() instanceof TechGunItem || stack.m_41720_() instanceof KatanaItem;
    }

    private void onContainerChanged() {
        boolean weaponChanged;
        if (this.syncing) {
            return;
        }
        ItemStack weapon = this.container.m_8020_(0);
        boolean bl = weaponChanged = !ItemStack.m_41728_((ItemStack)weapon, (ItemStack)this.lastWeapon);
        if (weaponChanged) {
            this.syncing = true;
            for (int i = 0; i < SLOT_TYPES.length; ++i) {
                this.container.m_6836_(1 + i, ItemStack.f_41583_);
            }
            if (!weapon.m_41619_()) {
                Map<AttachmentType, ItemStack> byType = GunAttachments.byType(weapon);
                for (int i = 0; i < SLOT_TYPES.length; ++i) {
                    ItemStack attachment = byType.get((Object)SLOT_TYPES[i]);
                    if (attachment == null) continue;
                    this.container.m_6836_(1 + i, attachment);
                }
            }
            this.syncing = false;
            this.lastWeapon = weapon.m_41777_();
        } else if (!weapon.m_41619_()) {
            this.writeAttachments(weapon);
            this.lastWeapon = weapon.m_41777_();
        }
    }

    private void writeAttachments(ItemStack weapon) {
        ArrayList<ItemStack> attachments = new ArrayList<ItemStack>();
        for (int i = 0; i < SLOT_TYPES.length; ++i) {
            ItemStack stack = this.container.m_8020_(1 + i);
            if (stack.m_41619_()) continue;
            attachments.add(stack);
        }
        this.syncing = true;
        GunAttachments.set(weapon, attachments);
        this.syncing = false;
    }

    public void m_6877_(Player player) {
        super.m_6877_(player);
        ItemStack weapon = this.container.m_8020_(0);
        if (!weapon.m_41619_()) {
            this.writeAttachments(weapon);
            if (!player.m_150109_().m_36054_(weapon)) {
                player.m_36176_(weapon, false);
            }
        }
    }

    public ItemStack m_7648_(Player player, int index) {
        ItemStack result = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(index);
        if (slot != null && slot.m_6657_()) {
            ItemStack stack = slot.m_7993_();
            result = stack.m_41777_();
            int containerSlots = 1 + SLOT_TYPES.length;
            if (index < containerSlots ? !this.m_38903_(stack, containerSlots, this.f_38839_.size(), true) : !this.m_38903_(stack, 0, containerSlots, false)) {
                return ItemStack.f_41583_;
            }
            if (stack.m_41619_()) {
                slot.m_5852_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
        }
        return result;
    }

    public boolean m_6875_(Player player) {
        return true;
    }

    public ItemStack getWeapon() {
        return this.container.m_8020_(0);
    }
}

