/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package com.cybervillage.client;

import com.cybervillage.menu.WeaponTerminalMenu;
import com.cybervillage.weapon.GunAttachments;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class WeaponTerminalScreen
extends AbstractContainerScreen<WeaponTerminalMenu> {
    private static final int BG = -15921388;
    private static final int PANEL = -15328734;
    private static final int SLOT_BG = -16118768;
    private static final int SLOT_BORDER = -8765528;
    private static final int ACCENT = -2076673;
    private static final int TEXT = -7366488;

    public WeaponTerminalScreen(WeaponTerminalMenu menu, Inventory inventory, Component title) {
        super((AbstractContainerMenu)menu, inventory, title);
        this.f_97726_ = 176;
        this.f_97727_ = 166;
        this.f_97731_ = this.f_97727_ - 94;
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(graphics);
        super.m_88315_(graphics, mouseX, mouseY, partialTick);
        this.m_280072_(graphics, mouseX, mouseY);
    }

    protected void m_7286_(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.f_97735_;
        int y = this.f_97736_;
        graphics.m_280509_(x, y, x + this.f_97726_, y + this.f_97727_, -15921388);
        graphics.m_280509_(x + 4, y + 14, x + this.f_97726_ - 4, y + 76, -15328734);
        graphics.m_280509_(x + 4, y + 80, x + this.f_97726_ - 4, y + this.f_97727_ - 4, -15328734);
        this.drawSlot(graphics, x + 26, y + 30);
        for (int i = 0; i < WeaponTerminalMenu.SLOT_TYPES.length; ++i) {
            this.drawSlot(graphics, x + 72 + i * 22, y + 30);
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.drawSlot(graphics, x + 8 + col * 18, y + 84 + row * 18);
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.drawSlot(graphics, x + 8 + col * 18, y + 142);
        }
    }

    private void drawSlot(GuiGraphics graphics, int x, int y) {
        graphics.m_280509_(x - 1, y - 1, x + 17, y + 17, -8765528);
        graphics.m_280509_(x, y, x + 16, y + 16, -16118768);
    }

    protected void m_280003_(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.m_280614_(this.f_96547_, this.f_96539_, this.f_97728_, this.f_97729_, -2076673, false);
        graphics.m_280614_(this.f_96547_, this.f_169604_, this.f_97730_, this.f_97731_, -7366488, false);
        graphics.m_280614_(this.f_96547_, (Component)Component.m_237115_((String)"screen.cybervillage.weapon"), 20, 20, -7366488, false);
        String[] shortNames = new String[]{"SGT", "BRL", "RCV", "FW"};
        for (int i = 0; i < WeaponTerminalMenu.SLOT_TYPES.length; ++i) {
            graphics.m_280056_(this.f_96547_, shortNames[i], 73 + i * 22, 20, -7366488, false);
        }
        ItemStack weapon = ((WeaponTerminalMenu)this.f_97732_).getWeapon();
        if (!weapon.m_41619_()) {
            int modules = GunAttachments.get(weapon).size();
            graphics.m_280614_(this.f_96547_, (Component)Component.m_237110_((String)"screen.cybervillage.modules", (Object[])new Object[]{modules, WeaponTerminalMenu.SLOT_TYPES.length}), 8, 58, -2501960, false);
        } else {
            graphics.m_280614_(this.f_96547_, (Component)Component.m_237115_((String)"screen.cybervillage.place_weapon"), 8, 58, -11116946, false);
        }
    }
}

