package org.laba.selection.impl;

import lombok.RequiredArgsConstructor;
import org.laba.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RouletteParentSelector implements ParentSelector {
    private final Random random;
    @Override
    public List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs) {
        List<int[]> parentPairs = new ArrayList<>();
        float totalFitness = fitnessScores.stream().reduce(0f, Float::sum);

        for (int i = 0; i < numPairs; i++) {
            int parent1 = selectOne(fitnessScores, totalFitness);
            int parent2;
            do {
                parent2 = selectOne(fitnessScores, totalFitness);
            } while (parent1 == parent2);

            parentPairs.add(new int[]{parent1, parent2});
        }

        return parentPairs;
    }

    private int selectOne(List<Float> fitnessScores, float totalFitness) {
        float r = random.nextFloat() * totalFitness;
        float cumulativeSum = 0;

        for (int i = 0; i < fitnessScores.size(); i++) {
            cumulativeSum += fitnessScores.get(i);
            if (cumulativeSum >= r) {
                return i;
            }
        }

        return fitnessScores.size() - 1;
    }
}
