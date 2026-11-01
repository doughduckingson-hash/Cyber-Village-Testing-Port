/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.RegistryObject
 */
package com.cybervillage.registry;

import com.cybervillage.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create((ResourceKey)Registries.f_279569_, (String)"cybervillage");
    public static final RegistryObject<CreativeModeTab> CYBERVILLAGE_TAB = TABS.register("cybervillage", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)"itemGroup.cybervillage")).m_257737_(() -> new ItemStack((ItemLike)ModItems.YUKIMURA.get())).m_257501_((params, output) -> {
        output.m_246326_((ItemLike)ModItems.YUKIMURA.get());
        output.m_246326_((ItemLike)ModItems.PALISADE.get());
        output.m_246326_((ItemLike)ModItems.ASHURA.get());
        output.m_246326_((ItemLike)ModItems.OVERLORD.get());
        output.m_246326_((ItemLike)ModItems.RAIDEN_KATANA.get());
        output.m_246326_((ItemLike)ModItems.MONOWIRE.get());
        output.m_246326_((ItemLike)ModItems.MANTIS_BLADES.get());
        output.m_246326_((ItemLike)ModItems.KIROSHI_OPTIC.get());
        output.m_246326_((ItemLike)ModItems.SMART_PROJECTOR.get());
        output.m_246326_((ItemLike)ModItems.QUANTUM_SILENCER.get());
        output.m_246326_((ItemLike)ModItems.MAGNETIC_ACCELERATOR.get());
        output.m_246326_((ItemLike)ModItems.EM_CAPACITOR.get());
        output.m_246326_((ItemLike)ModItems.MIRMIDON_CHIP.get());
        output.m_246326_((ItemLike)ModItems.SKIPPY_AI.get());
        output.m_246326_((ItemLike)ModItems.KIROSHI_EYES.get());
        output.m_246326_((ItemLike)ModItems.SMART_LINK.get());
        output.m_246326_((ItemLike)ModItems.SECOND_HEART.get());
        output.m_246326_((ItemLike)ModItems.REINFORCED_TENDONS.get());
        output.m_246326_((ItemLike)ModItems.SAPSAN_STIMS.get());
        output.m_246326_((ItemLike)ModItems.OPTICAL_CAMO.get());
        output.m_246326_((ItemLike)ModItems.EUROCREDIT.get());
        output.m_246326_((ItemLike)ModItems.DATA_DRIVE.get());
        output.m_246326_((ItemLike)ModItems.ENERGY_CELL.get());
        output.m_246326_((ItemLike)ModItems.WEAPON_TERMINAL_ITEM.get());
        output.m_246326_((ItemLike)ModItems.DATA_SERVER_ITEM.get());
        output.m_246326_((ItemLike)ModItems.DRONE_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.ENFORCER_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.NETRUNNER_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.MAXTAC_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.CAPTAIN_CONTRACT.get());
        output.m_246326_((ItemLike)ModItems.UNIT_CONTRACT.get());
        output.m_246326_((ItemLike)ModItems.SPIDER_MECH_CONTRACT.get());
        output.m_246326_((ItemLike)ModItems.SNIPER_CORE.get());
        output.m_246326_((ItemLike)ModItems.BERSERK_PROTOCOL.get());
        output.m_246326_((ItemLike)ModItems.SQUAD_ANCHOR_ITEM.get());
        output.m_246326_((ItemLike)ModItems.CAPTAIN_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.SQUAD_UNIT_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.SPIDER_MECH_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.CYBER_SAMURAI_SPAWN_EGG.get());
        output.m_246326_((ItemLike)ModItems.ENERGY_GENERATOR_ITEM.get());
        output.m_246326_((ItemLike)ModItems.LASER_RELAY_ITEM.get());
        output.m_246326_((ItemLike)ModItems.EMP_JAMMER_ITEM.get());
        output.m_246326_((ItemLike)ModItems.CHARGE_PAD_ITEM.get());
        output.m_246326_((ItemLike)ModItems.COMPOSITE_BLOCK_ITEM.get());
        output.m_246326_((ItemLike)ModItems.TACTICAL_TABLET.get());
    }).m_257652_());
}

