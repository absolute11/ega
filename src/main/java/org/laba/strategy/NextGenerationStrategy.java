package org.laba.strategy;

import java.util.List;

public interface NextGenerationStrategy {
    List<String> formNextGeneration(List<String> population, List<String> offspring);
}