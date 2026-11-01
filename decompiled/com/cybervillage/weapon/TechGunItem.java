/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
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
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.weapon;

import com.cybervillage.registry.ModItems;
import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.GunAttachments;
import com.cybervillage.weapon.WeaponUtil;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TechGunItem
extends Item {
    private final float baseDamage;
    private final float chargedDamage;
    private final int fullChargeTicks;
    private final int blockPierce;
    private final int entityPierce;
    private final int magSize;

    public TechGunItem(Item.Properties properties, float baseDamage, float chargedDamage, int fullChargeTicks, int blockPierce, int entityPierce, int magSize) {
        super(properties.m_41487_(1));
        this.baseDamage = baseDamage;
        this.chargedDamage = chargedDamage;
        this.fullChargeTicks = fullChargeTicks;
        this.blockPierce = blockPierce;
        this.entityPierce = entityPierce;
        this.magSize = magSize;
    }

    public int getMagSize() {
        return this.magSize;
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int m_8105_(ItemStack stack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack gun = player.m_21120_(hand);
        if (player.m_6144_()) {
            if (!level.f_46443_) {
                WeaponUtil.reload(level, player, gun, this.magSize, (Item)ModItems.ENERGY_CELL.get());
            }
            return InteractionResultHolder.m_19096_((Object)gun);
        }
        if (GunAttachments.getAmmo(gun) <= 0) {
            if (!level.f_46443_) {
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_charge"), true);
            }
            return InteractionResultHolder.m_19100_((Object)gun);
        }
        player.m_6672_(hand);
        return InteractionResultHolder.m_19096_((Object)gun);
    }

    public void m_5551_(ItemStack gun, Level level, LivingEntity shooter, int remainingTicks) {
        Player player;
        block8: {
            block7: {
                if (!(shooter instanceof Player)) break block7;
                player = (Player)shooter;
                if (!level.f_46443_) break block8;
            }
            return;
        }
        if (GunAttachments.getAmmo(gun) <= 0) {
            return;
        }
        int chargeNeeded = GunAttachments.has(gun, AttachmentItem.Effect.EM_CAPACITOR) ? this.fullChargeTicks / 2 : this.fullChargeTicks;
        int held = this.m_8105_(gun) - remainingTicks;
        float charge = Math.min(1.0f, (float)held / (float)chargeNeeded);
        float damage = this.baseDamage + (this.chargedDamage - this.baseDamage) * charge;
        if (GunAttachments.has(gun, AttachmentItem.Effect.QUANTUM_SILENCER) && player.m_6047_()) {
            damage *= 2.5f;
        }
        if (GunAttachments.has(gun, AttachmentItem.Effect.SKIPPY_AI) && player.m_217043_().m_188501_() < 0.1f) {
            damage *= 2.0f;
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.skippy_crit"), false);
        }
        GunAttachments.setAmmo(gun, GunAttachments.getAmmo(gun) - 1);
        double range = GunAttachments.has(gun, AttachmentItem.Effect.MAGNETIC_ACCELERATOR) ? 100.0 : 50.0;
        this.fireRail(level, player, damage, charge >= 1.0f ? this.blockPierce : 1, range);
        if (!GunAttachments.has(gun, AttachmentItem.Effect.QUANTUM_SILENCER)) {
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12089_, SoundSource.PLAYERS, 0.6f, 2.0f);
        }
        WeaponUtil.applyMirmidon(player, gun, damage);
        player.m_36335_().m_41524_((Item)this, 15);
    }

    private void fireRail(Level level, Player player, float damage, int maxBlockPierce, double range) {
        Vec3 start = player.m_146892_();
        Vec3 dir = player.m_20154_();
        HashSet<BlockPos> pierced = new HashSet<BlockPos>();
        HashSet<Integer> hitEntities = new HashSet<Integer>();
        int entitiesHit = 0;
        for (double d = 0.5; d <= range; d += 0.5) {
            Vec3 point = start.m_82549_(dir.m_82490_(d));
            BlockPos pos = BlockPos.m_274446_((Position)point);
            BlockState state = level.m_8055_(pos);
            if (!state.m_60795_() && state.m_60804_((BlockGetter)level, pos)) {
                if (state.m_60713_(Blocks.f_50752_) || state.m_60713_(Blocks.f_50080_)) break;
                pierced.add(pos);
                if (pierced.size() > maxBlockPierce) break;
            }
            for (LivingEntity target : level.m_6443_(LivingEntity.class, new AABB(point, point).m_82400_(0.75), e -> e != player && e.m_6084_())) {
                if (!hitEntities.add(target.m_19879_())) continue;
                target.m_6469_(level.m_269111_().m_269075_(player), damage);
                target.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 60));
                ++entitiesHit;
            }
            if (entitiesHit >= this.entityPierce) break;
            if (!(level instanceof ServerLevel)) continue;
            ServerLevel serverLevel = (ServerLevel)level;
            if ((int)(d * 2.0) % 2 != 0) continue;
            serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123810_, point.f_82479_, point.f_82480_, point.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.tech_weapon").m_130940_(ChatFormatting.GOLD));
        tooltip.add((Component)Component.m_237110_((String)"tooltip.cybervillage.tech_pierce", (Object[])new Object[]{this.blockPierce}).m_130940_(ChatFormatting.GRAY));
        WeaponUtil.appendWeaponTooltip(stack, tooltip, this.chargedDamage, this.magSize);
    }
}

