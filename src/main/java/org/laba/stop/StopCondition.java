package org.laba.stop;

import java.util.List;

public interface StopCondition {
    boolean shouldStop(int currentGeneration, int fitnessEvaluations, List<String> population);
}