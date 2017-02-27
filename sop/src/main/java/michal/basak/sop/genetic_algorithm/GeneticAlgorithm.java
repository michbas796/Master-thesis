package michal.basak.sop.genetic_algorithm;

import com.google.common.base.*;
import java.util.*;
import java.util.concurrent.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class GeneticAlgorithm implements Callable<GeneticAlgorithm.Results> {

    private int currentGenerationNumber;
    private GeneticAlgorithmParams params;
    private Stopwatch stopwatch;
    private final Population population = new Population();
    private final Population selectedIndividuals = new Population();
    private final Population offspringsPopulation = new Population();
    private final Results results = new Results();
    private final IndividualFactory individualFactory;

    public GeneticAlgorithm(GeneticAlgorithmParams params) {
        this.params = params;
        individualFactory = new IndividualFactory(params.citiesGraph, params.pathGenerator);
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
        if (currentBest.cost() < results.bestIndividual.cost()) {
            results.bestIndividual = currentBest;
        } else if (currentBest.cost() == results.bestIndividual.cost()) {
            if (Math.random() < 0.5) {
                results.bestIndividual = currentBest;
            }
        }
    }

    private boolean isNextGeneration() {
        return currentGenerationNumber < params.maxGenerationsNumber;
    }

    private void prepareFirstPopulation() {
        currentGenerationNumber = 0;
        for (int i = 0; i < params.populationSize; i++) {
            population.add(individualFactory.createRandomIndividual());
        }
        evaluateMeanCost();
        results.bestIndividual = population.getBestIndividual();
    }

    private void evaluateMeanCost() {
        double meanCost = population.stream()
                .mapToInt((individual) -> individual.cost())
                .average().getAsDouble();
        results.meanPopulationCost.add(meanCost);
    }

    private void selectIndividuals() {
        Population currentlySelectedIndviduals = params.selector.selectIndividualsFrom(population);
        selectedIndividuals.clear();
        selectedIndividuals.addAll(currentlySelectedIndviduals);
    }

    private void createOffspringsPopulation() {
        offspringsPopulation.clear();
        int[] bestIndividualchromosome = selectedIndividuals.getBestIndividual().getChromosomeCopy();
        for (int i = 0; i < selectedIndividuals.size(); i++) {
            int[] secondParentChromosome = selectedIndividuals.getIndividual(i).getChromosomeCopy();
            if (Math.random() < params.crossoverProbability) {
                List<int[]> offspringChromosomes = params.crossover.makeOffspringChromosomes(bestIndividualchromosome, secondParentChromosome);
                for (int[] offspringChromosome : offspringChromosomes) {
                    offspringChromosome = params.mutation.changeChromosome(offspringChromosome);
                    offspringsPopulation.add(individualFactory.createIndividualWith(offspringChromosome));
                }
            } else {
                offspringsPopulation.add(individualFactory.createIndividualWith(bestIndividualchromosome));
                offspringsPopulation.add(individualFactory.createIndividualWith(secondParentChromosome));
            }
        }
        while (offspringsPopulation.size() > population.size()) {
            offspringsPopulation.remove(offspringsPopulation.getWorstIndividual());
        }

    }

    private void replacePopulation() {
        currentGenerationNumber++;
        Population newIndividuals = params.replacer.replace(population, offspringsPopulation);
        population.clear();
        population.addAll(newIndividuals);
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

        public List<Double> getMeanPopulationCosts() {
            return meanPopulationCost;
        }

        public long getExecutionTimeInMilliseconds() {
            return executionTime;
        }
    }

}
