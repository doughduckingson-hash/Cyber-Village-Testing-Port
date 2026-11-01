/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 */
package com.cybervillage.squad;

import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class ContractItem
extends Item {
    private final Supplier<? extends EntityType<? extends Mob>> entityType;

    public ContractItem(Item.Properties properties, Supplier<? extends EntityType<? extends Mob>> entityType) {
        super(properties);
        this.entityType = entityType;
    }

    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        Player player = context.m_43723_();
        BlockPos pos = context.m_8083_().m_121945_(context.m_43719_());
        Mob mob = (Mob)this.entityType.get().m_20615_((Level)serverLevel);
        if (mob == null || player == null) {
            return InteractionResult.FAIL;
        }
        mob.m_7678_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, player.m_146908_(), 0.0f);
        mob.m_6518_((ServerLevelAccessor)serverLevel, serverLevel.m_6436_(pos), MobSpawnType.MOB_SUMMONED, null, null);
        if (mob instanceof CaptainEntity) {
            CaptainEntity captain = (CaptainEntity)mob;
            captain.setOwner(player);
        } else if (mob instanceof SquadUnitEntity) {
            SquadUnitEntity unit = (SquadUnitEntity)mob;
            List captains = serverLevel.m_6443_(CaptainEntity.class, mob.m_20191_().m_82400_(32.0), c -> c.isOwnedBy(player));
            captains.stream().min(Comparator.comparingDouble(arg_0 -> ((Mob)mob).m_20280_(arg_0))).ifPresent(unit::setCaptain);
        }
        serverLevel.m_7967_((Entity)mob);
        serverLevel.m_5594_(null, pos, SoundEvents.f_11736_, SoundSource.NEUTRAL, 0.7f, 1.6f);
        if (!player.m_150110_().f_35937_) {
            context.m_43722_().m_41774_(1);
        }
        return InteractionResult.CONSUME;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.contract").m_130940_(ChatFormatting.GRAY));
    }
}

