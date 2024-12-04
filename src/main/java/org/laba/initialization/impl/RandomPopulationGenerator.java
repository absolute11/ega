package org.laba.initialization.impl;

import lombok.RequiredArgsConstructor;
import org.laba.data.Pack;
import org.laba.initialization.PopulationGenerator;

import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
public class RandomPopulationGenerator implements PopulationGenerator {
    private final Random random;

    @Override
    public void generatePopulation(List<String> population, List<Pack> items, int populationSize, int maxWeight) {
        for (int i = 0; i < populationSize; i++) {
            char[] chromosome = new char[items.size()];
            for (int geneIndex = 0; geneIndex < items.size(); geneIndex++) {
                chromosome[geneIndex] = '0';
            }

            int currentWeight = 0;

            for (int geneIndex = 0; geneIndex < items.size(); geneIndex++) {
                int randomIndex = random.nextInt(items.size());
                Pack item = items.get(randomIndex);


                if (!item.isUsed()) {
                    currentWeight += item.getWeight();
                    chromosome[randomIndex] = '1';
                    item.setUsed(true);
                }
            }

            resetItemsState(items);


            population.add(new String(chromosome));
        }
    }


    private void resetItemsState(List<Pack> items) {
        for (Pack item : items) {
            item.setUsed(false);
        }
    }
}