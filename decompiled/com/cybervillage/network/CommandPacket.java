/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.cybervillage.network;

import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class CommandPacket {
    public static final byte SELECT_ALL = 0;
    public static final byte SELECT_ALPHA = 1;
    public static final byte SELECT_BETA = 2;
    private static final double COMMAND_RANGE = 64.0;
    private final byte selection;

    public CommandPacket(byte selection) {
        this.selection = selection;
    }

    public static void encode(CommandPacket packet, FriendlyByteBuf buf) {
        buf.writeByte((int)packet.selection);
    }

    public static CommandPacket decode(FriendlyByteBuf buf) {
        return new CommandPacket(buf.readByte());
    }

    public static void handle(CommandPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            if (player != null) {
                CommandPacket.dispatch(player, packet.selection);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void dispatch(ServerPlayer player, byte selection) {
        Object object;
        List captains = player.m_284548_().m_6443_(CaptainEntity.class, player.m_20191_().m_82400_(64.0), c -> c.isOwnedBy((Player)player) && (selection == 0 || c.getSquadId() == selection - 1));
        if (captains.isEmpty()) {
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.no_captains"), true);
            return;
        }
        Vec3 eyes = player.m_146892_();
        Vec3 view = player.m_20252_(1.0f);
        Vec3 end = eyes.m_82549_(view.m_82490_(64.0));
        EntityHitResult entityHit = ProjectileUtil.m_37304_((Level)player.m_9236_(), (Entity)player, (Vec3)eyes, (Vec3)end, (AABB)new AABB(eyes, end).m_82400_(1.0), e -> {
            LivingEntity living;
            return e instanceof LivingEntity && (living = (LivingEntity)e).m_6084_() && !(e instanceof CaptainEntity) && e != player && !(e instanceof SquadUnitEntity);
        });
        if (entityHit != null && (object = entityHit.m_82443_()) instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)object;
            for (CaptainEntity captain : captains) {
                captain.orderFocus(target);
            }
            player.m_284548_().m_8767_((ParticleOptions)ParticleTypes.f_123792_, target.m_20185_(), target.m_20186_() + (double)target.m_20206_() + 0.5, target.m_20189_(), 5, 0.3, 0.3, 0.3, 0.0);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.order_focus", (Object[])new Object[]{target.m_5446_()}).m_130940_(ChatFormatting.RED), true);
            return;
        }
        BlockHitResult blockHit = player.m_9236_().m_45547_(new ClipContext(eyes, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)player));
        if (blockHit.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos target = blockHit.m_82425_().m_121945_(blockHit.m_82434_());
            for (CaptainEntity captain : captains) {
                captain.orderMove(target);
            }
            player.m_284548_().m_8767_((ParticleOptions)ParticleTypes.f_123748_, (double)target.m_123341_() + 0.5, (double)target.m_123342_() + 0.5, (double)target.m_123343_() + 0.5, 10, 0.5, 0.5, 0.5, 0.0);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.order_move", (Object[])new Object[]{target.m_123341_(), target.m_123342_(), target.m_123343_()}).m_130940_(ChatFormatting.GREEN), true);
        } else {
            for (CaptainEntity captain : captains) {
                captain.orderReset();
            }
            player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.order_reset"), true);
        }
    }
}

