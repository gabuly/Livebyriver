package com.github.gabuly.livebyrivermod.leaders;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class LeaderSheep extends Sheep {
    private final LeaderTracker tracker = new LeaderTracker();
    public LeaderSheep(EntityType<? extends Sheep> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Sheep.createLivingAttributes().add(Attributes.MAX_HEALTH, 35D)
                .add(Attributes.MOVEMENT_SPEED, 0.75D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 2f);
    }
    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive()) {
            BlockPos currentPos = this.blockPosition();
            tracker.updatePath(currentPos);
        }
    }

    public LeaderTracker getTracker() {
        return tracker;
    }

//    public LeaderSheep(Level level, double x, double y, double z){
//        this(RegisterEntity.LEADERSHEEP.get(),level);
//    }
//
//    public LeaderSheep(Level level, BlockPos position){
//        this(level,position,getX(),position.getY(), position.getZ());
//    }


    // Add any special behaviors or overrides for LeaderSheep here
}
