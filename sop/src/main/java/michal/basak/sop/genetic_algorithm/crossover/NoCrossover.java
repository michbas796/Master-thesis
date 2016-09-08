package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;

public class NoCrossover implements Crossover {

    @Override
    public List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {
        return firstParentChromosome;
    }

}
