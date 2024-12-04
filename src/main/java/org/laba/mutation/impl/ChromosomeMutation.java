package org.laba.mutation.impl;

import lombok.RequiredArgsConstructor;
import org.laba.mutation.MutationOperator;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class ChromosomeMutation implements MutationOperator {
    private final Random random;

    @Override
    public void mutate(List<String> offspring, double mutationRate) {
        for (int i = 0; i < offspring.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                char[] genes = offspring.get(i).toCharArray();

                for (int j = 0; j < genes.length; j++) {
                    genes[j] = genes[j] == '1' ? '0' : '1';
                }
                offspring.set(i, new String(genes));
                System.out.printf("\u001B[33mХромосомная мутация: Хромосома %d инвертирована\u001B[0m%n", i + 1);
            }
        }
    }
}