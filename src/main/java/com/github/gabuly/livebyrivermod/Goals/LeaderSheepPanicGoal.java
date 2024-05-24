package com.github.gabuly.livebyrivermod.Goals;

import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class LeaderSheepPanicGoal extends Goal {
    private final LeaderSheep leaderSheep;


    public LeaderSheepPanicGoal(LeaderSheep leaderSheep) {
        this.leaderSheep = leaderSheep;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.leaderSheep.isInPanic();
    }

    @Override
    public void start() {
        runAwayFromSource();
    }

    @Override
    public void tick() {
        if (this.leaderSheep.getNavigation().isDone()) {
            runAwayFromSource();
        }
    }

    private void runAwayFromSource() {
        LivingEntity panicSource = this.leaderSheep.getPanicSource();
        if (panicSource != null) {
            Vec3 sourcePos = panicSource.position();
            Vec3 oppositeDirection = DefaultRandomPos.getPosAway(this.leaderSheep, 16, 7, sourcePos);
            if (oppositeDirection != null) {
                this.leaderSheep.getNavigation().moveTo(oppositeDirection.x, oppositeDirection.y, oppositeDirection.z, 1.9);
            }
        }
    }
}
