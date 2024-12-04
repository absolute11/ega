package org.laba.stop.impl;

import org.laba.stop.StopCondition;

import java.util.List;

public class FitnessEvaluationStopCondition implements StopCondition {
    private final int maxFitnessEvaluations;

    public FitnessEvaluationStopCondition(int maxFitnessEvaluations) {
        this.maxFitnessEvaluations = maxFitnessEvaluations;
    }

    @Override
    public boolean shouldStop(int currentGeneration, int fitnessEvaluations, List<String> population) {
        return fitnessEvaluations >= maxFitnessEvaluations;
    }
}