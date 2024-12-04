package org.laba.stop.impl;


import org.laba.stop.StopCondition;

import java.util.List;

public class GenerationStopCondition implements StopCondition {
    private final int maxGenerations;

    public GenerationStopCondition(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    @Override
    public boolean shouldStop(int currentGeneration, int fitnessEvaluations, List<String> population) {
        return currentGeneration >= maxGenerations;
    }
}