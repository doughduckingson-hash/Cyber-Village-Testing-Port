/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package com.cybervillage.threat;

import com.cybervillage.engram.Engram;
import com.cybervillage.engram.EngramData;
import com.cybervillage.implant.Implants;
import com.cybervillage.network.NetworkHandler;
import com.cybervillage.threat.ThreatManager;
import com.cybervillage.warfare.CorporateWarfareManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CyberCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"cyber").then(((LiteralArgumentBuilder)Commands.m_82127_((String)"threat").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"level", (ArgumentType)IntegerArgumentType.integer((int)0, (int)5)).requires(source -> source.m_6761_(2))).executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            ThreatManager.setLevel(player, IntegerArgumentType.getInteger((CommandContext)ctx, (String)"level"));
            return 1;
        }))).executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237110_((String)"threat.cybervillage.current", (Object[])new Object[]{ThreatManager.getLevel(player)}), false);
            return 1;
        }))).then(Commands.m_82127_((String)"engram").then(Commands.m_82129_((String)"id", (ArgumentType)IntegerArgumentType.integer((int)1, (int)3)).executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            EngramData.set((Player)player, Engram.byOrdinal(IntegerArgumentType.getInteger((CommandContext)ctx, (String)"id") - 1));
            NetworkHandler.syncToClient(player);
            ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237115_((String)("engram.cybervillage." + EngramData.get((Player)player).getSerializedName())), false);
            return 1;
        })))).then(((LiteralArgumentBuilder)Commands.m_82127_((String)"alert").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"level", (ArgumentType)IntegerArgumentType.integer((int)0, (int)5)).requires(source -> source.m_6761_(2))).executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            CorporateWarfareManager manager = CorporateWarfareManager.get(player.m_284548_());
            int target = IntegerArgumentType.getInteger((CommandContext)ctx, (String)"level");
            manager.raiseAlert(player.m_146902_(), target - manager.getAlert(player.m_146902_()));
            ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237110_((String)"message.cybervillage.alert_status", (Object[])new Object[]{target}), false);
            return 1;
        }))).executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            CorporateWarfareManager manager = CorporateWarfareManager.get(player.m_284548_());
            ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237110_((String)"message.cybervillage.alert_status", (Object[])new Object[]{manager.getAlert(player.m_146902_())}), false);
            return 1;
        }))).then(Commands.m_82127_((String)"humanity").executes(ctx -> {
            ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
            ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237110_((String)"message.cybervillage.humanity_status", (Object[])new Object[]{Implants.getHumanity((Player)player), 100}), false);
            return 1;
        })));
    }
}

