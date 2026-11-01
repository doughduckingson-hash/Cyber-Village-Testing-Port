/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.cybervillage.registry;

import com.cybervillage.entity.DroneEntity;
import com.cybervillage.entity.EnforcerEntity;
import com.cybervillage.entity.MaxTacEntity;
import com.cybervillage.entity.NetrunnerEntity;
import com.cybervillage.entity.SmartBulletEntity;
import com.cybervillage.squad.CaptainEntity;
import com.cybervillage.squad.SquadUnitEntity;
import com.cybervillage.vehicle.SpiderMechEntity;
import com.cybervillage.warfare.CyberSamuraiEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"cybervillage");
    public static final RegistryObject<EntityType<DroneEntity>> DRONE = ENTITIES.register("drone", () -> EntityType.Builder.m_20704_(DroneEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.4f, 0.8f).m_20719_().m_20712_("drone"));
    public static final RegistryObject<EntityType<EnforcerEntity>> ENFORCER = ENTITIES.register("enforcer", () -> EntityType.Builder.m_20704_(EnforcerEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20712_("enforcer"));
    public static final RegistryObject<EntityType<NetrunnerEntity>> NETRUNNER = ENTITIES.register("netrunner", () -> EntityType.Builder.m_20704_(NetrunnerEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20712_("netrunner"));
    public static final RegistryObject<EntityType<MaxTacEntity>> MAXTAC = ENTITIES.register("maxtac", () -> EntityType.Builder.m_20704_(MaxTacEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.7f, 2.0f).m_20719_().m_20712_("maxtac"));
    public static final RegistryObject<EntityType<CaptainEntity>> CAPTAIN = ENTITIES.register("captain", () -> EntityType.Builder.m_20704_(CaptainEntity::new, (MobCategory)MobCategory.CREATURE).m_20699_(0.6f, 1.95f).m_20712_("captain"));
    public static final RegistryObject<EntityType<SquadUnitEntity>> SQUAD_UNIT = ENTITIES.register("squad_unit", () -> EntityType.Builder.m_20704_(SquadUnitEntity::new, (MobCategory)MobCategory.CREATURE).m_20699_(0.6f, 1.95f).m_20712_("squad_unit"));
    public static final RegistryObject<EntityType<SpiderMechEntity>> SPIDER_MECH = ENTITIES.register("spider_mech", () -> EntityType.Builder.m_20704_(SpiderMechEntity::new, (MobCategory)MobCategory.CREATURE).m_20699_(1.4f, 0.9f).m_20719_().m_20712_("spider_mech"));
    public static final RegistryObject<EntityType<CyberSamuraiEntity>> CYBER_SAMURAI = ENTITIES.register("cyber_samurai", () -> EntityType.Builder.m_20704_(CyberSamuraiEntity::new, (MobCategory)MobCategory.MONSTER).m_20699_(0.7f, 2.1f).m_20719_().m_20712_("cyber_samurai"));
    public static final RegistryObject<EntityType<SmartBulletEntity>> SMART_BULLET = ENTITIES.register("smart_bullet", () -> EntityType.Builder.m_20704_(SmartBulletEntity::new, (MobCategory)MobCategory.MISC).m_20699_(0.3f, 0.3f).m_20702_(8).m_20717_(2).m_20712_("smart_bullet"));
}

