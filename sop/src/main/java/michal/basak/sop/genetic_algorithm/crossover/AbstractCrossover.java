package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;

public abstract class AbstractCrossover implements CrossoverOperator {

    List<int[]> offspringChromosomes = new ArrayList<>();

    @Override
    public List<int[]> makeOffspringChromosomes(int[] firstParentChromosome, int[] secondParentChromosome) {
        offspringChromosomes.clear();
        int[] firstOffspringChromosome = makeOffspringChromosome(firstParentChromosome, secondParentChromosome);
        int[] secondOffspringChromosome = makeOffspringChromosome(secondParentChromosome, firstOffspringChromosome);
        offspringChromosomes.add(firstParentChromosome);
        offspringChromosomes.add(secondOffspringChromosome);
        return offspringChromosomes;
    }

    public abstract int[] makeOffspringChromosome(int[] firstParentChromosome, int[] secondParentChromosome);

}
