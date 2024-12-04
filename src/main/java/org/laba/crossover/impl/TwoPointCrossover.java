package org.laba.crossover.impl;

import lombok.RequiredArgsConstructor;
import org.laba.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class TwoPointCrossover implements CrossoverOperator {
    private final Random random;

    @Override
    public List<String> performCrossover(List<int[]> parentPairs, List<String> population) {
        List<String> offspring = new ArrayList<>();

        for (int[] pair : parentPairs) {
            String parent1 = population.get(pair[0]);
            String parent2 = population.get(pair[1]);

            int point1 = random.nextInt(parent1.length());
            int point2 = random.nextInt(parent1.length());


            if (point1 > point2) {
                int temp = point1;
                point1 = point2;
                point2 = temp;
            }


            for (int i = 0; i < 3; i++) {
                String child;

                if (i == 0) {
                    child = parent1.substring(0, point1) + parent2.substring(point1, point2) + parent1.substring(point2);
                } else if (i == 1) {
                    child = parent2.substring(0, point1) + parent1.substring(point1, point2) + parent2.substring(point2);
                } else {

                    int newPoint1 = random.nextInt(parent1.length());
                    int newPoint2 = random.nextInt(parent1.length());
                    if (newPoint1 > newPoint2) {
                        int temp = newPoint1;
                        newPoint1 = newPoint2;
                        newPoint2 = temp;
                    }
                    child = parent1.substring(0, newPoint1) + parent2.substring(newPoint1, newPoint2) + parent1.substring(newPoint2);
                }
                offspring.add(child);
            }
        }
        return offspring;
    }
}