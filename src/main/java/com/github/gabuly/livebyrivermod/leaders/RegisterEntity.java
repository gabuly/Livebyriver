package com.github.gabuly.livebyrivermod.leaders;

import com.github.gabuly.livebyrivermod.livebyrivermod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisterEntity {
public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, livebyrivermod.MOD_ID);

//模型大小
    public static final RegistryObject<EntityType<LeaderSheep>> LEADERSHEEP =
            ENTITY_TYPES.register("leadersheep", () -> EntityType.Builder.of(LeaderSheep::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f).build("leadersheep"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
    }