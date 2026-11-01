/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.squad;

import com.cybervillage.energy.EnergyGrid;
import com.cybervillage.entity.SmartBulletEntity;
import com.cybervillage.registry.ModEffects;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CaptainEntity
extends Zombie {
    @Nullable
    private UUID ownerUUID;
    private int squadId;
    private State state = State.STATIONARY;
    @Nullable
    private BlockPos movePos;
    @Nullable
    private BlockPos anchorPos;
    private int focusTargetId = -1;
    private int fireCooldown;
    private boolean degraded;
    private boolean stealth;
    @Nullable
    private BlockPos mineFrom;
    @Nullable
    private BlockPos mineTo;

    public CaptainEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42383_));
        this.m_21530_();
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 50.0).m_22268_(Attributes.f_22279_, 0.32).m_22268_(Attributes.f_22284_, 10.0).m_22268_(Attributes.f_22277_, 64.0).m_22268_(Attributes.f_22281_, 5.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(5, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8));
        this.f_21345_.m_25352_(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
        this.f_21345_.m_25352_(7, (Goal)new RandomLookAroundGoal((Mob)this));
    }

    protected void m_6878_() {
    }

    protected boolean m_5884_() {
        return false;
    }

    protected boolean m_7593_() {
        return false;
    }

    public void setOwner(Player player) {
        this.ownerUUID = player.m_20148_();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public boolean isOwnedBy(Player player) {
        return player.m_20148_().equals(this.ownerUUID);
    }

    public int getSquadId() {
        return this.squadId;
    }

    public State getState() {
        return this.state;
    }

    public int getFocusTargetId() {
        return this.focusTargetId;
    }

    public void setAnchor(BlockPos pos) {
        this.anchorPos = pos;
    }

    public void orderMove(BlockPos pos) {
        this.movePos = pos;
        this.focusTargetId = -1;
        this.state = State.TACTICAL_MOVE;
    }

    public void orderFocus(LivingEntity target) {
        this.focusTargetId = target.m_19879_();
        this.state = State.COMBAT_SUPPORT;
    }

    public void orderReset() {
        this.movePos = null;
        this.focusTargetId = -1;
        this.state = State.STATIONARY;
    }

    public void orderMine(BlockPos from, BlockPos to) {
        this.mineFrom = from;
        this.mineTo = to;
        this.focusTargetId = -1;
        this.state = State.MINING;
    }

    public boolean isDegraded() {
        return this.degraded;
    }

    public boolean isStealth() {
        return this.stealth;
    }

    public void setStealth(boolean stealth) {
        this.stealth = stealth;
    }

    private boolean isHacked() {
        return this.m_21023_((MobEffect)ModEffects.HACKED.get());
    }

    @Nullable
    private LivingEntity resolveTarget() {
        LivingEntity focus;
        Entity entity;
        if (this.isHacked()) {
            Player owner = this.ownerUUID == null ? null : this.m_9236_().m_46003_(this.ownerUUID);
            return owner != null && (double)this.m_20270_((Entity)owner) < 24.0 ? owner : null;
        }
        if (this.focusTargetId >= 0 && (entity = this.m_9236_().m_6815_(this.focusTargetId)) instanceof LivingEntity && (focus = (LivingEntity)entity).m_6084_() && (double)this.m_20270_((Entity)focus) < 48.0) {
            return focus;
        }
        this.focusTargetId = -1;
        return this.m_9236_().m_6443_(Monster.class, this.m_20191_().m_82400_(20.0), m -> m.m_6084_() && !(m instanceof CaptainEntity) && !(m instanceof SquadUnitEntity)).stream().min(Comparator.comparingDouble(arg_0 -> ((CaptainEntity)this).m_20280_(arg_0))).orElse(null);
    }

    public void m_8107_() {
        Level level;
        super.m_8107_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.fireCooldown > 0) {
            --this.fireCooldown;
        }
        this.m_20260_(this.stealth);
        if (this.f_19797_ % 10 != 0) {
            return;
        }
        if (this.f_19797_ % 20 == 0 && this.anchorPos != null && (level = this.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            boolean bl = this.degraded = !EnergyGrid.isPowered(serverLevel, this.anchorPos);
        }
        if (this.m_21223_() < this.m_21233_() * 0.2f && this.anchorPos != null && this.state != State.RETREAT) {
            this.state = State.RETREAT;
        }
        switch (this.state) {
            case RETREAT: {
                if (this.anchorPos == null || this.m_20183_().m_123331_((Vec3i)this.anchorPos) < 9.0) {
                    this.m_5634_(1.0f);
                    if (!(this.m_21223_() > this.m_21233_() * 0.6f)) break;
                    this.state = State.STATIONARY;
                    break;
                }
                this.m_21573_().m_26519_((double)this.anchorPos.m_123341_(), (double)this.anchorPos.m_123342_(), (double)this.anchorPos.m_123343_(), 1.4);
                break;
            }
            case TACTICAL_MOVE: {
                if (this.movePos == null || this.m_20183_().m_123331_((Vec3i)this.movePos) < 6.0) {
                    this.movePos = null;
                    this.state = State.STATIONARY;
                    break;
                }
                this.m_21573_().m_26519_((double)this.movePos.m_123341_(), (double)this.movePos.m_123342_(), (double)this.movePos.m_123343_(), 1.2);
                break;
            }
            case MINING: {
                this.tickMining();
                break;
            }
            case COMBAT_SUPPORT: 
            case STATIONARY: {
                LivingEntity target = this.resolveTarget();
                if (target != null) {
                    this.state = State.COMBAT_SUPPORT;
                    double distance = this.m_20270_((Entity)target);
                    if (distance > 18.0) {
                        this.m_21573_().m_5624_((Entity)target, 1.1);
                    } else if (distance < 10.0) {
                        Vec3 away = this.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82490_(8.0).m_82549_(this.m_20182_());
                        this.m_21573_().m_26519_(away.f_82479_, away.f_82480_, away.f_82481_, 1.1);
                    } else {
                        this.m_21573_().m_26573_();
                    }
                    this.fireAt(target);
                    break;
                }
                if (this.state != State.COMBAT_SUPPORT) break;
                this.state = State.STATIONARY;
            }
        }
    }

    private void tickMining() {
        Level level;
        if (this.mineFrom == null || this.mineTo == null || !((level = this.m_9236_()) instanceof ServerLevel)) {
            this.state = State.STATIONARY;
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        if (this.f_19797_ % 40 != 0) {
            return;
        }
        BlockPos min = new BlockPos(Math.min(this.mineFrom.m_123341_(), this.mineTo.m_123341_()), Math.min(this.mineFrom.m_123342_(), this.mineTo.m_123342_()), Math.min(this.mineFrom.m_123343_(), this.mineTo.m_123343_()));
        BlockPos max = new BlockPos(Math.max(this.mineFrom.m_123341_(), this.mineTo.m_123341_()), Math.max(this.mineFrom.m_123342_(), this.mineTo.m_123342_()), Math.max(this.mineFrom.m_123343_(), this.mineTo.m_123343_()));
        BlockPos ore = null;
        for (BlockPos pos : BlockPos.m_121940_((BlockPos)min, (BlockPos)max)) {
            if (!this.m_9236_().m_8055_(pos).m_204336_(BlockTags.create((ResourceLocation)new ResourceLocation("forge", "ores")))) continue;
            ore = pos.m_7949_();
            break;
        }
        if (ore == null) {
            this.mineFrom = null;
            this.mineTo = null;
            this.state = State.STATIONARY;
            return;
        }
        BlockPos center = new BlockPos((min.m_123341_() + max.m_123341_()) / 2, (min.m_123342_() + max.m_123342_()) / 2, (min.m_123343_() + max.m_123343_()) / 2);
        if (this.m_20183_().m_123331_((Vec3i)center) > 576.0) {
            this.m_21573_().m_26519_((double)center.m_123341_(), (double)center.m_123342_(), (double)center.m_123343_(), 1.1);
            return;
        }
        Vec3 from = this.m_146892_();
        Vec3 to = Vec3.m_82512_((Vec3i)ore);
        for (double d = 0.0; d <= 1.0; d += 0.08) {
            Vec3 p = from.m_165921_(to, d);
            serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123810_, p.f_82479_, p.f_82480_, p.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
        }
        List drops = Block.m_49869_((BlockState)this.m_9236_().m_8055_(ore), (ServerLevel)serverLevel, (BlockPos)ore, null);
        serverLevel.m_46597_(ore, Blocks.f_50069_.m_49966_());
        this.m_9236_().m_5594_(null, ore, SoundEvents.f_11739_, SoundSource.NEUTRAL, 0.5f, 1.8f);
        Vec3 dropAt = this.anchorPos != null ? Vec3.m_82512_((Vec3i)this.anchorPos.m_7494_()) : this.m_20182_();
        for (ItemStack stack : drops) {
            ItemEntity item = new ItemEntity(this.m_9236_(), dropAt.f_82479_, dropAt.f_82480_, dropAt.f_82481_, stack);
            this.m_9236_().m_7967_((Entity)item);
        }
    }

    private void fireAt(LivingEntity target) {
        if (this.fireCooldown > 0 || !this.m_142582_((Entity)target)) {
            return;
        }
        this.fireCooldown = 25;
        this.m_21563_().m_148051_((Entity)target);
        SmartBulletEntity bullet = new SmartBulletEntity(this.m_9236_(), (LivingEntity)this, 3.0, true, 12.0);
        Vec3 dir = target.m_146892_().m_82546_(this.m_146892_()).m_82541_();
        bullet.m_6686_(dir.f_82479_, dir.f_82480_, dir.f_82481_, 2.5f, 2.0f);
        this.m_9236_().m_7967_((Entity)bullet);
        this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11738_, SoundSource.NEUTRAL, 0.4f, 2.0f);
    }

    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (!this.m_9236_().f_46443_ && this.isOwnedBy(player) && player.m_6144_() && player.m_21120_(hand).m_41619_()) {
            this.squadId = 1 - this.squadId;
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.squad_assigned", (Object[])new Object[]{Component.m_237115_((String)("squad.cybervillage." + (this.squadId == 0 ? "alpha" : "beta")))}).m_130940_(ChatFormatting.AQUA), true);
            return InteractionResult.SUCCESS;
        }
        return super.m_6071_(player, hand);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        if (this.ownerUUID != null) {
            tag.m_128362_("Owner", this.ownerUUID);
        }
        tag.m_128405_("SquadId", this.squadId);
        tag.m_128359_("State", this.state.name());
        tag.m_128379_("Stealth", this.stealth);
        if (this.mineFrom != null && this.mineTo != null) {
            tag.m_128365_("MineFrom", (Tag)NbtUtils.m_129224_((BlockPos)this.mineFrom));
            tag.m_128365_("MineTo", (Tag)NbtUtils.m_129224_((BlockPos)this.mineTo));
        }
        if (this.movePos != null) {
            tag.m_128365_("MovePos", (Tag)NbtUtils.m_129224_((BlockPos)this.movePos));
        }
        if (this.anchorPos != null) {
            tag.m_128365_("AnchorPos", (Tag)NbtUtils.m_129224_((BlockPos)this.anchorPos));
        }
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128403_("Owner")) {
            this.ownerUUID = tag.m_128342_("Owner");
        }
        this.squadId = tag.m_128451_("SquadId");
        try {
            this.state = State.valueOf(tag.m_128461_("State"));
        }
        catch (IllegalArgumentException e) {
            this.state = State.STATIONARY;
        }
        if (tag.m_128441_("MovePos")) {
            this.movePos = NbtUtils.m_129239_((CompoundTag)tag.m_128469_("MovePos"));
        }
        if (tag.m_128441_("AnchorPos")) {
            this.anchorPos = NbtUtils.m_129239_((CompoundTag)tag.m_128469_("AnchorPos"));
        }
        this.stealth = tag.m_128471_("Stealth");
        if (tag.m_128441_("MineFrom")) {
            this.mineFrom = NbtUtils.m_129239_((CompoundTag)tag.m_128469_("MineFrom"));
            this.mineTo = NbtUtils.m_129239_((CompoundTag)tag.m_128469_("MineTo"));
        }
    }

    public static enum State {
        STATIONARY,
        TACTICAL_MOVE,
        COMBAT_SUPPORT,
        RETREAT,
        MINING;

    }
}

