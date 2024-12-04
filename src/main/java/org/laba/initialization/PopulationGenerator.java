package org.laba.initialization;

import org.laba.data.Pack;

import java.util.List;

public interface PopulationGenerator {
    void generatePopulation(List<String> population, List<Pack> packs, int populationSize, int maxW);
}
