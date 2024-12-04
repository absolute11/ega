package org.laba.constraint.impl;

import lombok.RequiredArgsConstructor;
import org.laba.constraint.ConstraintHandler;
import org.laba.data.Pack;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class PenaltyMethodHandler implements ConstraintHandler {
    private final List<Pack> packs;
    private static final int PENALTY_PER_KG = 50;

    @Override
    public List<String> handleConstraints(List<String> population, int maxWeight, List<Integer> weights) {
        List<String> validPopulation = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            char[] genes = population.get(i).toCharArray();
            int totalWeight = 0;
            int totalValue = 0;


            for (int j = 0; j < genes.length; j++) {
                if (genes[j] == '1') {
                    totalWeight += weights.get(j);
                    totalValue += packs.get(j).getValue();
                }
            }


            if (totalWeight > maxWeight) {
                int excessWeight = totalWeight - maxWeight;
                int penalty = excessWeight * PENALTY_PER_KG;
                totalValue -= penalty;



                totalValue = Math.max(totalValue, 0);


                System.out.printf("Хромосома %s превышает вес на %d кг! Применен штраф: %d, новая ценность: %d%n",
                        population.get(i), excessWeight, penalty, totalValue);
            }


            if (totalValue > 0) {

                validPopulation.add(new String(genes));
            }
        }

        return validPopulation;
    }
}