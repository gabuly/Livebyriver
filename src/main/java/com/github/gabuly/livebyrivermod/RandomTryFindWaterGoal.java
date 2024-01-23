package com.github.gabuly.livebyrivermod;
import com.mojang.text2speech.Narrator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import javax.annotation.Nullable;
import static com.mojang.text2speech.Narrator.LOGGER;

public class RandomTryFindWaterGoal extends Goal {
    private static long COOLDOWN= 60000;
    private final PathfinderMob mob;
    private final long delayfinished = 25;
    private int initialTicksCounter = 0;
    private static int durationCount;
    private static final int forcestop = 160;
    private BlockPos newWaterPos;
    private BlockPos goToWaterPos=null;
    private long lastTimeReachedWater=Long.MAX_VALUE;
    public RandomTryFindWaterGoal(PathfinderMob p_25964_) {
    //    this.setFlags(EnumSet.of(Flag.MOVE));
        this.mob = p_25964_;
    }

    public boolean canUse() {
       if (initialTicksCounter<=delayfinished){ //stop counting once over first time called
        initialTicksCounter++;
       }
       if (((System.currentTimeMillis()-lastTimeReachedWater)>COOLDOWN)|| initialTicksCounter== delayfinished){ // call when cooldown over || first action delay triggered
           lastTimeReachedWater=System.currentTimeMillis();//co
           return true;
       }
        //LOGGER.info("FALSE USE");
        return false;
    }


    public void start() {
        if(goToWaterPos!=null&&this.mob.blockPosition().closerThan(goToWaterPos, 32)){ //if waterlocation has recorded and not far
           // LOGGER.info("go Home==============="+newWaterPos);
            goToWaterPos = LookForNearWater(this.mob.blockPosition(), newWaterPos);
            this.mob.getNavigation().moveTo(goToWaterPos.getX(), goToWaterPos.getY(), goToWaterPos.getZ(), 1.1);
        }
        else {
            newWaterPos = this.lookForWater(this.mob.level(), this.mob, 20);//if no water location yet === seek for water position
            if(newWaterPos!=null) {  //if found, record and go
                goToWaterPos = LookForNearWater(this.mob.blockPosition(), newWaterPos);
               // LOGGER.info("new home==============  " + goToWaterPos);
                this.mob.getNavigation().moveTo(goToWaterPos.getX(), goToWaterPos.getY(), goToWaterPos.getZ(), 1.1);
            } else {stop();}
        }
    }


    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    public void tick() {

    }

    public void stop () {
   // LOGGER.info("Stopping navigation towards water");
    }

    @Nullable
    protected BlockPos lookForWater(BlockGetter blockGetter, Entity entity, int searchRange) {
        BlockPos entityBlockPos = entity.blockPosition();
      //  LOGGER.info("lookForWater() - Searching for water around ");
        return !blockGetter.getBlockState(entityBlockPos).getCollisionShape(blockGetter, entityBlockPos).isEmpty() ? null : BlockPos.findClosestMatch(entityBlockPos, searchRange, 9, (potentialPos) -> {
            return blockGetter.getFluidState(potentialPos).is(FluidTags.WATER);
        }).orElse(null);
    }

    protected BlockPos LookForNearWater ( BlockPos entityPos,BlockPos waterPos){  //transfer waterlocation to near water location
        Vec3i vectorBA = entityPos.subtract(waterPos);
        double length = Math.sqrt(vectorBA.getX() * vectorBA.getX() + vectorBA.getY() * vectorBA.getY() + vectorBA.getZ() * vectorBA.getZ());
        // Normalizing the vector
        int normalizedX = (int)Math.round(vectorBA.getX() / length);
        int normalizedY = (int)Math.round(vectorBA.getY() / length);
        int normalizedZ = (int)Math.round(vectorBA.getZ() / length);

        // Adding the normalized vector to B
        return waterPos.offset(normalizedX, normalizedY, normalizedZ);
    }


}


