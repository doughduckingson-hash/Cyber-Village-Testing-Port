/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.SimpleMenuProvider
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.network.NetworkHooks
 */
package com.cybervillage.block;

import com.cybervillage.menu.WeaponTerminalMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class WeaponTerminalBlock
extends Block {
    public WeaponTerminalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.f_46443_ && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            NetworkHooks.openScreen((ServerPlayer)serverPlayer, (MenuProvider)new SimpleMenuProvider((windowId, inv, p) -> new WeaponTerminalMenu(windowId, inv), (Component)Component.m_237115_((String)"container.cybervillage.weapon_terminal")));
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }
}

