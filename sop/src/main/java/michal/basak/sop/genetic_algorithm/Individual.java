package michal.basak.sop.genetic_algorithm;

import java.util.List;

public class Individual {
    private int fitness;
    private List<Integer> chromosome;
    public Individual(CitiesGraph citiesGraph) {
        chromosome = citiesGraph.getRandomHamiltonianPath();
        fitness = 0;
        for (int i = 0; i < chromosome.size() - 2; i++) {
            fitness += citiesGraph.getEdgeWeight(i, i+1);
        }
    }
    public int getFitness() {
        return fitness;
    }
    
    public List<Integer> getChrmosome() {
        return chromosome;
    }
    
}
