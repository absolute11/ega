package org.laba.crossover.impl;



import lombok.RequiredArgsConstructor;
import org.laba.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class UniformCrossoverOperator implements CrossoverOperator {
    private final Random random;


    @Override
    public List<String> performCrossover(List<int[]> parentPairs, List<String> population) {
        List<String> offspring = new ArrayList<>();

        for (int[] pair : parentPairs) {
            if (pair.length != 2) {
                throw new IllegalArgumentException("Каждая пара должна содержать ровно два родителя.");
            }

            String parent1 = population.get(pair[0]);
            String parent2 = population.get(pair[1]);

            if (parent1.length() != parent2.length()) {
                throw new IllegalArgumentException("Генотипы родителей должны быть одинаковой длины.");
            }

            System.out.println("Выполняется равномерный кроссовер между:");
            System.out.println("Родитель 1: " + parent1);
            System.out.println("Родитель 2: " + parent2);


            for (int j = 0; j < 3; j++) {
                StringBuilder childGenotype = new StringBuilder();
                for (int i = 0; i < parent1.length(); i++) {

                    boolean fromParent1 = random.nextBoolean();
                    char gene = fromParent1 ? parent1.charAt(i) : parent2.charAt(i);
                    childGenotype.append(gene);
                }
                offspring.add(childGenotype.toString());
                System.out.println("Потомок " + (j + 1) + ": " + childGenotype.toString());
            }
        }

        return offspring;
    }
}
