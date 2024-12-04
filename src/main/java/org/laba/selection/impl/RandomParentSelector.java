package org.laba.selection.impl;

import lombok.RequiredArgsConstructor;
import org.laba.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomParentSelector implements ParentSelector {
    private final Random random;


    @Override
    public List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs) {
        List<int[]> parentPairs = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            int parent1 = random.nextInt(population.size());
            int parent2;
            do {
                parent2 = random.nextInt(population.size());
            } while (parent1 == parent2);

            parentPairs.add(new int[]{parent1, parent2});
        }

        return parentPairs;
    }
}