package com.github.gabuly.livebyrivermod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("livebyrivermod")
@Mod.EventBusSubscriber(modid = "livebyrivermod")
public class livebyrivermod {

    public livebyrivermod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Setup code here (if needed)
    }


    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        // Check if the entity is an instance of any of the specified animal classes
        if (entity instanceof Sheep || entity instanceof Cow || entity instanceof Pig ||
                entity instanceof Chicken || entity instanceof Horse || entity instanceof Donkey ||
                entity instanceof Mule || entity instanceof Llama || entity instanceof Wolf ||
                entity instanceof Ocelot || entity instanceof Cat || entity instanceof Rabbit ||
                entity instanceof Fox || entity instanceof Panda) {
            // Cast the entity to its specific type (this is safe due to the instanceof check)
            PathfinderMob animal = (PathfinderMob) entity;

            // Add the RandomTryFindWaterGoal to the animal's goal selector
            animal.goalSelector.addGoal(7, new RandomTryFindWaterGoal(animal));
        }
    }

}
