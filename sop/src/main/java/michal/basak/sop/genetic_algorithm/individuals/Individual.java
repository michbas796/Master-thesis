package michal.basak.sop.genetic_algorithm.individuals;

import java.util.List;
import michal.basak.sop.genetic_algorithm.CitiesGraph;

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
            int edgeStart = chromosome.get(i);
            int edgeEnd = chromosome.get(i+1);
            fitness += citiesGraph.weightOfEdge(edgeStart, edgeEnd);
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
    
    public void mutate(double mutationProbability) {        
        if (Math.random() < mutationProbability) {
            //TODO mutacja
        }
    }
    
    private List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {        
        List<Integer> offspringChromosome = citiesGraph.getEmptyPath();       
        int edgeStart = firstParentChromosome.get(1);
        int edgeEnd = firstParentChromosome.get(2);
        
        int firstParentEdgeElementIndex = 0;              
        for (int i = secondParentChromosome.size() - 1; i > 0; i--) {
            int currentElement = secondParentChromosome.get(i);
            if (currentElement == edgeStart || currentElement == edgeEnd ){
                firstParentEdgeElementIndex = i;
                break;
            } 
        }
                       
        for (int i = 0; i <= firstParentEdgeElementIndex; i++) {
            offspringChromosome.add(secondParentChromosome.get(i));
        }
        
        for (int i = firstParentEdgeElementIndex + 1; i < firstParentChromosome.size(); i++) {
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
