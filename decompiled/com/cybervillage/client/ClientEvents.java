/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.RegisterGuiOverlaysEvent
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.gui.overlay.ForgeGui
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.cybervillage.client;

import com.cybervillage.client.ClientState;
import com.cybervillage.client.RadialCommandScreen;
import com.cybervillage.client.VisorRenderer;
import com.cybervillage.engram.Engram;
import com.cybervillage.network.ActionPacket;
import com.cybervillage.network.CommandPacket;
import com.cybervillage.network.NetworkHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    public static final KeyMapping ABILITY_KEY = new KeyMapping("key.cybervillage.ability", InputConstants.Type.KEYSYM, 71, "key.categories.cybervillage");
    public static final KeyMapping SWITCH_ENGRAM_KEY = new KeyMapping("key.cybervillage.switch_engram", InputConstants.Type.KEYSYM, 72, "key.categories.cybervillage");
    public static final KeyMapping CAMO_KEY = new KeyMapping("key.cybervillage.camo", InputConstants.Type.KEYSYM, 78, "key.categories.cybervillage");
    public static final KeyMapping COMMAND_KEY = new KeyMapping("key.cybervillage.command", InputConstants.Type.KEYSYM, 74, "key.categories.cybervillage");
    public static final KeyMapping SELECT_SQUAD_KEY = new KeyMapping("key.cybervillage.select_squad", InputConstants.Type.KEYSYM, 75, "key.categories.cybervillage");
    public static final KeyMapping RADIAL_KEY = new KeyMapping("key.cybervillage.radial", InputConstants.Type.KEYSYM, 96, "key.categories.cybervillage");
    public static final KeyMapping VISOR_KEY = new KeyMapping("key.cybervillage.visor", InputConstants.Type.KEYSYM, 86, "key.categories.cybervillage");
    public static final KeyMapping STEALTH_KEY = new KeyMapping("key.cybervillage.stealth", InputConstants.Type.KEYSYM, 90, "key.categories.cybervillage");
    private static byte squadSelection = 0;
    private static boolean jumpWasDown;
    private static boolean doubleJumpUsed;

    private static void renderHud(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ == null || mc.f_91066_.f_92062_) {
            return;
        }
        Engram engram = Engram.byOrdinal(ClientState.INSTANCE.engram);
        ChatFormatting color = switch (engram) {
            default -> throw new IncompatibleClassChangeError();
            case Engram.HACKER -> ChatFormatting.AQUA;
            case Engram.NINJA -> ChatFormatting.GREEN;
            case Engram.DESTROYER -> ChatFormatting.RED;
        };
        MutableComponent line = Component.m_237113_((String)"\u25c8 ").m_7220_((Component)Component.m_237115_((String)("engram.cybervillage." + engram.getSerializedName()))).m_130940_(color);
        graphics.m_280614_(mc.f_91062_, (Component)line, 6, 6, 0xFFFFFF, true);
        MutableComponent humanity = Component.m_237110_((String)"hud.cybervillage.humanity", (Object[])new Object[]{ClientState.INSTANCE.humanity}).m_130940_(ChatFormatting.LIGHT_PURPLE);
        graphics.m_280614_(mc.f_91062_, (Component)humanity, 6, 17, 0xFFFFFF, true);
        MutableComponent squad = Component.m_237110_((String)"hud.cybervillage.squad", (Object[])new Object[]{ClientEvents.selectionName()}).m_130940_(ChatFormatting.GOLD);
        graphics.m_280614_(mc.f_91062_, (Component)squad, 6, 28, 0xFFFFFF, true);
    }

    private static void handleRadialMenu(Minecraft mc, LocalPlayer player) {
        Screen screen;
        boolean down;
        boolean bl = down = !RADIAL_KEY.m_90862_() && InputConstants.m_84830_((long)mc.m_91268_().m_85439_(), (int)RADIAL_KEY.getKey().m_84873_());
        if (down && mc.f_91080_ == null) {
            mc.m_91152_((Screen)new RadialCommandScreen(squadSelection, selection -> {
                squadSelection = selection;
                player.m_5661_((Component)Component.m_237110_((String)"squad.cybervillage.selected", (Object[])new Object[]{ClientEvents.selectionName()}).m_130940_(ChatFormatting.AQUA), true);
            }));
        } else if (!down && (screen = mc.f_91080_) instanceof RadialCommandScreen) {
            RadialCommandScreen radial = (RadialCommandScreen)screen;
            radial.confirm();
        }
    }

    private static Component selectionName() {
        return switch (squadSelection) {
            case 1 -> Component.m_237115_((String)"squad.cybervillage.alpha");
            case 2 -> Component.m_237115_((String)"squad.cybervillage.beta");
            default -> Component.m_237115_((String)"squad.cybervillage.all");
        };
    }

    @Mod.EventBusSubscriber(modid="cybervillage", bus=Mod.EventBusSubscriber.Bus.FORGE, value={Dist.CLIENT})
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            Minecraft mc = Minecraft.m_91087_();
            LocalPlayer player = mc.f_91074_;
            if (player == null) {
                return;
            }
            while (ABILITY_KEY.m_90859_()) {
                NetworkHandler.CHANNEL.sendToServer((Object)new ActionPacket(0));
            }
            while (SWITCH_ENGRAM_KEY.m_90859_()) {
                NetworkHandler.CHANNEL.sendToServer((Object)new ActionPacket(1));
            }
            while (CAMO_KEY.m_90859_()) {
                NetworkHandler.CHANNEL.sendToServer((Object)new ActionPacket(2));
            }
            while (COMMAND_KEY.m_90859_()) {
                NetworkHandler.CHANNEL.sendToServer((Object)new CommandPacket(squadSelection));
            }
            while (VISOR_KEY.m_90859_()) {
                if (player.m_6144_()) {
                    NetworkHandler.CHANNEL.sendToServer((Object)new ActionPacket(3));
                    continue;
                }
                VisorRenderer.toggle(player);
            }
            while (STEALTH_KEY.m_90859_()) {
                NetworkHandler.CHANNEL.sendToServer((Object)new ActionPacket(4));
            }
            VisorRenderer.clientTick(mc);
            while (SELECT_SQUAD_KEY.m_90859_()) {
                squadSelection = (byte)((squadSelection + 1) % 3);
                player.m_5661_((Component)Component.m_237110_((String)"squad.cybervillage.selected", (Object[])new Object[]{ClientEvents.selectionName()}).m_130940_(ChatFormatting.AQUA), true);
            }
            ClientEvents.handleRadialMenu(mc, player);
            ForgeBusEvents.handleDoubleJump(mc, player);
        }

        private static void handleDoubleJump(Minecraft mc, LocalPlayer player) {
            boolean canDoubleJump;
            boolean bl = canDoubleJump = ClientState.INSTANCE.doubleJump || ClientState.INSTANCE.engram == Engram.NINJA.ordinal();
            if (!canDoubleJump) {
                return;
            }
            boolean jumpDown = mc.f_91066_.f_92089_.m_90857_();
            if (player.m_20096_()) {
                doubleJumpUsed = false;
            } else if (!(!jumpDown || jumpWasDown || doubleJumpUsed || player.m_150110_().f_35935_ || player.m_20069_())) {
                Vec3 velocity = player.m_20184_();
                player.m_20334_(velocity.f_82479_ * 1.2, 0.55, velocity.f_82481_ * 1.2);
                player.f_19789_ = 0.0f;
                doubleJumpUsed = true;
            }
            jumpWasDown = jumpDown;
        }
    }

    @Mod.EventBusSubscriber(modid="cybervillage", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
            event.register(ABILITY_KEY);
            event.register(SWITCH_ENGRAM_KEY);
            event.register(CAMO_KEY);
            event.register(COMMAND_KEY);
            event.register(SELECT_SQUAD_KEY);
            event.register(RADIAL_KEY);
            event.register(VISOR_KEY);
            event.register(STEALTH_KEY);
        }

        @SubscribeEvent
        public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "cyber_hud", ClientEvents::renderHud);
        }
    }
}

