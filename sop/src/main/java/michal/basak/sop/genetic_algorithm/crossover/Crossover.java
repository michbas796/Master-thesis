package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;

public interface Crossover {

    List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome);

}
