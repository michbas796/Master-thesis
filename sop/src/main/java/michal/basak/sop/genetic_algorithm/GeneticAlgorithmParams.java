package michal.basak.sop.genetic_algorithm;

import michal.basak.sop.genetic_algorithm.crossover.*;
import michal.basak.sop.genetic_algorithm.individuals.*;
import michal.basak.sop.genetic_algorithm.mutation.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;
import michal.basak.sop.genetic_algorithm.population_replacing.*;
import michal.basak.sop.genetic_algorithm.selection.*;

public class GeneticAlgorithmParams {

    int maxGenerationsNumber;
    int populationSize;
    double crossoverProbability;
    double mutationProbability;
    long maxExecutionTimeInMilliseconds;
    int numberOfSubpopulations;
    CitiesGraph citiesGraph;
    PathGenerator pathGenerator;
    IndividualFactory individualFactory;
    IndividualSelector selector;
    CrossoverOperator crossover;
    MutationOperator mutation;
    PopulationReplacer replacer;

    public GeneticAlgorithmParams(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        maxGenerationsNumber = 10000;
        populationSize = 1000;
        crossoverProbability = 1.0;
        mutationProbability = 0.999;
        pathGenerator = new RandomPathGenerator(citiesGraph);
        individualFactory = new IndividualFactory(citiesGraph, pathGenerator);
        selector = new TournamentSelector(2);
        crossover = new TwoPointCrossover();
        mutation = new NeighborExchangeMutation(citiesGraph, mutationProbability);
        replacer = new FullReplacer();
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

    public GeneticAlgorithmParams setCrossover(CrossoverOperator crossover) {
        this.crossover = crossover;
        return this;
    }

    public GeneticAlgorithmParams setReplacer(PopulationReplacer replacer) {
        this.replacer = replacer;
        return this;
    }

    public GeneticAlgorithmParams setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public GeneticAlgorithmParams setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        mutation.setMutationProbability(mutationProbability);
        return this;
    }

}
