package com.github.gabuly.livebyrivermod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
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
        if (entity instanceof Sheep) {
            Sheep sheep = (Sheep) entity;
            sheep.goalSelector.addGoal(1, new RandomTryFindWaterGoal(sheep));
        }
    }
}
