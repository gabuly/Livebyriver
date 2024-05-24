package com.github.gabuly.livebyrivermod.event;
import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import com.github.gabuly.livebyrivermod.livebyrivermod;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = livebyrivermod.MOD_ID)
public class PanicEventHandler {

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        Entity source = event.getSource().getEntity();
        if (source != null) {
            checkForNearbyLeaderSheep(event.getEntity(), source);
        }
    }

    @SubscribeEvent
    public static void onEntityDied(LivingDeathEvent event) {
        Entity source = event.getSource().getEntity();
        if (source != null) {
            checkForNearbyLeaderSheep(event.getEntity(), source);
        }
    }

    private static void checkForNearbyLeaderSheep(Entity target, Entity source) {
        List<LeaderSheep> leaders = target.level().getEntitiesOfClass
                (LeaderSheep.class, target.getBoundingBox().inflate(30.0));
        for (LeaderSheep leader : leaders) {
            leader.setPanic(true, source);
        }
    }
}
