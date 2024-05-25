package com.github.gabuly.livebyrivermod.Goals;

import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class LeaderSheepAlertGoal extends Goal {
    private final LeaderSheep leaderSheep;
    private Player closestPlayer;
    private final float maxDetectionDistance;
    private final float panicDistance;
    private int lookTime;

    public LeaderSheepAlertGoal(LeaderSheep leaderSheep, float maxDetectionDistance, float panicDistance) {
        this.leaderSheep = leaderSheep;
        this.maxDetectionDistance = maxDetectionDistance;
        this.panicDistance = panicDistance;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }


    @Override
    public boolean canUse() {
        List<Player> players = this.leaderSheep.level().getEntitiesOfClass(Player.class, this.leaderSheep.getBoundingBox().inflate(this.maxDetectionDistance, 3.0D, this.maxDetectionDistance));
        if (!players.isEmpty()) {
            this.closestPlayer = players.get(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.closestPlayer != null && this.closestPlayer.isAlive() && this.leaderSheep.distanceToSqr(this.closestPlayer) <= this.maxDetectionDistance * this.maxDetectionDistance;
    }

    @Override
    public void start() {
        // Initial state
        this.closestPlayer = null;
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
    }


    @Override
    public void tick() {
        List<Player> players = this.leaderSheep.level().getEntitiesOfClass(Player.class, this.leaderSheep.getBoundingBox().inflate(this.maxDetectionDistance, 3.0D, this.maxDetectionDistance));
        double closestDistance = Double.MAX_VALUE;
        Player closestPlayer = null;

        for (Player player : players) {
            double distance = this.leaderSheep.distanceToSqr(player);
            if (distance < closestDistance && this.leaderSheep.hasLineOfSight(player)) {
                closestPlayer = player;
                closestDistance = distance;
            }
        }

        if (closestPlayer != null) {
            this.closestPlayer = closestPlayer;
            if (closestDistance <= this.panicDistance * this.panicDistance) {
                this.leaderSheep.setPanic(true, closestPlayer);
            } else {
                this.leaderSheep.getLookControl().setLookAt(this.closestPlayer.getX(), this.closestPlayer.getEyeY(), this.closestPlayer.getZ());
            }
        }
    }
}