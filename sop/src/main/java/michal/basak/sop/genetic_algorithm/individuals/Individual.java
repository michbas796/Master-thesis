package michal.basak.sop.genetic_algorithm.individuals;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
            Mutation mutation = new Mutation();
            mutation.changeChromosome();
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
        
        for (int i = 3; i < firstParentChromosome.size(); i++) {
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
    
    private class Mutation {        
        private int firstIndex;
        private int secondIndex;
        private final List<Integer> leftPath;
        private final List<Integer> rightPath;
        private int geneFromLeft;
        private int geneFromRight;

        public Mutation() {
            leftPath = new LinkedList<>();
            rightPath = new LinkedList<>();
        }
                        
        public void changeChromosome() {            
            evaluateIndices();            
            prepareLeftAndRightPath();
            exchangePaths();
        }

        private void evaluateIndices() {
            Random random = new Random();
            firstIndex = random.nextInt(chromosome.size() / 2 - 1) + 1;
            secondIndex = random.nextInt(chromosome.size() - firstIndex - 2) + firstIndex + 1;            
            geneFromLeft = chromosome.get(firstIndex);
            geneFromRight = chromosome.get(secondIndex);
            leftPath.add(geneFromLeft);
            rightPath.add(geneFromRight);
            int index = secondIndex;
            while (index >= firstIndex + 1) {
                index--;
                if (isPrecedenceConstraintBetween(geneFromLeft, geneFromRight)) {                    
                    rightPath.clear();
                    geneFromRight = chromosome.get(index);
                    rightPath.add(geneFromRight);
                    secondIndex = index;
                } 
            }
        }

        private void prepareLeftAndRightPath() {
            int i1 = firstIndex + 1;
            int i2 = secondIndex;
            geneFromLeft = chromosome.get(i1);
            while (i1 < i2 && citiesGraph.weightOfEdge(geneFromLeft, geneFromRight) != CitiesGraph.PRECEDENCE_CONSTRAINT) {
                leftPath.add(geneFromLeft);                
                i2--;
                if (i2 > i1) {
                    geneFromRight = chromosome.get(i2);
                    rightPath.add(0, geneFromRight);                     
                }
                i1++;
                geneFromLeft = chromosome.get(i1);
            }
        }
        
        private void exchangePaths() {
            chromosome.removeAll(leftPath);
            chromosome.removeAll(rightPath);
            chromosome.addAll(firstIndex, rightPath);
            chromosome.addAll(firstIndex + rightPath.size(), leftPath);
        }
        
        private boolean isPrecedenceConstraintBetween(int geneFromLeft, int geneFromRight) {
            return citiesGraph.weightOfEdge(geneFromLeft, geneFromRight) == CitiesGraph.PRECEDENCE_CONSTRAINT;
        }
                
    }
    
}
