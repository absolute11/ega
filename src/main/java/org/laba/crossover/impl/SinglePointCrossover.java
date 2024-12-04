package org.laba.crossover.impl;

import lombok.RequiredArgsConstructor;
import org.laba.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class SinglePointCrossover implements CrossoverOperator {
    private final Random random;


    @Override
    public List<String> performCrossover(List<int[]> parentPairs, List<String> population) {
        List<String> offspring = new ArrayList<>();

        for (int[] pair : parentPairs) {
            String parent1 = population.get(pair[0]);
            String parent2 = population.get(pair[1]);


            for (int i = 0; i < 3; i++) {
                int crossoverPoint = random.nextInt(parent1.length());

                String child1 = parent1.substring(0, crossoverPoint) + parent2.substring(crossoverPoint);
                String child2 = parent2.substring(0, crossoverPoint) + parent1.substring(crossoverPoint);


                if (i == 0) {
                    offspring.add(child1);
                } else if (i == 1) {
                    offspring.add(child2);
                } else {

                    String child3 = parent1.substring(0, crossoverPoint) + parent2.substring(crossoverPoint);
                    offspring.add(child3);
                }
            }
        }
        return offspring;
    }
}