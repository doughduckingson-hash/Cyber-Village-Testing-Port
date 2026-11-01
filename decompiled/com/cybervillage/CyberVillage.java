/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  net.minecraft.client.gui.screens.MenuScreens
 *  net.minecraft.client.renderer.entity.SpiderRenderer
 *  net.minecraft.client.renderer.entity.VexRenderer
 *  net.minecraft.client.renderer.entity.ZombieRenderer
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 */
package com.cybervillage;

import com.cybervillage.client.MaxTacRenderer;
import com.cybervillage.client.SmartBulletRenderer;
import com.cybervillage.client.WeaponTerminalScreen;
import com.cybervillage.combat.CombatEvents;
import com.cybervillage.engram.EngramEvents;
import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.implant.ImplantEvents;
import com.cybervillage.network.NetworkHandler;
import com.cybervillage.registry.ModBlockEntities;
import com.cybervillage.registry.ModBlocks;
import com.cybervillage.registry.ModEffects;
import com.cybervillage.registry.ModEntities;
import com.cybervillage.registry.ModItems;
import com.cybervillage.registry.ModMenus;
import com.cybervillage.registry.ModTabs;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.threat.CyberCommands;
import com.cybervillage.threat.ThreatEvents;
import com.cybervillage.threat.ThreatManager;
import com.cybervillage.vehicle.SpiderMechEntity;
import com.cybervillage.warfare.CyberSamuraiEntity;
import com.cybervillage.warfare.WarfareEvents;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value="cybervillage")
public class CyberVillage {
    public static final String MODID = "cybervillage";

    public CyberVillage() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        ModEffects.EFFECTS.register(modBus);
        ModMenus.MENUS.register(modBus);
        ModTabs.TABS.register(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerAttributes);
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new EngramEvents());
        MinecraftForge.EVENT_BUS.register((Object)new ImplantEvents());
        MinecraftForge.EVENT_BUS.register((Object)new ThreatEvents());
        MinecraftForge.EVENT_BUS.register((Object)new WarfareEvents());
        MinecraftForge.EVENT_BUS.register((Object)new CombatEvents());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)ModEntities.DRONE.get(), DroneEntity.m_34040_().m_22265_());
        event.put((EntityType)ModEntities.ENFORCER.get(), EnforcerEntity.m_34328_().m_22265_());
        event.put((EntityType)ModEntities.NETRUNNER.get(), NetrunnerEntity.m_34328_().m_22265_());
        event.put((EntityType)ModEntities.MAXTAC.get(), MaxTacEntity.m_34328_().m_22265_());
        event.put((EntityType)ModEntities.CAPTAIN.get(), CaptainEntity.m_34328_().m_22265_());
        event.put((EntityType)ModEntities.SQUAD_UNIT.get(), SquadUnitEntity.m_34328_().m_22265_());
        event.put((EntityType)ModEntities.SPIDER_MECH.get(), SpiderMechEntity.m_33815_().m_22265_());
        event.put((EntityType)ModEntities.CYBER_SAMURAI.get(), CyberSamuraiEntity.m_34328_().m_22265_());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CyberCommands.register((CommandDispatcher<CommandSourceStack>)event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ThreatManager.tick(event.getServer());
        }
    }

    @Mod.EventBusSubscriber(modid="cybervillage", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> MenuScreens.m_96206_((MenuType)((MenuType)ModMenus.WEAPON_TERMINAL.get()), WeaponTerminalScreen::new));
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer((EntityType)ModEntities.DRONE.get(), VexRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.SMART_BULLET.get(), SmartBulletRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.ENFORCER.get(), ZombieRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.NETRUNNER.get(), ZombieRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.MAXTAC.get(), MaxTacRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.CAPTAIN.get(), ZombieRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.SQUAD_UNIT.get(), ZombieRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.SPIDER_MECH.get(), SpiderRenderer::new);
            event.registerEntityRenderer((EntityType)ModEntities.CYBER_SAMURAI.get(), ZombieRenderer::new);
        }
    }
}

