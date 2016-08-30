package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;

public class Individual {

    private int cost;
    private final List<Integer> chromosome;
    private static CitiesGraph citiesGraph;

    Individual(CitiesGraph citiesGraph, List<Integer> existingChromosome) {
        if (Individual.citiesGraph == null) {
            Individual.citiesGraph = citiesGraph;
        }
        chromosome = new LinkedList<>();
        chromosome.addAll(existingChromosome);
        evaluateCost();
    }

    private void evaluateCost() {
        cost = 0;
        for (int i = 0; i < chromosome.size() - 1; i++) {
            int edgeStart = chromosome.get(i);
            int edgeEnd = chromosome.get(i + 1);
            cost += citiesGraph.weightOfEdge(edgeStart, edgeEnd);
        }
    }

    public int getCost() {
        return cost;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }
}
