package org.laba.initialization.impl;

import org.laba.data.Pack;
import org.laba.initialization.PopulationGenerator;

import java.util.List;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class GreedyPopulationGenerator implements PopulationGenerator {
    private final Random random;

    @Override
    public void generatePopulation(List<String> population, List<Pack> items, int populationSize, int maxWeight) {
        for (int i = 0; i < populationSize; i++) {
            char[] chromosome = new char[populationSize];
            for (int geneIndex = 0; geneIndex < populationSize; geneIndex++) {
                chromosome[geneIndex] = '0';
            }

            int currentWeight = 0;
            for (int geneIndex = 0; geneIndex < populationSize; geneIndex++) {
                int selectedIndex = ruletkaIndex(items);
                if (selectedIndex != -1) {
                    Pack item = items.get(selectedIndex);


                    if ((currentWeight + item.getWeight() <= maxWeight) && chromosome[selectedIndex] == '0') {
                        currentWeight += item.getWeight();
                        chromosome[selectedIndex] = '1';


                        item.setUsed(true);
                    }
                }
            }


            resetItemsState(items);

            population.add(new String(chromosome));
        }
    }

    private int ruletkaIndex(List<Pack> items) {
        float P = random.nextFloat();
        float sumFract = 0;


        float totalFraction = calculateTotalFraction(items);


        for (int i = 0; i < items.size(); i++) {
            Pack item = items.get(i);
            if (!item.isUsed()) {
                sumFract += item.getValuePerWeight() / totalFraction;
                if (P <= sumFract) {
                    return i;
                }
            }
        }
        return -1;
    }

    private float calculateTotalFraction(List<Pack> items) {
        float totalFraction = 0;
        for (Pack item : items) {
            if (!item.isUsed()) {
                totalFraction += item.getValuePerWeight();
            }
        }
        return totalFraction;
    }

    private void resetItemsState(List<Pack> items) {
        for (Pack item : items) {
            item.setUsed(false);
        }
    }
}
