package michal.basak.sop.genetic_algorithm;

import michal.basak.sop.genetic_algorithm.crossover.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;
import michal.basak.sop.genetic_algorithm.population_replacing.*;
import michal.basak.sop.genetic_algorithm.selection.*;

public class GeneticAlgorithmParams {

    int maxGenerationsNumber;
    int populationSize;
    double mutationProbability;
    long maxExecutionTimeInMilliseconds;
    CitiesGraph citiesGraph;
    PathGenerator pathGenerator;
    IndividualSelector selector;
    Crossover crossover;
    PopulationReplacer replacer;
    StopCondition stopCondition;

    public GeneticAlgorithmParams(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        maxGenerationsNumber = 10000;
        populationSize = 10;
        mutationProbability = 0.001;
        pathGenerator = new RandomPathGenerator(citiesGraph);
        selector = new RouletteWheelSelector();
        crossover = new MaxPartialOrderCrossover(citiesGraph);
        replacer = new FullReplacer();
        stopCondition = StopCondition.GENERATIONS_NUMBER;
    }

    public GeneticAlgorithmParams setMaxNumberOfGenerations(int maxGenerationsNumber) {
        this.maxGenerationsNumber = maxGenerationsNumber;
        return this;
    }

    public GeneticAlgorithmParams setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public GeneticAlgorithmParams setPathGenerator(AbstractPathGenerator pathGenerator) {
        this.pathGenerator = pathGenerator;
        return this;
    }

    public GeneticAlgorithmParams setSelector(IndividualSelector selector) {
        this.selector = selector;
        return this;
    }

    public GeneticAlgorithmParams setCrossover(Crossover crossover) {
        this.crossover = crossover;
        return this;
    }

    public GeneticAlgorithmParams setReplacer(PopulationReplacer replacer) {
        this.replacer = replacer;
        return this;
    }

    public GeneticAlgorithmParams setStopCondition(StopCondition stopCondition) {
        this.stopCondition = stopCondition;
        return this;
    }

    public GeneticAlgorithmParams setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public GeneticAlgorithmParams setMaxExecutionTimeInMilliseconds(long maxExecutionTimeInMilliseconds) {
        this.maxExecutionTimeInMilliseconds = maxExecutionTimeInMilliseconds;
        return this;
    }

    public enum StopCondition {
        GENERATIONS_NUMBER, MEAN_FITNESS, TIME
    }
}
