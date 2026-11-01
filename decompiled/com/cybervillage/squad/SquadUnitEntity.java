/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.Zombie
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.cybervillage.squad;

import com.cybervillage.energy.EnergyGrid;
import com.cybervillage.entity.SmartBulletEntity;
import com.cybervillage.registry.ModEffects;
import com.cybervillage.registry.ModItems;
import com.cybervillage.squad.CaptainEntity;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SquadUnitEntity
extends Zombie {
    private static final double[][] FORMATION = new double[][]{{-2.0, -2.0}, {2.0, -2.0}, {-4.0, -4.0}, {4.0, -4.0}, {0.0, -3.5}, {-6.0, -6.0}, {6.0, -6.0}, {0.0, -6.0}};
    public static final String CHIP_NONE = "none";
    public static final String CHIP_SNIPER = "sniper_core";
    public static final String CHIP_BERSERK = "berserk_protocol";
    @Nullable
    private UUID captainUUID;
    @Nullable
    private CaptainEntity captainCache;
    private String chip = "none";
    private int fireCooldown;
    private static final int MAX_ENERGY = 100;
    private int energy = 100;

    public SquadUnitEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42387_));
        this.m_21530_();
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Zombie.m_34328_().m_22268_(Attributes.f_22276_, 24.0).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22284_, 5.0).m_22268_(Attributes.f_22277_, 48.0).m_22268_(Attributes.f_22281_, 3.0);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0f));
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

    public void setCaptain(CaptainEntity captain) {
        this.captainUUID = captain.m_20148_();
        this.captainCache = captain;
    }

    @Nullable
    private CaptainEntity captain() {
        if (this.captainCache != null && this.captainCache.m_6084_() && !this.captainCache.m_213877_()) {
            return this.captainCache;
        }
        this.captainCache = null;
        if (this.f_19797_ % 40 == 0) {
            List captains = this.m_9236_().m_6443_(CaptainEntity.class, this.m_20191_().m_82400_(32.0), c -> c.m_6084_() && (this.captainUUID == null || c.m_20148_().equals(this.captainUUID)));
            if (captains.isEmpty() && this.captainUUID != null) {
                captains = this.m_9236_().m_6443_(CaptainEntity.class, this.m_20191_().m_82400_(32.0), LivingEntity::m_6084_);
            }
            this.captainCache = captains.stream().min(Comparator.comparingDouble(arg_0 -> ((SquadUnitEntity)this).m_20280_(arg_0))).orElse(null);
            if (this.captainCache != null) {
                this.captainUUID = this.captainCache.m_20148_();
            }
        }
        return this.captainCache;
    }

    private boolean isHacked() {
        return this.m_21023_((MobEffect)ModEffects.HACKED.get());
    }

    public boolean chargeEnergy(int amount) {
        if (this.energy >= 100) {
            return false;
        }
        this.energy = Math.min(100, this.energy + amount);
        return true;
    }

    private boolean lowEnergy() {
        return this.energy < 15;
    }

    private String activeChip() {
        CaptainEntity captain = this.captainCache;
        return captain != null && captain.isDegraded() ? CHIP_NONE : this.chip;
    }

    @Nullable
    private LivingEntity resolveTarget(@Nullable CaptainEntity captain) {
        LivingEntity focus;
        Entity entity;
        if (this.isHacked()) {
            Player owner;
            if (captain != null && captain.getOwnerUUID() != null && (owner = this.m_9236_().m_46003_(captain.getOwnerUUID())) != null && (double)this.m_20270_((Entity)owner) < 24.0) {
                return owner;
            }
            return captain;
        }
        if (captain != null && captain.getFocusTargetId() >= 0 && (entity = this.m_9236_().m_6815_(captain.getFocusTargetId())) instanceof LivingEntity && (focus = (LivingEntity)entity).m_6084_()) {
            return focus;
        }
        double range = CHIP_SNIPER.equals(this.activeChip()) ? 35.0 : 16.0;
        return this.m_9236_().m_6443_(Monster.class, this.m_20191_().m_82400_(range), m -> m.m_6084_() && !(m instanceof CaptainEntity) && !(m instanceof SquadUnitEntity)).stream().min(Comparator.comparingDouble(arg_0 -> ((SquadUnitEntity)this).m_20280_(arg_0))).orElse(null);
    }

    public void m_8107_() {
        ServerLevel serverLevel;
        BlockPos pad;
        Level level;
        super.m_8107_();
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.fireCooldown > 0) {
            --this.fireCooldown;
        }
        this.applySeparation();
        CaptainEntity captain = this.captain();
        this.m_20260_(captain != null && captain.isStealth());
        if (this.lowEnergy() && this.f_19797_ % 10 == 0 && (level = this.m_9236_()) instanceof ServerLevel && (pad = EnergyGrid.nearestPad(serverLevel = (ServerLevel)level, this.m_20182_(), 32.0)) != null) {
            this.m_21573_().m_26519_((double)pad.m_123341_() + 0.5, (double)pad.m_123342_() + 1.0, (double)pad.m_123343_() + 0.5, 1.2);
            return;
        }
        LivingEntity target = this.resolveTarget(captain);
        if (target != null) {
            this.engage(target);
        } else if (captain != null && this.f_19797_ % 10 == 0) {
            this.followFormation(captain);
        }
    }

    private void applySeparation() {
        if (this.f_19797_ % 5 != 0) {
            return;
        }
        for (LivingEntity ally : this.m_9236_().m_6443_(LivingEntity.class, this.m_20191_().m_82400_(1.2), e -> e != this && (e instanceof SquadUnitEntity || e instanceof CaptainEntity || e instanceof Player))) {
            Vec3 push = this.m_20182_().m_82546_(ally.m_20182_());
            if (!(push.m_82556_() > 1.0E-4)) continue;
            push = push.m_82541_().m_82490_(0.08);
            this.m_20256_(this.m_20184_().m_82520_(push.f_82479_, 0.0, push.f_82481_));
        }
    }

    private void followFormation(CaptainEntity captain) {
        double scale = captain.isDegraded() ? 0.4 : 1.0;
        double[] rawOffset = FORMATION[Math.floorMod(this.m_19879_(), FORMATION.length)];
        double[] offset = new double[]{rawOffset[0] * scale, rawOffset[1] * scale};
        double yaw = Math.toRadians(captain.m_146908_());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);
        double x = captain.m_20185_() + offset[0] * cos - offset[1] * sin;
        double z = captain.m_20189_() + offset[0] * sin + offset[1] * cos;
        if (this.m_20275_(x, captain.m_20186_(), z) > 4.0) {
            double speed = captain.getState() == CaptainEntity.State.RETREAT ? 1.4 : 1.15;
            this.m_21573_().m_26519_(x, captain.m_20186_(), z, speed);
        }
    }

    private void engage(LivingEntity target) {
        this.m_21563_().m_148051_((Entity)target);
        double distance = this.m_20270_((Entity)target);
        if (CHIP_BERSERK.equals(this.activeChip())) {
            if (this.f_19797_ % 10 == 0) {
                this.m_21573_().m_5624_((Entity)target, 1.3);
            }
        } else if (this.f_19797_ % 10 == 0) {
            double d = CHIP_SNIPER.equals(this.activeChip()) ? 35.0 : 14.0;
            if (distance > d) {
                this.m_21573_().m_5624_((Entity)target, 1.1);
            } else {
                Vec3 toTarget = target.m_20182_().m_82546_(this.m_20182_()).m_82541_();
                Vec3 strafe = new Vec3(-toTarget.f_82481_, 0.0, toTarget.f_82479_).m_82490_(this.m_19879_() % 2 == 0 ? 3.0 : -3.0);
                this.m_21573_().m_26519_(this.m_20185_() + strafe.f_82479_, this.m_20186_(), this.m_20189_() + strafe.f_82481_, 1.0);
            }
        }
        this.fireAt(target);
    }

    private void fireAt(LivingEntity target) {
        float velocity;
        double damage;
        if (this.fireCooldown > 0 || !this.m_142582_((Entity)target) || this.energy <= 0) {
            return;
        }
        --this.energy;
        float spread = switch (this.activeChip()) {
            case CHIP_SNIPER -> {
                this.fireCooldown = 60;
                damage = 9.0;
                velocity = 4.0f;
                yield 0.5f;
            }
            case CHIP_BERSERK -> {
                this.fireCooldown = 6;
                damage = 2.0;
                velocity = 2.5f;
                yield 8.0f;
            }
            default -> {
                this.fireCooldown = 30;
                damage = 3.0;
                velocity = 2.5f;
                yield 2.0f;
            }
        };
        SmartBulletEntity bullet = new SmartBulletEntity(this.m_9236_(), (LivingEntity)this, damage, false, 0.0);
        Vec3 dir = target.m_146892_().m_82546_(this.m_146892_()).m_82541_();
        bullet.m_6686_(dir.f_82479_, dir.f_82480_, dir.f_82481_, velocity, spread);
        this.m_9236_().m_7967_((Entity)bullet);
        this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11738_, SoundSource.NEUTRAL, 0.3f, 2.0f);
    }

    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        ItemStack held = player.m_21120_(hand);
        String newChip = null;
        if (held.m_150930_((Item)ModItems.SNIPER_CORE.get())) {
            newChip = CHIP_SNIPER;
        } else if (held.m_150930_((Item)ModItems.BERSERK_PROTOCOL.get())) {
            newChip = CHIP_BERSERK;
        }
        if (newChip != null) {
            if (!this.m_9236_().f_46443_) {
                this.chip = newChip;
                if (!player.m_150110_().f_35937_) {
                    held.m_41774_(1);
                }
                this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11671_, SoundSource.NEUTRAL, 0.5f, 1.8f);
                player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.chip_installed", (Object[])new Object[]{Component.m_237115_((String)("item.cybervillage." + newChip))}).m_130940_(ChatFormatting.AQUA), true);
            }
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return super.m_6071_(player, hand);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        if (this.captainUUID != null) {
            tag.m_128362_("Captain", this.captainUUID);
        }
        tag.m_128359_("Chip", this.chip);
        tag.m_128405_("Energy", this.energy);
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128403_("Captain")) {
            this.captainUUID = tag.m_128342_("Captain");
        }
        this.chip = tag.m_128461_("Chip").isEmpty() ? CHIP_NONE : tag.m_128461_("Chip");
        this.energy = tag.m_128441_("Energy") ? tag.m_128451_("Energy") : 100;
    }
}

