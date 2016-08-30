package michal.basak.sop.genetic_algorithm;

import com.google.common.base.*;
import java.util.*;
import java.util.concurrent.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class GeneticAlgorithm implements Callable<GeneticAlgorithm.Results> {

    private int currentGenerationNumber;
    private GeneticAlgorithmParams params;
    private int generationsWithNoFitnessProgress;
    private Stopwatch stopwatch;
    private final Population population = new Population();
    private final Population selectedIndividuals = new Population();
    private final Population offspringsPopulation = new Population();
    private final Results results = new Results();
    private final Mutation mutation;
    private final IndividualFactory individualFactory;

    public GeneticAlgorithm(GeneticAlgorithmParams params) {
        this.params = params;
        mutation = new Mutation(params.citiesGraph);
        individualFactory = new IndividualFactory(params.citiesGraph, params.pathGenerator);
    }

    @Override
    public Results call() {
        stopwatch = Stopwatch.createStarted();
        prepareFirstPopulation();
        while (isNextGeneration()) {
            params.selector.selectIndividuals(population, selectedIndividuals);
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
            newIndividual = individualFactory.createIndividual();
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

    private void createOffspringsPopulation() {
        offspringsPopulation.clear();
        for (int i = 0; i < selectedIndividuals.size() - 1; i += 2) {
            List<Integer> firstParentChromosome = selectedIndividuals.getIndividual(i).getChromosome();
            List<Integer> secondParentChromosome = selectedIndividuals.getIndividual(i + 1).getChromosome();
            List<Integer> firstOffspringChromosome = params.crossover.makeOffspringChromosome(firstParentChromosome, secondParentChromosome);
            List<Integer> secondOffspringChromosome = params.crossover.makeOffspringChromosome(secondParentChromosome, firstParentChromosome);
            if (Math.random() < params.mutationProbability) {
                mutation.changeChromosome(firstOffspringChromosome);
            }
            if (Math.random() < params.mutationProbability) {
                mutation.changeChromosome(secondOffspringChromosome);
            }
            offspringsPopulation.add(individualFactory.createIndividual(firstOffspringChromosome));
            offspringsPopulation.add(individualFactory.createIndividual(secondOffspringChromosome));
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
