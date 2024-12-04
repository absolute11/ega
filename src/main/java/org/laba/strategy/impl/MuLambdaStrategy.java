package org.laba.strategy.impl;

import org.laba.strategy.NextGenerationStrategy;

import java.util.List;

public class MuLambdaStrategy implements NextGenerationStrategy {

    @Override
    public List<String> formNextGeneration(List<String> population, List<String> offspring) {

        return offspring;
    }
}