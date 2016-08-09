package michal.basak.sop.genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import michal.basak.sop.genetic_algorithm.individuals.Individual;

public class Population implements Iterable<Individual> {

    public static int totalCostOf(Population population) {
        int costSum = 0;
        for (Individual i : population) {
            costSum += i.getCost();
        }
        return costSum;
    }

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
        individuals.sort((Individual i1, Individual i2) -> i1.getCost() - i2.getCost());
    }

    public void sortFromWorstToBest() {
        individuals.sort((Individual i1, Individual i2) -> i2.getCost() - i1.getCost());
    }

    public void shuffle() {
        Collections.shuffle(individuals);
    }

    public Individual getBestIndividual() {
        return Collections.min(individuals, (Individual i1, Individual i2) -> i1.getCost() - i2.getCost());
    }
    
    public Individual getWorstIndividual() {
        return Collections.max(individuals, (Individual i1, Individual i2) -> i1.getCost() - i2.getCost());
    }

}
