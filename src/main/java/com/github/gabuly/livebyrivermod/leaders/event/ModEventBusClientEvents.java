//package com.github.gabuly.livebyrivermod.leaders.event;
//
//import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
//import com.github.gabuly.livebyrivermod.livebyrivermod;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraftforge.client.event.EntityRenderersEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//
//@Mod.EventBusSubscriber(modid = livebyrivermod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class ModEntities {
//
//    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, livebyrivermod.MOD_ID);
//
//    public static final RegistryObject<EntityType<LeaderSheep>> LEADERSHEEP = ENTITIES.register("leader_sheep",
//            () -> EntityType.Builder.of(LeaderSheep::new, MobCategory.CREATURE)
//                    .sized(0.9F, 1.3F) // Same size as vanilla sheep
//                    .build("leader_sheep"));
//
//    @SubscribeEvent
//    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
//        ENTITIES.register(event.getRegistry());
//    }
//}