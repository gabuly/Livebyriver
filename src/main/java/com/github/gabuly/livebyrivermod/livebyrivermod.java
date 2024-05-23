package com.github.gabuly.livebyrivermod;


import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import com.github.gabuly.livebyrivermod.leaders.RegisterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.github.gabuly.livebyrivermod.leaders.RegisterEntity.LEADERSHEEP;
import static com.mojang.text2speech.Narrator.LOGGER;

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