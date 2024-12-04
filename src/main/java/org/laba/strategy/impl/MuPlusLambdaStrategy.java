package org.laba.strategy.impl;

import org.laba.strategy.NextGenerationStrategy;

import java.util.ArrayList;
import java.util.List;

public class MuPlusLambdaStrategy implements NextGenerationStrategy {

    @Override
    public List<String> formNextGeneration(List<String> population, List<String> offspring) {
        List<String> nextGeneration = new ArrayList<>();
        nextGeneration.addAll(population);
        nextGeneration.addAll(offspring);

        return nextGeneration;
    }
}