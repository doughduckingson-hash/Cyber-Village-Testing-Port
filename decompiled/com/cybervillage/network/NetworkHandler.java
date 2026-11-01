/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.network.PacketDistributor
 *  net.minecraftforge.network.simple.SimpleChannel
 */
package com.cybervillage.network;

import com.cybervillage.engram.EngramData;
import com.cybervillage.implant.Implants;
import com.cybervillage.network.ActionPacket;
import com.cybervillage.network.CommandPacket;
import com.cybervillage.network.SyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel((ResourceLocation)new ResourceLocation("cybervillage", "main"), () -> "1", "1"::equals, "1"::equals);

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, ActionPacket.class, ActionPacket::encode, ActionPacket::decode, ActionPacket::handle);
        CHANNEL.registerMessage(id++, SyncPacket.class, SyncPacket::encode, SyncPacket::decode, SyncPacket::handle);
        CHANNEL.registerMessage(id++, CommandPacket.class, CommandPacket::encode, CommandPacket::decode, CommandPacket::handle);
    }

    public static void syncToClient(ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), (Object)new SyncPacket(EngramData.get((Player)player).ordinal(), Implants.has((Player)player, "reinforced_tendons"), Implants.getHumanity((Player)player)));
    }
}

