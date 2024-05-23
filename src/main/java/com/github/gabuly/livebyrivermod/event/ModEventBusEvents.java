package com.github.gabuly.livebyrivermod.event;
import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import com.github.gabuly.livebyrivermod.RegisterEntity;
import com.github.gabuly.livebyrivermod.livebyrivermod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = livebyrivermod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(RegisterEntity.LEADERSHEEP.get(),LeaderSheep.createAttributes().build());
    }



}
