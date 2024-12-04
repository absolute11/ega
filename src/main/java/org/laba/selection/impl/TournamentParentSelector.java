package org.laba.selection.impl;

import lombok.RequiredArgsConstructor;
import org.laba.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class TournamentParentSelector implements ParentSelector {
    private final Random random;
    private final int tournamentSize;
    @Override
    public List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs) {
        List<int[]> parentPairs = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            int parent1 = selectOne(population, fitnessScores);
            int parent2;
            do {
                parent2 = selectOne(population, fitnessScores);
            } while (parent1 == parent2);

            parentPairs.add(new int[]{parent1, parent2});
        }

        return parentPairs;
    }

    private int selectOne(List<String> population, List<Float> fitnessScores) {
        int bestIndex = -1;
        float bestFitness = -1;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            if (fitnessScores.get(randomIndex) > bestFitness) {
                bestFitness = fitnessScores.get(randomIndex);
                bestIndex = randomIndex;
            }
        }

        return bestIndex;
    }
}
