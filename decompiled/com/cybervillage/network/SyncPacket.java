/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.cybervillage.network;

import com.cybervillage.client.ClientState;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class SyncPacket {
    private final int engram;
    private final boolean doubleJump;
    private final int humanity;

    public SyncPacket(int engram, boolean doubleJump, int humanity) {
        this.engram = engram;
        this.doubleJump = doubleJump;
        this.humanity = humanity;
    }

    public static void encode(SyncPacket packet, FriendlyByteBuf buf) {
        buf.m_130130_(packet.engram);
        buf.writeBoolean(packet.doubleJump);
        buf.m_130130_(packet.humanity);
    }

    public static SyncPacket decode(FriendlyByteBuf buf) {
        return new SyncPacket(buf.m_130242_(), buf.readBoolean(), buf.m_130242_());
    }

    public static void handle(SyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn((Dist)Dist.CLIENT, () -> () -> {
            ClientState.INSTANCE.engram = packet.engram;
            ClientState.INSTANCE.doubleJump = packet.doubleJump;
            ClientState.INSTANCE.humanity = packet.humanity;
        }));
        ctx.get().setPacketHandled(true);
    }
}

