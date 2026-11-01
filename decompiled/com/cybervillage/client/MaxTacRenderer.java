/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package com.cybervillage.client;

import com.cybervillage.client.MaxTacModel;
import com.cybervillage.entity.MaxTacEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MaxTacRenderer
extends MobRenderer<MaxTacEntity, MaxTacModel<MaxTacEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("cybervillage:textures/entity/maxtac/maxtac.png");

    public MaxTacRenderer(EntityRendererProvider.Context context) {
        super(context, new MaxTacModel(context.m_174023_(ModelLayers.f_171223_)), 0.5f);
    }

    public ResourceLocation getTextureLocation(MaxTacEntity entity) {
        return TEXTURE;
    }
}

