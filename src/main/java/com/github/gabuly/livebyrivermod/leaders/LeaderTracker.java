package com.github.gabuly.livebyrivermod.leaders;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.LinkedList;
import java.util.Random;

import static com.mojang.text2speech.Narrator.LOGGER;

public class LeaderTracker {
    private final LinkedList<BlockPos> pathHistory = new LinkedList<>();
    private static final int MAX_HISTORY_SIZE = 5;
    private final Random random = new Random();
    public void updatePath(BlockPos newPos) {

        // Check if the new position is already in the list
        if (pathHistory.contains(newPos)) {
            return; // Do not record the position if it's already in the list
        }

        // Ensure the list size does not exceed the maximum history size
        if (pathHistory.size() >= MAX_HISTORY_SIZE) {
            pathHistory.poll(); // Remove the oldest position
        }

        pathHistory.offer(newPos); // Add the new position
    }

    public LinkedList<BlockPos> getPathHistory() {
        return new LinkedList<>(pathHistory);
    }
}

