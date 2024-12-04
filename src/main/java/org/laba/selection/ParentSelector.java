package org.laba.selection;

import java.util.List;

public interface ParentSelector {
    List<int[]> selectParents(List<String> population, List<Float> fitnessScores, int numPairs);
}
