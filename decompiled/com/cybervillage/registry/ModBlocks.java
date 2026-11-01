/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.cybervillage.registry;

import com.cybervillage.block.AnchorBlock;
import com.cybervillage.block.DataServerBlock;
import com.cybervillage.block.WeaponTerminalBlock;
import com.cybervillage.energy.ChargePadBlock;
import com.cybervillage.energy.GridBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.BLOCKS, (String)"cybervillage");
    public static final RegistryObject<Block> WEAPON_TERMINAL = BLOCKS.register("weapon_terminal", () -> new WeaponTerminalBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50625_)));
    public static final RegistryObject<Block> DATA_SERVER = BLOCKS.register("data_server", () -> new DataServerBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_).m_60953_(state -> 7)));
    public static final RegistryObject<Block> SQUAD_ANCHOR = BLOCKS.register("squad_anchor", () -> new AnchorBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50729_).m_60953_(state -> 5)));
    public static final RegistryObject<Block> ENERGY_GENERATOR = BLOCKS.register("energy_generator", () -> new GridBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_).m_60953_(state -> 10), GridBlock.Role.GENERATOR));
    public static final RegistryObject<Block> LASER_RELAY = BLOCKS.register("laser_relay", () -> new GridBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_).m_60953_(state -> 6), GridBlock.Role.RELAY));
    public static final RegistryObject<Block> EMP_JAMMER = BLOCKS.register("emp_jammer", () -> new GridBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_).m_60953_(state -> 4), GridBlock.Role.JAMMER));
    public static final RegistryObject<Block> CHARGE_PAD = BLOCKS.register("charge_pad", () -> new ChargePadBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50075_).m_60953_(state -> 8)));
    public static final RegistryObject<Block> COMPOSITE_BLOCK = BLOCKS.register("composite_block", () -> new Block(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50080_).m_60913_(25.0f, 1200.0f)));
}

