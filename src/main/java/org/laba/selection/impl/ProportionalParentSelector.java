package org.laba.selection.impl;

import lombok.RequiredArgsConstructor;
import org.laba.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class ProportionalParentSelector implements ParentSelector {
    private final Random random;



    @Override
    public List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs) {
        List<int[]> parentPairs = new ArrayList<>();
        float totalFitness = 0;
        for (Float fitness : fitnessScores) {
            totalFitness += fitness;
        }

        for (int i = 0; i < numPairs; i++) {
            int parent1 = selectOne(population, fitnessScores, totalFitness);
            int parent2;
            do {
                parent2 = selectOne(population, fitnessScores, totalFitness);
            } while (parent1 == parent2);

            parentPairs.add(new int[]{parent1, parent2});
        }
        return parentPairs;
    }

    private int selectOne(List<String> population, List<Float> fitnessScores, float totalFitness) {
        float rand = random.nextFloat() * totalFitness;
        float cumulativeFitness = 0;
        for (int i = 0; i < population.size(); i++) {
            cumulativeFitness += fitnessScores.get(i);
            if (cumulativeFitness >= rand) {
                return i;
            }
        }
        return population.size() - 1;
    }
}