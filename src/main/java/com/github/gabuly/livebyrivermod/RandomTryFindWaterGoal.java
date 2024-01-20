package com.github.gabuly.livebyrivermod;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import static com.mojang.text2speech.Narrator.LOGGER;


public class RandomTryFindWaterGoal extends Goal {
    private static long COOLDOWN= 120 * 1000;
    private final PathfinderMob mob;
    private boolean firstCheck = true;
    private final int proximityThreshold = 4;
    private boolean waterflag=false;
    private BlockPos movePos;
    private long lastTimeReachedWater = Long.MIN_VALUE;
    public RandomTryFindWaterGoal(PathfinderMob p_25964_) {
        this.mob = p_25964_;
        movePos=this.mob.getOnPos(); //initialize movePos to ifs *(can improve)
        LOGGER.info("first time");
    }

    public boolean canUse() {
     //Forced first time run; no run within cooldown
       if (firstCheck ||(System.currentTimeMillis()-lastTimeReachedWater)>COOLDOWN) {
            //LOGGER.info("first time");
            firstCheck = false;
            return true;
       }
       return false;
    }

    public boolean canContinueToUse() {
        //tick() continuing running untill close to water pos
        return movePos != null && !this.mob.blockPosition().closerThan(movePos, proximityThreshold);
    }

    public void start() {
        //LOGGER.info("Start!!");
        BlockPos entityPos = mob.blockPosition();
        lastTimeReachedWater = System.currentTimeMillis();//cooldown set

        //no search action if the previous searched blocks has water
        if(!this.mob.level().getFluidState(movePos).is(FluidTags.WATER)){
            waterflag=false;
            COOLDOWN=120 * 1000;
            LOGGER.info("search");
        double nearestDistanceSq = Double.MAX_VALUE; // Start with the highest distance possible
            int chunkRange = 16;
            for (int dx = -chunkRange; dx <= chunkRange; dx++) {
            for (int dy = -5; dy <= 3; dy++) { // Assuming you want to search 4 blocks up and down
                for (int dz = -chunkRange; dz <= chunkRange; dz++) {
                    BlockPos searchPos = entityPos.offset(dx, dy, dz);
                    if (this.mob.level().getFluidState(searchPos).is(FluidTags.WATER)) {
                        double distanceSq = entityPos.distSqr(searchPos);
                        if (distanceSq < nearestDistanceSq) {
                            nearestDistanceSq = distanceSq;
                            movePos = searchPos;
                            waterflag=true;
                            COOLDOWN=20 * 1000;
                            //record water position
                            LOGGER.info("got POS!!");
                        }
                    }
                }
            }
        }
        }
    }


    public void tick() {
        //run if block is water
       if (waterflag){
           LOGGER.info("moving!!");
           this.mob.getNavigation().moveTo(movePos.getX(), movePos.getY(), movePos.getZ(), 1.0);
            if(this.mob.blockPosition().closerThan(movePos, proximityThreshold)) {
               LOGGER.info("Stopping ");
               stop();
           }

        }
    }
//}

    public void stop () {
    LOGGER.info("Stopping navigation towards water");
    this.mob.getNavigation().stop();
    //record time reached
    }  }

