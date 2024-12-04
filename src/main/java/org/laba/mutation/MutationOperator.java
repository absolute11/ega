package org.laba.mutation;

import java.util.List;

public interface MutationOperator {
    void mutate(List<String> offspring, double mutationRate);
}