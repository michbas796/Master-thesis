package michal.basak.sop.genetic_algorithm;

import java.util.ArrayList;
import java.util.Iterator;

public class Population implements Iterable<Individual>{
    private final ArrayList<Individual> individuals;
    
    public Population() {
        individuals = new ArrayList<>();
    }
    
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }
    
    public void add(Individual individual) {
        individuals.add(individual);
    }
        
    public int size() {
        return individuals.size();
    }

    @Override
    public Iterator<Individual> iterator() {
        return individuals.iterator();
    }
    
    public void sortFromBestToWorst() {
        individuals.sort((Individual i1, Individual i2) -> i1.getFitness() - i2.getFitness());
    }
    
    public void sortFromWorstToBest() {
        individuals.sort((Individual i1, Individual i2) -> i2.getFitness() - i1.getFitness());
    }
    
    public static int totalFitnessOf(Population population) {
        int fitnessSum = 0;
        for (Individual i : population) {
            fitnessSum += i.getFitness();
        }
        return fitnessSum;
    }
                 
}
