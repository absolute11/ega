package org.laba.constraint.impl;

import org.laba.constraint.ConstraintHandler;

import java.util.ArrayList;
import java.util.List;

public class EliminationHandler implements ConstraintHandler {
    @Override
    public List<String> handleConstraints(List<String> population, int maxWeight, List<Integer> weights) {
        List<String> validPopulation = new ArrayList<>();

        for (String chromosome : population) {
            int totalWeight = 0;
            char[] genes = chromosome.toCharArray();
            for (int i = 0; i < genes.length; i++) {
                if (genes[i] == '1') {
                    totalWeight += weights.get(i);
                }
            }

            if (totalWeight <= maxWeight) {
                validPopulation.add(chromosome);
            }
        }
        return validPopulation;
    }
}
