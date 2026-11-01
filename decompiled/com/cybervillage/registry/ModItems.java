/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.common.ForgeSpawnEggItem
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.cybervillage.registry;

import com.cybervillage.implant.ImplantItem;
import com.cybervillage.registry.ModBlocks;
import com.cybervillage.registry.ModEntities;
import com.cybervillage.squad.ChipItem;
import com.cybervillage.squad.ContractItem;
import com.cybervillage.squad.TacticalTabletItem;
import com.cybervillage.weapon.AttachmentItem;
import com.cybervillage.weapon.AttachmentType;
import com.cybervillage.weapon.KatanaItem;
import com.cybervillage.weapon.MantisBladesItem;
import com.cybervillage.weapon.MonowireItem;
import com.cybervillage.weapon.SmartGunItem;
import com.cybervillage.weapon.TechGunItem;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ITEMS, (String)"cybervillage");
    public static final RegistryObject<Item> YUKIMURA = ITEMS.register("yukimura", () -> new SmartGunItem(new Item.Properties(), 3.0f, 2, 40, 1));
    public static final RegistryObject<Item> PALISADE = ITEMS.register("palisade", () -> new SmartGunItem(new Item.Properties(), 1.0f, 12, 8, 12));
    public static final RegistryObject<Item> ASHURA = ITEMS.register("ashura", () -> new TechGunItem(new Item.Properties(), 16.0f, 36.0f, 30, 4, 1, 1));
    public static final RegistryObject<Item> OVERLORD = ITEMS.register("overlord", () -> new TechGunItem(new Item.Properties(), 8.0f, 14.0f, 20, 1, 3, 6));
    public static final RegistryObject<Item> RAIDEN_KATANA = ITEMS.register("raiden_katana", () -> new KatanaItem((Tier)Tiers.NETHERITE, 4, -2.0f, new Item.Properties()));
    public static final RegistryObject<Item> MONOWIRE = ITEMS.register("monowire", () -> new MonowireItem(new Item.Properties()));
    public static final RegistryObject<Item> MANTIS_BLADES = ITEMS.register("mantis_blades", () -> new MantisBladesItem((Tier)Tiers.DIAMOND, 5, -1.6f, new Item.Properties()));
    public static final RegistryObject<Item> KIROSHI_OPTIC = ITEMS.register("kiroshi_optic", () -> new AttachmentItem(new Item.Properties(), AttachmentType.SIGHT, AttachmentItem.Effect.KIROSHI_OPTIC));
    public static final RegistryObject<Item> SMART_PROJECTOR = ITEMS.register("smart_projector", () -> new AttachmentItem(new Item.Properties(), AttachmentType.SIGHT, AttachmentItem.Effect.SMART_PROJECTOR));
    public static final RegistryObject<Item> QUANTUM_SILENCER = ITEMS.register("quantum_silencer", () -> new AttachmentItem(new Item.Properties(), AttachmentType.BARREL, AttachmentItem.Effect.QUANTUM_SILENCER));
    public static final RegistryObject<Item> MAGNETIC_ACCELERATOR = ITEMS.register("magnetic_accelerator", () -> new AttachmentItem(new Item.Properties(), AttachmentType.BARREL, AttachmentItem.Effect.MAGNETIC_ACCELERATOR));
    public static final RegistryObject<Item> EM_CAPACITOR = ITEMS.register("em_capacitor", () -> new AttachmentItem(new Item.Properties(), AttachmentType.RECEIVER, AttachmentItem.Effect.EM_CAPACITOR));
    public static final RegistryObject<Item> MIRMIDON_CHIP = ITEMS.register("mirmidon_chip", () -> new AttachmentItem(new Item.Properties(), AttachmentType.RECEIVER, AttachmentItem.Effect.MIRMIDON_CHIP));
    public static final RegistryObject<Item> SKIPPY_AI = ITEMS.register("skippy_ai", () -> new AttachmentItem(new Item.Properties(), AttachmentType.FIRMWARE, AttachmentItem.Effect.SKIPPY_AI));
    public static final RegistryObject<Item> KIROSHI_EYES = ITEMS.register("kiroshi_eyes", () -> new ImplantItem(new Item.Properties(), "kiroshi_eyes", 10));
    public static final RegistryObject<Item> SMART_LINK = ITEMS.register("smart_link", () -> new ImplantItem(new Item.Properties(), "smart_link", 10));
    public static final RegistryObject<Item> SECOND_HEART = ITEMS.register("second_heart", () -> new ImplantItem(new Item.Properties(), "second_heart", 30));
    public static final RegistryObject<Item> REINFORCED_TENDONS = ITEMS.register("reinforced_tendons", () -> new ImplantItem(new Item.Properties(), "reinforced_tendons", 15));
    public static final RegistryObject<Item> SAPSAN_STIMS = ITEMS.register("sapsan_stims", () -> new ImplantItem(new Item.Properties(), "sapsan_stims", 20));
    public static final RegistryObject<Item> OPTICAL_CAMO = ITEMS.register("optical_camo", () -> new ImplantItem(new Item.Properties(), "optical_camo", 15));
    public static final RegistryObject<Item> EUROCREDIT = ITEMS.register("eurocredit", () -> new Item(new Item.Properties().m_41487_(64)));
    public static final RegistryObject<Item> DATA_DRIVE = ITEMS.register("data_drive", () -> new Item(new Item.Properties().m_41487_(64)));
    public static final RegistryObject<Item> ENERGY_CELL = ITEMS.register("energy_cell", () -> new Item(new Item.Properties().m_41487_(64)));
    public static final RegistryObject<Item> CAPTAIN_CONTRACT = ITEMS.register("captain_contract", () -> new ContractItem(new Item.Properties(), (Supplier<? extends EntityType<? extends Mob>>)ModEntities.CAPTAIN));
    public static final RegistryObject<Item> UNIT_CONTRACT = ITEMS.register("unit_contract", () -> new ContractItem(new Item.Properties(), (Supplier<? extends EntityType<? extends Mob>>)ModEntities.SQUAD_UNIT));
    public static final RegistryObject<Item> SPIDER_MECH_CONTRACT = ITEMS.register("spider_mech_contract", () -> new ContractItem(new Item.Properties(), (Supplier<? extends EntityType<? extends Mob>>)ModEntities.SPIDER_MECH));
    public static final RegistryObject<Item> SNIPER_CORE = ITEMS.register("sniper_core", () -> new ChipItem(new Item.Properties(), "sniper_core"));
    public static final RegistryObject<Item> BERSERK_PROTOCOL = ITEMS.register("berserk_protocol", () -> new ChipItem(new Item.Properties(), "berserk_protocol"));
    public static final RegistryObject<Item> WEAPON_TERMINAL_ITEM = ITEMS.register("weapon_terminal", () -> new BlockItem((Block)ModBlocks.WEAPON_TERMINAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> DATA_SERVER_ITEM = ITEMS.register("data_server", () -> new BlockItem((Block)ModBlocks.DATA_SERVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> SQUAD_ANCHOR_ITEM = ITEMS.register("squad_anchor", () -> new BlockItem((Block)ModBlocks.SQUAD_ANCHOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_GENERATOR_ITEM = ITEMS.register("energy_generator", () -> new BlockItem((Block)ModBlocks.ENERGY_GENERATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LASER_RELAY_ITEM = ITEMS.register("laser_relay", () -> new BlockItem((Block)ModBlocks.LASER_RELAY.get(), new Item.Properties()));
    public static final RegistryObject<Item> EMP_JAMMER_ITEM = ITEMS.register("emp_jammer", () -> new BlockItem((Block)ModBlocks.EMP_JAMMER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHARGE_PAD_ITEM = ITEMS.register("charge_pad", () -> new BlockItem((Block)ModBlocks.CHARGE_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> COMPOSITE_BLOCK_ITEM = ITEMS.register("composite_block", () -> new BlockItem((Block)ModBlocks.COMPOSITE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> TACTICAL_TABLET = ITEMS.register("tactical_tablet", () -> new TacticalTabletItem(new Item.Properties().m_41487_(1)));
    public static final RegistryObject<Item> DRONE_SPAWN_EGG = ITEMS.register("drone_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DRONE, 3203327, 659504, new Item.Properties()));
    public static final RegistryObject<Item> ENFORCER_SPAWN_EGG = ITEMS.register("enforcer_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENFORCER, 3159370, 13087610, new Item.Properties()));
    public static final RegistryObject<Item> NETRUNNER_SPAWN_EGG = ITEMS.register("netrunner_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.NETRUNNER, 1053720, 14700543, new Item.Properties()));
    public static final RegistryObject<Item> MAXTAC_SPAWN_EGG = ITEMS.register("maxtac_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MAXTAC, 0x15151A, 16719936, new Item.Properties()));
    public static final RegistryObject<Item> CAPTAIN_SPAWN_EGG = ITEMS.register("captain_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.CAPTAIN, 1714752, 3203327, new Item.Properties()));
    public static final RegistryObject<Item> SQUAD_UNIT_SPAWN_EGG = ITEMS.register("squad_unit_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.SQUAD_UNIT, 2372682, 7405504, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_MECH_SPAWN_EGG = ITEMS.register("spider_mech_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.SPIDER_MECH, 0x22252B, 16756784, new Item.Properties()));
    public static final RegistryObject<Item> CYBER_SAMURAI_SPAWN_EGG = ITEMS.register("cyber_samurai_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.CYBER_SAMURAI, 0x101014, 16724048, new Item.Properties()));
}

