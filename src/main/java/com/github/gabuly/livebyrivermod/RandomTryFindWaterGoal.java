package com.github.gabuly.livebyrivermod;
import com.mojang.text2speech.Narrator;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;


import javax.annotation.Nullable;

import java.util.EnumSet;

import static com.mojang.text2speech.Narrator.LOGGER;


public class RandomTryFindWaterGoal extends Goal {
    private static long COOLDOWN= 10000;
    private final PathfinderMob mob;
    private final long delayfinished = 21;
    private int initialTicksCounter = 0;
    private static final int INITIAL_TICKS_THRESHOLD = 6;
    private BlockPos movePos;
    private long lastTimeReachedWater=Long.MAX_VALUE;
    public RandomTryFindWaterGoal(PathfinderMob p_25964_) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.mob = p_25964_;
    }

    public boolean canUse() {
        initialTicksCounter++;
       if ((initialTicksCounter == delayfinished ||(System.currentTimeMillis()-lastTimeReachedWater)>COOLDOWN)){
           LOGGER.info("== "+initialTicksCounter);
            LOGGER.info("first attempt");
           movePos = this.lookForWater(this.mob.level(), this.mob, 20);
           if (movePos != null) {
               LOGGER.info("Found!!!  "+movePos);
               //this.posX = movePos.getX();
              // this.posY = movePos.getY();
             //  this.posZ = movePos.getZ();
               return true;
           }
           LOGGER.info("not Found");
           return false;
       }
        //LOGGER.info("FALSE USE");
        return false;
    }


    public void start() {
        LOGGER.info("Start with "+ movePos);
       lastTimeReachedWater = System.currentTimeMillis();//cooldown set
       //movePos = this.lookForWater(this.mob.level(), this.mob, 16);
        //this.mob.setSecondsOnFire(2);
        this.mob.getNavigation().moveTo(movePos.getX(), movePos.getY(), movePos.getZ(), 2.0);

    }

    public boolean canContinueToUse() {
        //tick() continuing running untill close to water pos
        //int proximityThreshold = 2;
        //return true;
        return !this.mob.blockPosition().closerThan(movePos, 3);
        //return !this.mob.getNavigation().isDone();
        // return(!this.mob.blockPosition().closerThan(movePos, 2)||!this.mob.getNavigation().isDone());
    }


    public void tick() {
      //  this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, 2);
    }


    public void stop () {
    LOGGER.info("Stopping navigation towards water");
    this.mob.getNavigation().stop();
    //record time reached
    }


    @Nullable
    protected BlockPos lookForWater(BlockGetter blockGetter, Entity entity, int searchRange) {
        BlockPos entityBlockPos = entity.blockPosition();
        LOGGER.info("lookForWater() - Searching for water around ");
        return !blockGetter.getBlockState(entityBlockPos).getCollisionShape(blockGetter, entityBlockPos).isEmpty() ? null : BlockPos.findClosestMatch(entityBlockPos, searchRange, 7, (potentialPos) -> {
            return blockGetter.getFluidState(potentialPos).is(FluidTags.WATER);
        }).orElse(null);
    }
}


