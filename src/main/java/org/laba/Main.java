package org.laba;


import org.laba.constraint.ConstraintHandler;
import org.laba.constraint.impl.EliminationHandler;
import org.laba.constraint.impl.EncodingWithOperatorsHandler;
import org.laba.constraint.impl.GenotypeModificationHandler;
import org.laba.constraint.impl.PenaltyMethodHandler;
import org.laba.crossover.CrossoverOperator;
import org.laba.crossover.impl.SinglePointCrossover;
import org.laba.crossover.impl.TwoPointCrossover;
import org.laba.crossover.impl.UniformCrossoverOperator;
import org.laba.data.Pack;
import org.laba.initialization.PopulationGenerator;
import org.laba.initialization.impl.GreedyPopulationGenerator;
import org.laba.initialization.impl.GreedyPopulationWithoutRoulette;
import org.laba.initialization.impl.RandomPopulationGenerator;
import org.laba.initialization.impl.RandomWithControl;
import org.laba.mutation.MutationOperator;
import org.laba.mutation.impl.ChromosomeMutation;
import org.laba.mutation.impl.GeneMutation;
import org.laba.mutation.impl.InversionMutationOperator;
import org.laba.selection.ParentSelector;
import org.laba.selection.impl.RandomParentSelector;
import org.laba.selection.impl.RouletteParentSelector;
import org.laba.selection.impl.TournamentParentSelector;
import org.laba.stop.StopCondition;
import org.laba.stop.impl.DiversityStopCondition;
import org.laba.stop.impl.FitnessEvaluationStopCondition;
import org.laba.stop.impl.GenerationStopCondition;
import org.laba.strategy.NextGenerationStrategy;
import org.laba.strategy.impl.MuLambdaStrategy;
import org.laba.strategy.impl.MuPlusLambdaStrategy;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите кол во особей в популяции : ");
        int populationSize = scanner.nextInt();
        scanner.nextLine();
        int maxWeight = 100;
        int numParentPairs = populationSize/2;
        List<String> population = new ArrayList<>();
        List<Pack> packs = createPacks();
        Random random = new Random();
        double mutationRate;



        System.out.println("Выберите условие остановки:");
        System.out.println("1 - По числу поколений");
        System.out.println("2 - По числу вычислений приспособленности");
        System.out.println("3 - По разнообразию");
        int stopConditionChoice = scanner.nextInt();
        StopCondition stopCondition;
        switch (stopConditionChoice) {
            case 1:
                System.out.println("Введите максимальное число поколений:");
                int maxGenerations = scanner.nextInt();
                stopCondition = new GenerationStopCondition(maxGenerations);
                break;
            case 2:
                System.out.println("Введите максимальное число вычислений приспособленности:");
                int maxFitnessEvaluations = scanner.nextInt();
                stopCondition = new FitnessEvaluationStopCondition(maxFitnessEvaluations);
                break;
            case 3:
                System.out.println("Введите минимальное разнообразие (0-1):");
                double minDiversity = scanner.nextDouble();
                stopCondition = new DiversityStopCondition(minDiversity);
                break;
            default:
                stopCondition = new GenerationStopCondition(100);
        }

        // Метод генерации популяции
        System.out.println("Выберите метод генерации популяции:");
        System.out.println("1 - Случайный");
        System.out.println("2 - Жадный с рулеткой");
        System.out.println("3 - Жадный без рулетки");
        System.out.println("4 - Случайный с контролем");
        int generatorChoice = scanner.nextInt();
        PopulationGenerator generator = switch (generatorChoice) {
            case 1 -> new RandomPopulationGenerator(random);
            case 2 -> new GreedyPopulationGenerator(random);
            case 3 -> new GreedyPopulationWithoutRoulette();
            case 4 -> new RandomWithControl(random);
            default -> new RandomPopulationGenerator(random);
        };
        generator.generatePopulation(population, packs, populationSize, maxWeight);
        if (generatorChoice == 1) {
            ConstraintHandler constraintHandler2 = new GenotypeModificationHandler(random);
            population = constraintHandler2.handleConstraints(population, maxWeight, extractWeights(packs));
        }
        // Селекция родителей
        System.out.println("Выберите метод отбора родителей:");
        System.out.println("1 - Случайный");
        System.out.println("2 - Турнирный");
        System.out.println("3 - Пропорциональный (рулетка)");
        int selectionChoice = scanner.nextInt();
        ParentSelector parentSelector;
        switch (selectionChoice) {
            case 1 -> parentSelector = new RandomParentSelector(random);
            case 2 -> {
                System.out.println("Введите размер турнира:");
                int tournamentSize = scanner.nextInt();
                parentSelector = new TournamentParentSelector(random, tournamentSize);
            }
            case 3 -> parentSelector = new RouletteParentSelector(random);
            default -> parentSelector = new RandomParentSelector(random);
        }

        // Кроссовер
        System.out.println("Выберите метод кроссовера:");
        System.out.println("1 - Одноточечный");
        System.out.println("2 - Двухточечный");
        System.out.println("3 - универсальный");
        int crossoverChoice = scanner.nextInt();
        CrossoverOperator crossoverOperator;
        if (crossoverChoice == 1) {
            crossoverOperator = new SinglePointCrossover(random);
        } else if (crossoverChoice == 2) {
            crossoverOperator = new TwoPointCrossover(random);
        } else if (crossoverChoice == 3) {
            crossoverOperator = new UniformCrossoverOperator(random);
        } else {
            throw new IllegalArgumentException("Некорректный выбор метода мутации");
        }

        // Мутация
        System.out.println("Выберите метод мутации:");
        System.out.println("1 - Генная мутация");
        System.out.println("2 - Хромосомная мутация");
        System.out.println("3 - Инверсионная мутация");

        int mutationChoice = scanner.nextInt();
        System.out.println("Введите вероятность мутации Например (0,1) для 10%");
        mutationRate = scanner.nextDouble();
        scanner.nextLine();
        MutationOperator mutationOperator;

        if (mutationChoice == 1) {
            mutationOperator = new GeneMutation(random);
        } else if (mutationChoice == 2) {
            mutationOperator = new ChromosomeMutation(random);
        } else if (mutationChoice == 3) {
            mutationOperator = new InversionMutationOperator(random);
        } else {
            throw new IllegalArgumentException("Некорректный выбор метода мутации");
        }

        // Обработка ограничений
        System.out.println("Выберите метод обработки ограничений:");
        System.out.println("1 - Элиминация");
        System.out.println("2 - Метод штрафов");
        System.out.println("3 - Кодировка + операторы");
        System.out.println("4 - Модификация генотипа");
        int constraintChoice = scanner.nextInt();
        ConstraintHandler constraintHandler = switch (constraintChoice) {
            case 1 -> new EliminationHandler();
            case 2 -> new PenaltyMethodHandler(packs);
            case 3 -> new EncodingWithOperatorsHandler();
            case 4 -> new GenotypeModificationHandler(random);
            default -> new EliminationHandler();
        };


        System.out.println("Выберите стратегию формирования следующего поколения:");
        System.out.println("1 - μ+λ");
        System.out.println("2 - μ,λ");
        int strategyChoice = scanner.nextInt();

        NextGenerationStrategy strategy;

        switch (strategyChoice) {
            case 1 -> {
                strategy = new MuPlusLambdaStrategy();
            }

            case 2 -> strategy = new MuLambdaStrategy();
            default -> {
                System.out.println("Некорректный выбор! По умолчанию используется стратегия μ+λ.");
                strategy = new MuPlusLambdaStrategy();
            }
        }



         population = constraintHandler.handleConstraints(population, maxWeight, extractWeights(packs));

        int currentGeneration = 0;
        int fitnessEvaluations = 0;

        String bestOverallIndividual = null;
        int bestOverallWeight = 0;
        int bestOverallValue = 0;

        while (!stopCondition.shouldStop(currentGeneration, fitnessEvaluations, population)) {
            System.out.printf("\u001B[31mПоколение %d:\u001B[0m%n", currentGeneration);


            System.out.println("\u001B[32mРодители:\u001B[0m");
            printFormattedPopulation(population, packs);


            List<Float> fitnessScores = calculateFitness(population, packs);


            List<int[]> parentPairs = parentSelector.selectParents(population, fitnessScores, numParentPairs);
            List<String> offspring = crossoverOperator.performCrossover(parentPairs, population);



            mutationOperator.mutate(offspring, mutationRate);


            List<Integer> weights = extractWeights(packs);
            offspring = constraintHandler.handleConstraints(offspring, maxWeight, weights);

            System.out.println("\u001B[34mДети:\u001B[0m");
            printFormattedPopulation(offspring, packs);


            List<String> nextGeneration = strategy.formNextGeneration(population, offspring);
            population = selectNextGeneration(nextGeneration, packs, populationSize, parentSelector);


            System.out.println("\u001B[31mНовое поколение:\u001B[0m");
            printFormattedPopulation(population, packs);


            String bestIndividual = findBestIndividual(population, packs);
            int[] bestData = getChromosomeData(bestIndividual, packs);
            System.out.printf("\u001B[32mЛучшая особь в поколении: %s  вес - %d, ценность - %d\u001B[0m%n", bestIndividual, bestData[0], bestData[1]);

            // Сравнение с лучшей особью за все поколения
            if (bestOverallIndividual == null || bestData[1] > bestOverallValue) {
                bestOverallIndividual = bestIndividual;
                bestOverallWeight = bestData[0];
                bestOverallValue = bestData[1];
            }

            currentGeneration++;
            fitnessEvaluations += population.size();
        }

        // Вывод лучшей особи за все поколения
        System.out.println("\u001B[31mЛучшая особь за все поколения:\u001B[0m");
        System.out.printf("\u001B[32m%s  вес - %d, ценность - %d\u001B[0m%n", bestOverallIndividual, bestOverallWeight, bestOverallValue);
    }



    private static int[] getChromosomeData(String chromosome, List<Pack> packs) {
        int totalWeight = 0;
        int totalValue = 0;
        char[] genes = chromosome.toCharArray();
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == '1') {
                totalWeight += packs.get(i).getWeight();
                totalValue += packs.get(i).getValue();
            }
        }
        return new int[]{totalWeight, totalValue};
    }


    private static String findBestIndividual(List<String> population, List<Pack> packs) {
        return population.stream()
                .max(Comparator.comparing(ind -> calculateFitness(List.of(ind), packs).get(0)))
                .orElse("Нет данных");
    }

    private static List<String> selectNextGeneration(
            List<String> population,
            List<Pack> packs,
            int populationSize,
            ParentSelector parentSelector) {

        // Вычисляем приспособленность для всех особей
        List<Float> fitnessScores = calculateFitness(population, packs);

        // Используем ParentSelector для выбора родителей
        List<int[]> selectedPairs = parentSelector.selectParents(population, fitnessScores, populationSize / 2);

        // Создаем список для нового поколения
        List<String> selectedGeneration = new ArrayList<>();

        // Добавляем выбранных особей в новое поколение
        for (int[] pair : selectedPairs) {
            // Добавляем первую особь из пары
            selectedGeneration.add(population.get(pair[0]));
            // Убедимся, что размер не превышает populationSize
            if (selectedGeneration.size() < populationSize) {
                // Добавляем вторую особь из пары
                selectedGeneration.add(population.get(pair[1]));
            }
        }

        // Ограничиваем размер нового поколения до populationSize
        if (selectedGeneration.size() > populationSize) {
            selectedGeneration = selectedGeneration.subList(0, populationSize);
        }

        return selectedGeneration;
    }

    private static List<Pack> createPacks() {
        List<Pack> packs = new ArrayList<>();
        packs.add(new Pack(24, 29));
        packs.add(new Pack(29, 30));
        packs.add(new Pack(6, 12));
        packs.add(new Pack(11, 10));
        packs.add(new Pack(20, 22));
        packs.add(new Pack(3, 4));
        packs.add(new Pack(15, 16));
        packs.add(new Pack(3, 22));
        packs.add(new Pack(15, 8));
        packs.add(new Pack(21, 16));
        packs.add(new Pack(4, 6));
        packs.add(new Pack(16, 16));
        packs.add(new Pack(24, 20));
        packs.add(new Pack(29, 16));
        packs.add(new Pack(24, 10));
        return packs;
    }

    private static List<Float> calculateFitness(List<String> population, List<Pack> packs) {
        List<Float> fitnessScores = new ArrayList<>();
        for (String chromosome : population) {
            float fitness = 0;
            char[] genes = chromosome.toCharArray();
            for (int i = 0; i < genes.length; i++) {
                if (genes[i] == '1') {
                    fitness += packs.get(i).getValuePerWeight();
                }
            }
            fitnessScores.add(fitness);
        }
        return fitnessScores;
    }

    private static void printFormattedPopulation(List<String> population, List<Pack> packs) {
        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i);
            int totalWeight = 0;
            int totalValue = 0;
            char[] genes = chromosome.toCharArray();
            for (int j = 0; j < genes.length; j++) {
                if (genes[j] == '1') {
                    totalWeight += packs.get(j).getWeight();
                    totalValue += packs.get(j).getValue();
                }
            }
            System.out.printf("%d)  %s  вес - %d  ценность - %d%n", i + 1, chromosome, totalWeight, totalValue);
        }
    }

    private static void printParentPairs(List<int[]> parentPairs, List<String> population, List<Pack> packs) {
        int pairNumber = 1;
        for (int[] pair : parentPairs) {
            System.out.printf("Пара %d:%n", pairNumber++);
            printParentDetails(pair[0], population, packs);
            printParentDetails(pair[1], population, packs);
        }
    }

    private static void printParentDetails(int parentIndex, List<String> population, List<Pack> packs) {
        String chromosome = population.get(parentIndex);
        int totalWeight = 0;
        int totalValue = 0;
        char[] genes = chromosome.toCharArray();
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == '1') {
                totalWeight += packs.get(i).getWeight();
                totalValue += packs.get(i).getValue();
            }
        }
        System.out.printf("  %s  вес - %d  ценность - %d%n", chromosome, totalWeight, totalValue);
    }

    private static List<Integer> extractWeights(List<Pack> packs) {
        List<Integer> weights = new ArrayList<>();
        for (Pack pack : packs) {
            weights.add(pack.getWeight());
        }
        return weights;
    }
}
