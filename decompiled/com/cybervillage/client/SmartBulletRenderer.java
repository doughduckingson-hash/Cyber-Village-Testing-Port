/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.ArrowRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 */
package com.cybervillage.client;

import com.cybervillage.entity.SmartBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SmartBulletRenderer
extends ArrowRenderer<SmartBulletEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/projectiles/spectral_arrow.png");

    public SmartBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(SmartBulletEntity entity) {
        return TEXTURE;
    }
}

