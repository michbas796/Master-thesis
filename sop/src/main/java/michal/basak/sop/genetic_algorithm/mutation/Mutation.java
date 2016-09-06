package michal.basak.sop.genetic_algorithm.mutation;

import java.util.*;

@FunctionalInterface
public interface Mutation {

    void changeChromosome(List<Integer> chromosome);
}
