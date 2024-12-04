package org.laba.initialization.impl;


import lombok.RequiredArgsConstructor;
import org.laba.data.Pack;
import org.laba.initialization.PopulationGenerator;

import java.util.List;

@RequiredArgsConstructor
public class GreedyPopulationWithoutRoulette implements PopulationGenerator {

    @Override
    public void generatePopulation(List<String> population, List<Pack> items, int populationSize, int maxWeight) {
        for (int i = 0; i < populationSize; i++) {
            items.sort((item1, item2) -> Float.compare(item2.getValuePerWeight(), item1.getValuePerWeight()));

            char[] chromosome = new char[items.size()];
            for (int geneIndex = 0; geneIndex < items.size(); geneIndex++) {
                chromosome[geneIndex] = '0';
            }

            int currentWeight = 0;


            for (int geneIndex = 0; geneIndex < items.size(); geneIndex++) {
                Pack item = items.get(geneIndex);

                if (!item.isUsed() && (currentWeight + item.getWeight() <= maxWeight)) {
                    currentWeight += item.getWeight();
                    chromosome[geneIndex] = '1';

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
