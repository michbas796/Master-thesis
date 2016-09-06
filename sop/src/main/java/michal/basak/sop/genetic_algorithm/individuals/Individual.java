package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;

public class Individual {

    private final int COST;
    private final List<Integer> chromosome;

    Individual(List<Integer> existingChromosome, int cost) {
        chromosome = new LinkedList<>();
        chromosome.addAll(existingChromosome);
        this.COST = cost;
    }

    public int cost() {
        return COST;
    }

    public List<Integer> getChromosomeCopy() {
        return new ArrayList<>(chromosome);
    }
}
