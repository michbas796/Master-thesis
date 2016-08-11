package michal.basak.sop.genetic_algorithm;

import java.util.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class Population extends AbstractCollection<Individual> {

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

    public Population(Collection<Individual> collection) {
        individuals = new ArrayList<>(collection);
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public Individual removeIndividual(int index) {
        return individuals.remove(index);
    }

    @Override
    public boolean add(Individual individual) {
        return individuals.add(individual);
    }

    @Override
    public void clear() {
        individuals.clear();
    }

    @Override
    public boolean isEmpty() {
        return individuals.isEmpty();
    }

    @Override
    public int size() {
        return individuals.size();
    }

    @Override
    public Iterator<Individual> iterator() {
        return individuals.iterator();
    }

    @Override
    public boolean remove(Object o) {
        return individuals.remove(o);
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
