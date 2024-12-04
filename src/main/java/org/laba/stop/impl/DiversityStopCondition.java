package org.laba.stop.impl;

import org.laba.stop.StopCondition;

import java.util.HashSet;
import java.util.List;

public class DiversityStopCondition implements StopCondition {
    private final double minDiversity;

    public DiversityStopCondition(double minDiversity) {
        this.minDiversity = minDiversity;
    }

    @Override
    public boolean shouldStop(int currentGeneration, int fitnessEvaluations, List<String> population) {
        HashSet<String> uniqueChromosomes = new HashSet<>(population);
        double diversity = (double) uniqueChromosomes.size() / population.size();
        return diversity <= minDiversity;
    }
}