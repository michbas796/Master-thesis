package michal.basak.sop.genetic_algorithm;

import com.google.common.base.*;
import java.util.*;
import java.util.concurrent.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class GeneticAlgorithm implements Callable<GeneticAlgorithm.Results> {

    private int currentGenerationNumber;
    private Population population;
    private Population selectedIndividuals;
    private Population offspringsPopulation;
    private GeneticAlgorithmParams params;
    private int generationsWithNoFitnessProgress;
    private final Results results;
    private Stopwatch stopwatch;

    public GeneticAlgorithm(GeneticAlgorithmParams params) {
        this.params = params;
        population = new Population();
        selectedIndividuals = new Population();
        offspringsPopulation = new Population();
        results = new Results();
    }

    @Override
    public Results call() {
        stopwatch = Stopwatch.createStarted();
        prepareFirstPopulation();
        while (isNextGeneration()) {
            selectIndividuals();
            createOffspringsPopulation();
            replacePopulation();
        }
        stopwatch.stop();
        results.executionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        return results;
    }

    private void findNewBestIndividual() {
        Individual currentBest = population.getBestIndividual();
        if (currentBest.getCost() < results.bestIndividual.getCost()) {
            results.bestIndividual = currentBest;
        }
    }

    private boolean isNextGeneration() {
        switch (params.stopCondition) {
            case GENERATIONS_NUMBER:
                return currentGenerationNumber < params.maxGenerationsNumber;
            case MEAN_FITNESS:
                int lastElement = results.meanPopulationCost.size() - 1;
                if (lastElement >= 1 && results.meanPopulationCost.get(lastElement) >= results.meanPopulationCost.get(lastElement - 1)) {
                    generationsWithNoFitnessProgress++;
                } else {
                    generationsWithNoFitnessProgress = 0;
                }
                return generationsWithNoFitnessProgress < params.maxGenerationsNumber;
            case TIME:
                return stopwatch.elapsed(TimeUnit.MILLISECONDS) < params.maxExecutionTimeInMilliseconds;
            default:
                return currentGenerationNumber < params.maxGenerationsNumber;
        }
    }

    private void prepareFirstPopulation() {
        currentGenerationNumber = 0;
        generationsWithNoFitnessProgress = 0;
        Individual newIndividual;
        for (int i = 0; i < params.populationSize; i++) {
            newIndividual = params.individualFactory.createIndividual(params.citiesGraph, params.pathGenerator);
            population.add(newIndividual);
        }
        evaluateMeanCost();
        results.bestIndividual = population.getBestIndividual();
    }

    private void evaluateMeanCost() {
        int individualsCostSum = 0;
        for (Individual i : population) {
            individualsCostSum += i.getCost();
        }
        results.meanPopulationCost.add((double) individualsCostSum / population.size());
    }

    private void selectIndividuals() {
        params.selector.selectIndividuals(population, selectedIndividuals);
    }

    private void createOffspringsPopulation() {
        offspringsPopulation.clear();
        for (int i = 0; i < selectedIndividuals.size() - 1; i += 2) {
            Individual firstParent = selectedIndividuals.getIndividual(i);
            Individual secondParent = selectedIndividuals.getIndividual(i + 1);
            Individual.Offsprings offsprings = firstParent.crossoverWith(secondParent);
            offsprings.getFirst().mutate(params.mutationProbability);
            offsprings.getSecond().mutate(params.mutationProbability);
            offspringsPopulation.add(offsprings.getFirst());
            offspringsPopulation.add(offsprings.getSecond());
        }
    }

    private void replacePopulation() {
        currentGenerationNumber++;
        params.replacer.replace(population, offspringsPopulation);
        evaluateMeanCost();
        findNewBestIndividual();
    }

    public GeneticAlgorithmParams getParams() {
        return params;
    }

    public void setParams(GeneticAlgorithmParams params) {
        this.params = params;
    }

    public class Results {

        private Individual bestIndividual;
        private List<Double> meanPopulationCost;
        private long executionTime;

        private Results() {
            meanPopulationCost = new ArrayList<>();
        }

        public Individual getBestIndividual() {
            return bestIndividual;
        }

        public List<Double> getMeanPopulationCost() {
            return meanPopulationCost;
        }

        public long getExecutionTimeInMilliseconds() {
            return executionTime;
        }
    }

}
