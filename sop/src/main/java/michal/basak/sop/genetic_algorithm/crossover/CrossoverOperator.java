package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;

@FunctionalInterface
public interface CrossoverOperator {

    List<int[]> makeOffspringChromosomes(int[] firstParentChromosome, int[] secondParentChromosome);

}
