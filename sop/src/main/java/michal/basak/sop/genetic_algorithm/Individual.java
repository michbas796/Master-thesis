package michal.basak.sop.genetic_algorithm;

public class Individual {
    private int fitness;
    private int[] chromosome;
    public Individual(CitiesGraph citiesGraph) {
        chromosome = citiesGraph.getRandomHamiltonianPath(0, citiesGraph.numberOfCities() - 1);
        fitness = 0;
        for (int i = 0; i < chromosome.length - 1; i++) {
            fitness += citiesGraph.getEdgeWeight(i, i+1);
        }
    }
    public int getFitness() {
        return fitness;
    }
    
    public int[] getChrmosome() {
        return chromosome;
    }
    
}
