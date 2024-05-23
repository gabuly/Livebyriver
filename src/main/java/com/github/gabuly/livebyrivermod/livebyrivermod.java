package com.github.gabuly.livebyrivermod;


import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Random;

import static com.github.gabuly.livebyrivermod.RegisterEntity.LEADERSHEEP;

@Mod(livebyrivermod.MOD_ID)
public class livebyrivermod {
    public static final String MOD_ID = "livebyriver";
    private static final String LEADER_TAG = "leader_entity";
    private static MinecraftServer server;
    private static final Random RANDOM = new Random();
    private static ServerLevel overworld;
    public livebyrivermod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RegisterEntity.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
      //  FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {

                EntityRenderers.register(LEADERSHEEP.get(), SheepRenderer::new);
            });
        }
    }






}