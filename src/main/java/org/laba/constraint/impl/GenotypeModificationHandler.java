package org.laba.constraint.impl;

import lombok.RequiredArgsConstructor;
import org.laba.constraint.ConstraintHandler;

import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class GenotypeModificationHandler implements ConstraintHandler {
    private final Random random;



    @Override
    public List<String> handleConstraints(List<String> population, int maxWeight, List<Integer> weights) {
        for (int i = 0; i < population.size(); i++) {
            char[] genes = population.get(i).toCharArray();
            int totalWeight = 0;


            for (int j = 0; j < genes.length; j++) {
                if (genes[j] == '1') {
                    totalWeight += weights.get(j);
                }
            }


            while (totalWeight > maxWeight) {
                int randomGeneIndex = random.nextInt(genes.length);
                if (genes[randomGeneIndex] == '1') {
                    genes[randomGeneIndex] = '0';
                    totalWeight -= weights.get(randomGeneIndex);
                }
            }
            population.set(i, new String(genes));
        }
        return population;
    }
}