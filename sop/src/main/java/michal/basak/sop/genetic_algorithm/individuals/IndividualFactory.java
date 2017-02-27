package michal.basak.sop.genetic_algorithm.individuals;

import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;

public class IndividualFactory {

    private final CitiesGraph citiesGraph;
    private final PathGenerator pathGenerator;

    public IndividualFactory(CitiesGraph citiesGraph, PathGenerator pathGenerator) {
        this.citiesGraph = citiesGraph;
        this.pathGenerator = pathGenerator;
    }

    public Individual createRandomIndividual() {
        int[] chromosome = pathGenerator.generate();
        return new Individual(chromosome, costOf(chromosome));
    }

    public Individual createIndividualWith(int[] chromosome) {
        return new Individual(chromosome, costOf(chromosome));
    }

    public int costOf(int[] chromosome) {
        int cost = 0;
        for (int i = 0; i < chromosome.length - 1; i++) {
            int edgeStart = chromosome[i];
            int edgeEnd = chromosome[i + 1];
            cost += citiesGraph.weightOfEdge(edgeStart, edgeEnd);
        }
        return cost;
    }

}
