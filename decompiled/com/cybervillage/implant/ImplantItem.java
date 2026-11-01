/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.cybervillage.implant;

import com.cybervillage.implant.Implants;
import com.cybervillage.network.NetworkHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ImplantItem
extends Item {
    private final String implantId;
    private final int humanityCost;

    public ImplantItem(Item.Properties properties, String implantId, int humanityCost) {
        super(properties.m_41487_(1));
        this.implantId = implantId;
        this.humanityCost = humanityCost;
    }

    public String getImplantId() {
        return this.implantId;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (!level.f_46443_) {
            if (Implants.has(player, this.implantId)) {
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.already_installed"), true);
                return InteractionResultHolder.m_19100_((Object)stack);
            }
            if (!Implants.install(player, this.implantId, this.humanityCost)) {
                player.m_5661_((Component)Component.m_237115_((String)"message.cybervillage.humanity_limit"), true);
                return InteractionResultHolder.m_19100_((Object)stack);
            }
            if (!player.m_150110_().f_35937_) {
                stack.m_41774_(1);
            }
            level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11671_, SoundSource.PLAYERS, 0.6f, 1.8f);
            player.m_5661_((Component)Component.m_237110_((String)"message.cybervillage.installed", (Object[])new Object[]{Component.m_237115_((String)this.m_5524_()), Implants.getHumanity(player), 100}), false);
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                NetworkHandler.syncToClient(serverPlayer);
            }
        }
        return InteractionResultHolder.m_19092_((Object)stack, (boolean)level.m_5776_());
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add((Component)Component.m_237115_((String)("tooltip.cybervillage.implant." + this.implantId)).m_130940_(ChatFormatting.GRAY));
        tooltip.add((Component)Component.m_237110_((String)"tooltip.cybervillage.humanity_cost", (Object[])new Object[]{this.humanityCost}).m_130940_(ChatFormatting.LIGHT_PURPLE));
        tooltip.add((Component)Component.m_237115_((String)"tooltip.cybervillage.implant_help").m_130940_(ChatFormatting.DARK_GRAY));
    }
}

