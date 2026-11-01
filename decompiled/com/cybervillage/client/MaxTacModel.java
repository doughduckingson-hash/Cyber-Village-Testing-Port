/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 */
package com.cybervillage.client;

import com.cybervillage.entity.MaxTacEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MaxTacModel<T extends MaxTacEntity>
extends HumanoidModel<T> {
    public static final ResourceLocation LAYER_LOCATION = new ResourceLocation("cybervillage", "textures/entity/maxtac/maxtac.png");

    public MaxTacModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.f_171404_);
        partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).m_171514_(32, 0).m_171488_(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.f_171404_);
        PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).m_171514_(0, 16).m_171488_(-4.0f, 0.0f, -2.0f, 8.0f, 18.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.f_171404_);
        PartDefinition right_arm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-5.0f, (float)2.0f, (float)0.0f));
        PartDefinition left_arm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171488_(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)5.0f, (float)2.0f, (float)0.0f));
        PartDefinition right_leg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-1.9f, (float)12.0f, (float)0.0f));
        PartDefinition left_leg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)1.9f, (float)12.0f, (float)0.0f));
        return LayerDefinition.m_171565_((MeshDefinition)meshdefinition, (int)64, (int)64);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.f_102808_.f_104204_ = netHeadYaw * ((float)Math.PI / 180);
        this.f_102808_.f_104203_ = headPitch * ((float)Math.PI / 180);
        this.f_102811_.f_104203_ = Mth.m_14089_((float)(limbSwing * 0.6662f + (float)Math.PI)) * 0.8f * limbSwingAmount * 0.5f;
        this.f_102812_.f_104203_ = Mth.m_14089_((float)(limbSwing * 0.6662f)) * 0.8f * limbSwingAmount * 0.5f;
        this.f_102811_.f_104205_ = 0.0f;
        this.f_102812_.f_104205_ = 0.0f;
        this.f_102813_.f_104203_ = Mth.m_14089_((float)(limbSwing * 0.6662f)) * 1.4f * limbSwingAmount;
        this.f_102814_.f_104203_ = Mth.m_14089_((float)(limbSwing * 0.6662f + (float)Math.PI)) * 1.4f * limbSwingAmount;
    }
}

