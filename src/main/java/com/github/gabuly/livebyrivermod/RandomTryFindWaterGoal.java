package com.github.gabuly.livebyrivermod;
import com.mojang.text2speech.Narrator;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import javax.annotation.Nullable;
import static com.mojang.text2speech.Narrator.LOGGER;

public class RandomTryFindWaterGoal extends Goal {
    private static long COOLDOWN= 25000;
    private final PathfinderMob mob;
    private final long delayfinished = 25;
    private int initialTicksCounter = 0;
    private static int durationCount;
    private static final int forcestop = 140;
    private BlockPos movePos;
    private long lastTimeReachedWater=Long.MAX_VALUE;
    public RandomTryFindWaterGoal(PathfinderMob p_25964_) {
    //    this.setFlags(EnumSet.of(Flag.MOVE));
        this.mob = p_25964_;
    }

    public boolean canUse() {
        //stop counting once over first time called
       if (initialTicksCounter<=delayfinished){
        initialTicksCounter++;
       }
       // call when cooldown over || first action delay triggered
       if (((System.currentTimeMillis()-lastTimeReachedWater)>COOLDOWN)||initialTicksCounter == delayfinished){
          // LOGGER.info("== "+initialTicksCounter);
           movePos = this.lookForWater(this.mob.level(), this.mob, 20); // try find a water position
           lastTimeReachedWater=System.currentTimeMillis();//cooldown
              // LOGGER.info("Found!!!  "+movePos);
               // call only when water is found and entity is far away from water
               return  (movePos != null&&!this.mob.blockPosition().closerThan(movePos, 3));
       }
        //LOGGER.info("FALSE USE");
        return false;
    }


    public void start() {
        durationCount=0;//count for move to water duration
       // LOGGER.info("Start with "+ movePos);
        this.mob.getNavigation().moveTo(movePos.getX(), movePos.getY(), movePos.getZ(), 1.1);
    }

    public boolean canContinueToUse() {
        durationCount++;//stop when close to water or exceeds 7 seconds
        return (!this.mob.blockPosition().closerThan(movePos, 3)||durationCount>forcestop);
    }

    public void tick() {
      //  this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, 2);
    }

    public void stop () {
   // LOGGER.info("Stopping navigation towards water");
    this.mob.getNavigation().stop();
    }

    @Nullable
    protected BlockPos lookForWater(BlockGetter blockGetter, Entity entity, int searchRange) {
        BlockPos entityBlockPos = entity.blockPosition();
        LOGGER.info("lookForWater() - Searching for water around ");
        return !blockGetter.getBlockState(entityBlockPos).getCollisionShape(blockGetter, entityBlockPos).isEmpty() ? null : BlockPos.findClosestMatch(entityBlockPos, searchRange, 9, (potentialPos) -> {
            return blockGetter.getFluidState(potentialPos).is(FluidTags.WATER);
        }).orElse(null);
    }
}


