/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package com.cybervillage.network;

import com.cybervillage.engram.Abilities;
import com.cybervillage.squad.TacticalActions;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ActionPacket {
    public static final byte USE_ABILITY = 0;
    public static final byte SWITCH_ENGRAM = 1;
    public static final byte OPTICAL_CAMO = 2;
    public static final byte EMP_PING = 3;
    public static final byte STEALTH_TOGGLE = 4;
    private final byte action;

    public ActionPacket(byte action) {
        this.action = action;
    }

    public static void encode(ActionPacket packet, FriendlyByteBuf buf) {
        buf.writeByte((int)packet.action);
    }

    public static ActionPacket decode(FriendlyByteBuf buf) {
        return new ActionPacket(buf.readByte());
    }

    public static void handle(ActionPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            if (player == null) {
                return;
            }
            switch (packet.action) {
                case 0: {
                    Abilities.useEngramAbility(player);
                    break;
                }
                case 1: {
                    Abilities.switchEngram(player);
                    break;
                }
                case 2: {
                    Abilities.useOpticalCamo(player);
                    break;
                }
                case 3: {
                    TacticalActions.empPing(player);
                    break;
                }
                case 4: {
                    TacticalActions.toggleStealth(player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

