package org.laba.mutation.impl;

import lombok.RequiredArgsConstructor;
import org.laba.mutation.MutationOperator;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class InversionMutationOperator implements MutationOperator {
    private final Random random;

    @Override
    public void mutate(List<String> offspring, double mutationRate) {
        for (int i = 0; i < offspring.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                String genotype = offspring.get(i);
                int length = genotype.length();

                if (length < 2) continue;


                int point1 = random.nextInt(length - 1);
                int point2 = point1 + random.nextInt(length - point1);


                StringBuilder mutatedGenotype = new StringBuilder(genotype);
                for (int j = point1; j < point2; j++) {
                    char currentBit = mutatedGenotype.charAt(j);
                    char invertedBit = (currentBit == '0') ? '1' : '0';
                    mutatedGenotype.setCharAt(j, invertedBit);
                }

                offspring.set(i, mutatedGenotype.toString());


                System.out.printf("\u001B[32mИнверсионная мутация: Хромосома %d изменена, гены [%d-%d] инвертированы\u001B[0m%n",
                        i + 1, point1, point2 - 1);
            }
        }
    }
}
