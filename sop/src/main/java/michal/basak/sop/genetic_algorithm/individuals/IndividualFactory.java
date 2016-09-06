package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;

public class IndividualFactory {

    private final CitiesGraph citiesGraph;
    private final PathGenerator pathGenerator;

    public IndividualFactory(CitiesGraph citiesGraph, PathGenerator pathGenerator) {
        this.citiesGraph = citiesGraph;
        this.pathGenerator = pathGenerator;
    }

    public Individual createIndividual() {
        List<Integer> chromosome = pathGenerator.generate();
        return new Individual(chromosome, costOf(chromosome));
    }

    public Individual createIndividual(List<Integer> chromosome) {
        return new Individual(chromosome, costOf(chromosome));
    }

    private int costOf(List<Integer> chromosome) {
        int cost = 0;
        for (int i = 0; i < chromosome.size() - 1; i++) {
            int edgeStart = chromosome.get(i);
            int edgeEnd = chromosome.get(i + 1);
            cost += citiesGraph.weightOfEdge(edgeStart, edgeEnd);
        }
        return cost;
    }

}
