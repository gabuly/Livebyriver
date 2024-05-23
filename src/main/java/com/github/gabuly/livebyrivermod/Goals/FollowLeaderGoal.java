package com.github.gabuly.livebyrivermod.Goals;
import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.mojang.text2speech.Narrator.LOGGER;

public class FollowLeaderGoal extends Goal {
    private final PathfinderMob mob;
    private LeaderSheep leaderSheep;
    private int timer;
    private final Random random = new Random();
    public FollowLeaderGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.timer = this.random.nextInt(100) + 50; // Initialize with a random delay between 50 and 100 ticks
    }

    @Override
    public boolean canUse() {
        if (this.leaderSheep ==null) {//没有已记住的leader的话 尝试寻找一个， 如果找到， true
            this.leaderSheep = findLeaderSheep();
            return this.leaderSheep != null;
        }
        //已经有leader了的话true
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isAlive() && this.leaderSheep != null && this.leaderSheep.isAlive();
    }

    @Override
    public void start() {
        this.timer = this.random.nextInt(50) + 50;
    }

    @Override
    public void tick() {
        if (--this.timer <= 0) {
                this.timer = this.random.nextInt(50) + 50; // Reset the timer with a new random delay
                if (this.leaderSheep != null) {
                    LinkedList<BlockPos> pathHistory = this.leaderSheep.getTracker().getPathHistory();

                    if (pathHistory.size() > 3) { // Ensure there are at least 5 positions
                        List<BlockPos> selectablePositions = pathHistory.subList(0, pathHistory.size() - 1);
                        LOGGER.info("Selectable positions size: " + selectablePositions.size());

                        BlockPos targetPos = selectablePositions.get(random.nextInt(selectablePositions.size()));
                        LOGGER.info("Selected target position: " + targetPos);

                        int X = random.nextInt(5) - 2;
                        int Z = random.nextInt(5) - 2;

                        this.mob.getNavigation().moveTo(targetPos.getX()+X, targetPos.getY(), targetPos.getZ()+Z, 1.2);
                        LOGGER.info("MOVVVVVVVVVVVVVVVVVVVVV to: " + targetPos);
                    }
                }
            }
        }


    private LeaderSheep findLeaderSheep() {
        List<LeaderSheep> leaders = this.mob.level().getEntitiesOfClass(LeaderSheep.class, this.mob.getBoundingBox().inflate(10.0));
        if (!leaders.isEmpty()) {
            return leaders.get(0); // Simplification: Just get the first one found
        }
        return null;
    }
}
