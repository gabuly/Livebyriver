package com.github.gabuly.livebyrivermod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.minecraft.core.BlockPos.getZ;
import static net.minecraft.world.entity.Entity.RemovalReason.KILLED;

@Mod("livebyrivermod")
@Mod.EventBusSubscriber(modid = "livebyrivermod")
public class livebyrivermod {

    public void Livebyrivernmod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof Sheep) {
                Sheep sheep = (Sheep) entity;
                sheep.goalSelector.addGoal(7, new RandomTryFindWaterGoal(sheep));
            } }}



