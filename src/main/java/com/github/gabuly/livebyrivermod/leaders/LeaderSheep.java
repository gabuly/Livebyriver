package com.github.gabuly.livebyrivermod.leaders;


import com.github.gabuly.livebyrivermod.Goals.LeaderSheepPanicGoal;

import com.github.gabuly.livebyrivermod.Goals.SpawnFollowerOnceGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LeaderSheep extends Sheep {
    private final LeaderTracker tracker = new LeaderTracker();
    private boolean inPanic;
    private long panicEndTime;
    private LivingEntity panicSource;

    public LeaderSheep(EntityType<? extends Sheep> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        // EatBlockGoal eatBlockGoal = new EatBlockGoal(this);
       // this.goalSelector.getAvailableGoals().clear();
//        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeaderSheepPanicGoal(this));
        this.goalSelector.addGoal(1, new SpawnFollowerOnceGoal(this));
    }



    public static AttributeSupplier.Builder createAttributes() {
        return Sheep.createLivingAttributes().add(Attributes.MAX_HEALTH, 35D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
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
            if (inPanic && this.level().getGameTime() > panicEndTime) {
                inPanic = false;
                panicSource = null;
            }
        }
    }

    public void setPanic(boolean inPanic, Entity source) {
        this.inPanic = inPanic;
        this.panicEndTime = this.level().getGameTime() + 200; // 10 seconds at 20 ticks per second
        this.panicSource = (LivingEntity) source;
    }

    public boolean isInPanic() {
        return inPanic;
    }

    public LivingEntity getPanicSource() {
        return panicSource;
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
