package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;
import michal.basak.sop.helpers.*;

public class TwoPointCrossover implements Crossover {

    @Override
    public List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {
        List<Integer> offspringChromosome = new LinkedList<>(); //TODO: do przemyślenia
        List<Integer> firstParentCopy = new LinkedList<>(firstParentChromosome);
        int firstIndex = RandomInteger.getInstance().getFromRange(0, secondParentChromosome.size() - 2);
        int secondIndex = RandomInteger.getInstance().getFromRange(firstIndex, secondParentChromosome.size());
        List<Integer> secondParentFragment = secondParentChromosome.subList(firstIndex, secondIndex);
        firstParentCopy.removeAll(secondParentFragment);
        List<Integer> leftFragmentOfSecondParent = secondParentChromosome.subList(0, firstIndex);
        int i = 0;
        int addedGenes = 0;
        while (addedGenes < leftFragmentOfSecondParent.size()) {
            int gene = firstParentCopy.get(i);
            if (leftFragmentOfSecondParent.contains(gene)) {
                offspringChromosome.add(gene);
                addedGenes++;
            }
            i++;
        }
        firstParentCopy.removeAll(offspringChromosome);
        offspringChromosome.addAll(secondParentFragment);
        offspringChromosome.addAll(firstParentCopy);
        return offspringChromosome;
    }

}
