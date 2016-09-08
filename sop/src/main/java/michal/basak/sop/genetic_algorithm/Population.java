package michal.basak.sop.genetic_algorithm;

import java.util.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class Population extends AbstractCollection<Individual> {

    public static int totalCostOf(Population population) {
        return population.stream().map((i) -> i.cost()).reduce(0, Integer::sum);
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
        individuals.sort((Individual i1, Individual i2) -> i1.cost() - i2.cost());
    }

    public void sortFromWorstToBest() {
        individuals.sort((Individual i1, Individual i2) -> i2.cost() - i1.cost());
    }

    public void shuffle() {
        Collections.shuffle(individuals);
    }

    public Individual getBestIndividual() {
        return Collections.min(individuals, (Individual i1, Individual i2) -> i1.cost() - i2.cost());
    }

    public Individual getWorstIndividual() {
        return Collections.max(individuals, (Individual i1, Individual i2) -> i1.cost() - i2.cost());
    }

    public int totalCost() {
        return individuals.stream().mapToInt((i) -> i.cost()).sum();
    }

}
