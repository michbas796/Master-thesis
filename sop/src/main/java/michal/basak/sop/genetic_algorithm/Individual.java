package michal.basak.sop.genetic_algorithm;

import java.util.List;

public class Individual {
    private int fitness;
    private final List<Integer> chromosome;
    private final CitiesGraph citiesGraph;
    
    public Individual(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        chromosome = citiesGraph.getRandomHamiltonianPath();
        evaluateFitness();
    }
    
    private Individual(List<Integer> chromosome, CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        this.chromosome = chromosome;
        evaluateFitness();
    }

    private void evaluateFitness() {
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
    
    public Offsprings crossoverWith(Individual secondParent) {        
        Offsprings offsprings = new Offsprings();
        List<Integer> firstOffspringChromosome = makeOffspringChromosome(this.chromosome, secondParent.chromosome);
        List<Integer> secondOffspringChromosome = makeOffspringChromosome(secondParent.chromosome, this.chromosome);
        offsprings.firstOffspring = new Individual(firstOffspringChromosome, citiesGraph);
        offsprings.secondOffspring = new Individual(secondOffspringChromosome, citiesGraph);        
        return offsprings;
    }
    
    private List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {        
        List<Integer> offspringChromosome = citiesGraph.getEmptyPath();       
        int edgeStart = firstParentChromosome.get(1);
        int edgeEnd = firstParentChromosome.get(2);
        final int NOT_FOUND = -1;
        int firstIndex = NOT_FOUND;
        int secondIndex = NOT_FOUND;
        
        for (int i = 0; i < secondParentChromosome.size(); i++) {
            if (secondParentChromosome.get(i) == edgeStart){
                firstIndex = i;
            } else if (secondParentChromosome.get(i) == edgeEnd) {
                secondIndex = i;
            }
            if (firstIndex != NOT_FOUND && secondIndex != NOT_FOUND) {
                break;
            }
        }
        
        if (secondIndex < firstIndex) {
            int tmp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = tmp;
        }
        
        for (int i = 0; i <= secondIndex; i++) {
            offspringChromosome.add(secondParentChromosome.get(i));
        }
        
        for (int i = secondIndex + 1; i < firstParentChromosome.size(); i++) {
            int value = firstParentChromosome.get(i);
            if (offspringChromosome.contains(value)) {
                continue;
            }
            offspringChromosome.add(value);
        }
        return offspringChromosome;
    }
    
    
    public class Offsprings {
        Individual firstOffspring;
        Individual secondOffspring;
        
        private Offsprings() {
            
        }
                           
        public Individual getFirst() {
            return firstOffspring;
        }
        
        public Individual getSecond() {
            return secondOffspring;
        }
    }
    
}
