package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;

public class Individual {

    private final int COST;
    private final int[] chromosome;

    Individual(int[] existingChromosome, int cost) {
        chromosome = Arrays.copyOf(existingChromosome, existingChromosome.length);
        this.COST = cost;
    }

    public int cost() {
        return COST;
    }

    public int[] getChromosomeCopy() {
        return Arrays.copyOf(chromosome, chromosome.length);
    }
}
