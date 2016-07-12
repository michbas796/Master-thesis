package michal.basak.sop.genetic_algorithm;

import java.util.ArrayList;
import java.util.Iterator;

public class Population implements Iterable<Individual>{
    private ArrayList<Individual> individuals;
    
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
    
    public void sortByFitness() {
        individuals.sort((Individual i1, Individual i2) -> i1.getFitness() - i2.getFitness());
    }
                 
}
