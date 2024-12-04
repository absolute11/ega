package org.laba.selection.impl;

import lombok.RequiredArgsConstructor;
import org.laba.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class RankParentSelector implements ParentSelector {
    private final Random random;



    @Override
    public List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs) {
        List<int[]> parentPairs = new ArrayList<>();
        List<Integer> rankedIndices = rankPopulation(fitnessScores);

        for (int i = 0; i < numPairs; i++) {
            int parent1 = rankedIndices.get(random.nextInt(rankedIndices.size()));
            int parent2;
            do {
                parent2 = rankedIndices.get(random.nextInt(rankedIndices.size()));
            } while (parent1 == parent2);

            parentPairs.add(new int[]{parent1, parent2});
        }
        return parentPairs;
    }

    private List<Integer> rankPopulation(List<Float> fitnessScores) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fitnessScores.size(); i++) {
            indices.add(i);
        }
        indices.sort((i1, i2) -> fitnessScores.get(i2).compareTo(fitnessScores.get(i1)));
        return indices;
    }
}