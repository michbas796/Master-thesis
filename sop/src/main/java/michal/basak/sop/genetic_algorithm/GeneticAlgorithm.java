package michal.basak.sop.genetic_algorithm;

import com.google.common.base.Stopwatch;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import michal.basak.sop.genetic_algorithm.individuals.Individual;

public class GeneticAlgorithm implements Callable<GeneticAlgorithm.Results> {
    private int currentGenerationNumber;
    private Population population;
    private Population selectedIndividuals;
    private Population offspringsPopulation;           
    private double prevMeanPopulationFitness;
    private GeneticAlgorithmParams params;
    private int generationsWithNoFitnessProgress;
    private final CitiesGraph citiesGraph;
    private final Results results;
    private Stopwatch stopwatch;

    public GeneticAlgorithm(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        params = new GeneticAlgorithmParams();
        population = new Population();
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
        if (currentBest.getFitness() < results.bestIndividual.getFitness()) {
            results.bestIndividual = currentBest;
        }
    }
    
    private boolean isNextGeneration() {
        switch(params.stopCondition) {
            case GENERATIONS_NUMBER:
                return currentGenerationNumber < params.maxGenerationsNumber;
            case MEAN_FITNESS:
                if (results.meanPopulationFitness <= prevMeanPopulationFitness) {
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
            newIndividual = params.individualFactory.createIndividual(citiesGraph);
            population.add(newIndividual);            
        }
        evaluateMeanFitness(); 
        results.bestIndividual = population.getBestIndividual();
    }
    
    private void evaluateMeanFitness() {
        int individualsFitnessSum = 0;
        for (Individual i : population) {
            individualsFitnessSum += i.getFitness();
        }
        results.meanPopulationFitness = individualsFitnessSum / population.size();
    }
        
    private void selectIndividuals() {
        selectedIndividuals = params.selector.selectIndividualsFrom(population);
    }
           
    private void createOffspringsPopulation() {
        offspringsPopulation = new Population();        
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
        population = params.replacer.replace(population, offspringsPopulation);
        evaluateMeanFitness();
        findNewBestIndividual();
    }
    
    public GeneticAlgorithmParams getParams() {
        return params;
    }
        
    public void setParams(GeneticAlgorithmParams params) {
        this.params = params;
    }
               
    public class Results {
        Individual bestIndividual;
        double meanPopulationFitness;
        long executionTime;
        
        public Individual getBestIndividual() {
            return bestIndividual;
        }
        
        public double getMeanPopulationFitness() {
            return meanPopulationFitness;
        }
        
        public long getExecutionTimeInMilliseconds() {
            return executionTime;
        }
    }
        
}
