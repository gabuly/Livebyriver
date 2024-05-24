package com.github.gabuly.livebyrivermod.Goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.mojang.text2speech.Narrator.LOGGER;

public class SpawnFollowerOnceGoal extends Goal {
    private final PathfinderMob mob;
    private boolean executed;

    public SpawnFollowerOnceGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.executed = false;
    }

    @Override
    public boolean canUse() {
        // Only use this goal if it hasn't been executed yet
        return !executed;
    }

    @Override
    public void start() {
        Level level = mob.level();
        if (!level.isClientSide) {
            for (int i = 0; i < 11; i++) {
            Vec3 randomPos = DefaultRandomPos.getPos(mob,5,5)  ;
            Sheep sheep = EntityType.SHEEP.create(level);

              if(sheep!=null&&randomPos!=null){
                sheep.setPos(randomPos.x, randomPos.y, randomPos.z);
                level.addFreshEntity(sheep);
              }
            }
            LOGGER.info(""+this.executed);
            this.executed = true;
        }
        // Mark this goal as executed

    }

    @Override
    public boolean canContinueToUse() {
        // This goal does not continue once it has started
        return false;
    }
}
