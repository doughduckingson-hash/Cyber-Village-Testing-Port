/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  org.joml.Matrix4f
 */
package com.cybervillage.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.joml.Matrix4f;

public class RadialCommandScreen
extends Screen {
    private static final int SECTORS = 3;
    private static final float INNER_RADIUS = 25.0f;
    private static final float OUTER_RADIUS = 80.0f;
    private final Consumer<Byte> onPick;
    private int hovered = -1;

    public RadialCommandScreen(byte current, Consumer<Byte> onPick) {
        super((Component)Component.m_237115_((String)"screen.cybervillage.radial"));
        this.onPick = onPick;
        this.hovered = current;
    }

    public boolean m_7043_() {
        return false;
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int sector;
        float cx = (float)this.f_96543_ / 2.0f;
        double dx = (float)mouseX - cx;
        float cy = (float)this.f_96544_ / 2.0f;
        double dy = (float)mouseY - cy;
        if (dx * dx + dy * dy > 312.5) {
            double theta = Math.atan2(dy, dx) + 1.5707963267948966;
            if (theta < 0.0) {
                theta += Math.PI * 2;
            }
            this.hovered = (int)(theta / 2.0943951023931953) % 3;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::m_172811_);
        Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85815_);
        for (sector = 0; sector < 3; ++sector) {
            boolean isHovered = sector == this.hovered;
            int r = isHovered ? 48 : 16;
            int g = isHovered ? 224 : 64;
            int b = isHovered ? 255 : 96;
            int a = isHovered ? 176 : 112;
            double start = (double)sector * 2.0943951023931953 - 1.5707963267948966 + 0.04;
            double end = (double)(sector + 1) * 2.0943951023931953 - 1.5707963267948966 - 0.04;
            int steps = 16;
            for (int i = 0; i < steps; ++i) {
                double a0 = start + (end - start) * (double)i / (double)steps;
                double a1 = start + (end - start) * (double)(i + 1) / (double)steps;
                float x0i = cx + (float)(Math.cos(a0) * 25.0);
                float y0i = cy + (float)(Math.sin(a0) * 25.0);
                float x0o = cx + (float)(Math.cos(a0) * 80.0);
                float y0o = cy + (float)(Math.sin(a0) * 80.0);
                float x1i = cx + (float)(Math.cos(a1) * 25.0);
                float y1i = cy + (float)(Math.sin(a1) * 25.0);
                float x1o = cx + (float)(Math.cos(a1) * 80.0);
                float y1o = cy + (float)(Math.sin(a1) * 80.0);
                buffer.m_252986_(matrix, x0i, y0i, 0.0f).m_6122_(r, g, b, a).m_5752_();
                buffer.m_252986_(matrix, x0o, y0o, 0.0f).m_6122_(r, g, b, a).m_5752_();
                buffer.m_252986_(matrix, x1o, y1o, 0.0f).m_6122_(r, g, b, a).m_5752_();
                buffer.m_252986_(matrix, x0i, y0i, 0.0f).m_6122_(r, g, b, a).m_5752_();
                buffer.m_252986_(matrix, x1o, y1o, 0.0f).m_6122_(r, g, b, a).m_5752_();
                buffer.m_252986_(matrix, x1i, y1i, 0.0f).m_6122_(r, g, b, a).m_5752_();
            }
        }
        Tesselator.m_85913_().m_85914_();
        RenderSystem.disableBlend();
        for (sector = 0; sector < 3; ++sector) {
            double mid = ((double)sector + 0.5) * 2.0943951023931953 - 1.5707963267948966;
            float lx = cx + (float)(Math.cos(mid) * 98.0);
            float ly = cy + (float)(Math.sin(mid) * 98.0);
            Component label = RadialCommandScreen.sectorName(sector);
            int textWidth = this.f_96547_.m_92852_((FormattedText)label);
            graphics.m_280614_(this.f_96547_, label, (int)(lx - (float)textWidth / 2.0f), (int)(ly - 4.0f), sector == this.hovered ? 3203327 : 0xA0A0A0, true);
        }
        graphics.m_280653_(this.f_96547_, RadialCommandScreen.sectorName(this.hovered), (int)cx, (int)(cy - 4.0f), 0xFFFFFF);
    }

    private static Component sectorName(int sector) {
        return switch (sector) {
            case 1 -> Component.m_237115_((String)"squad.cybervillage.alpha");
            case 2 -> Component.m_237115_((String)"squad.cybervillage.beta");
            default -> Component.m_237115_((String)"squad.cybervillage.all");
        };
    }

    public void confirm() {
        if (this.hovered >= 0) {
            this.onPick.accept((byte)this.hovered);
        }
        this.m_7379_();
    }
}

