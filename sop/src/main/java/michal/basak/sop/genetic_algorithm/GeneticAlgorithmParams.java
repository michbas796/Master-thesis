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

    public void setMaxNumberOfGenerations(int maxGenerationsNumber) {
        this.maxGenerationsNumber = maxGenerationsNumber;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setPathGenerator(AbstractPathGenerator pathGenerator) {
        this.pathGenerator = pathGenerator;
    }

    public void setSelector(IndividualSelector selector) {
        this.selector = selector;
    }

    public void setCrossover(Crossover crossover) {
        this.crossover = crossover;
    }

    public void setReplacer(PopulationReplacer replacer) {
        this.replacer = replacer;
    }

    public void setStopCondition(StopCondition stopCondition) {
        this.stopCondition = stopCondition;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public void setMaxExecutionTimeInMilliseconds(long maxExecutionTimeInMilliseconds) {
        this.maxExecutionTimeInMilliseconds = maxExecutionTimeInMilliseconds;
    }

    public enum StopCondition {
        GENERATIONS_NUMBER, MEAN_FITNESS, TIME
    }
}
