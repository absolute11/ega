package org.laba.crossover;
import java.util.List;

public interface CrossoverOperator {
    List<String> performCrossover(List<int[]> parentPairs, List<String> population);
}