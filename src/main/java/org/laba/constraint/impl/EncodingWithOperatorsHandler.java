package org.laba.constraint.impl;

import org.laba.constraint.ConstraintHandler;

import java.util.ArrayList;
import java.util.List;
public class EncodingWithOperatorsHandler implements ConstraintHandler {

    @Override
    public List<String> handleConstraints(List<String> population, int maxWeight, List<Integer> weights) {
        List<String> validPopulation = new ArrayList<>();

        for (String chromosome : population) {
            char[] genes = chromosome.toCharArray();
            int totalWeight = 0;

            // Шаг 1: Проверяем текущий вес хромосомы
            for (int i = 0; i < genes.length; i++) {
                if (genes[i] == '1') {
                    totalWeight += weights.get(i);
                    if (totalWeight > maxWeight) {
                        // Превышение веса — флаг невалидности
                        break;
                    }
                }
            }

            // Шаг 2: Если хромосома не превышает вес, добавляем ее
            if (totalWeight <= maxWeight) {
                validPopulation.add(chromosome);
            } else {
                // Шаг 3: Применяем корректировку генов
                for (int i = genes.length - 1; i >= 0 && totalWeight > maxWeight; i--) {
                    if (genes[i] == '1') {
                        genes[i] = '0';
                        totalWeight -= weights.get(i);
                    }
                }

                // Шаг 4: Проверяем, не превышает ли вес допустимый предел после исправления
                if (totalWeight <= maxWeight) {
                    validPopulation.add(new String(genes)); // Добавляем исправленную хромосому
                }
            }
        }

        // Возвращаем только хромосомы, которые удовлетворяют ограничениям по весу
        return validPopulation;
    }
}