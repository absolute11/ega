package org.laba.constraint;

import java.util.List;

public interface ConstraintHandler {
    List<String> handleConstraints(List<String> population, int maxWeight, List<Integer> weights);
}