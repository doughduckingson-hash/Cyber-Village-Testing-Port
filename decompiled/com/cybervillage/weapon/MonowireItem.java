/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.weapon;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MonowireItem
extends Item {
    private static final double RANGE = 7.0;
    private static final float DAMAGE = 8.0f;

    public MonowireItem(Item.Properties properties) {
        super(properties.m_41487_(1));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack wire = player.m_21120_(hand);
        if (!level.f_46443_) {
            Vec3 start = player.m_146892_();
            Vec3 dir = player.m_20154_();
            boolean hit = false;
            for (double d = 0.5; d <= 7.0; d += 0.5) {
                Vec3 point = start.m_82549_(dir.m_82490_(d));
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123797_, point.f_82479_, point.f_82480_, point.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
                }
                for (LivingEntity target : level.m_6443_(LivingEntity.class, new AABB(point, point).m_82400_(0.8), e -> e != player && e.m_6084_())) {
                    target.m_6469_(level.m_269111_().m_269075_(player), 8.0f);
                    target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 3));
                    hit = true;
                }
                if (hit) break;
            }
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12520_, SoundSource.PLAYERS, 0.8f, 1.8f);
        }
        player.m_36335_().m_41524_((Item)this, 20);
        return InteractionResultHolder.m_19092_((Object)wire, (boolean)level.m_5776_());
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.monowire").m_130940_(ChatFormatting.LIGHT_PURPLE));
    }
}

