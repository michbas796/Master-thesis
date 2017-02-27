package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;
import michal.basak.sop.helpers.*;

public class TwoPointCrossover implements CrossoverOperator {

    int firstIndex;
    int secondIndex;

    @Override
    public List<int[]> makeOffspringChromosomes(int[] firstParentChromosome, int[] secondParentChromosome) {
        firstIndex = RandomInteger.getFromRange(1, firstParentChromosome.length - 1);
        secondIndex = RandomInteger.getFromRange(1, firstParentChromosome.length - 1);
        if (secondIndex < firstIndex) {
            int tmp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = tmp;
        }
        List<int[]> offspringChromosomes = new ArrayList<>();
        int[] firstOffspringChromosome = makeChromosome(firstParentChromosome, secondParentChromosome);
        int[] secondOffspringChromosome = makeChromosome(secondParentChromosome, firstOffspringChromosome);
        offspringChromosomes.add(firstOffspringChromosome);
        offspringChromosomes.add(secondOffspringChromosome);
        return offspringChromosomes;
    }

    public int[] makeChromosome(int[] firstParentChromosome, int[] secondParentChromosome) {
        int[] offspringChromosome = new int[firstParentChromosome.length];
        int[] secondParentFragment = Arrays.copyOfRange(secondParentChromosome, firstIndex, secondIndex);
        int[] leftFragmentOfSecondParent = Arrays.copyOfRange(secondParentChromosome, 0, firstIndex);
        int i = 0;
        int leftGeneIndex = 0;
        int rightGeneIndex = secondIndex;
        while (i < firstParentChromosome.length) {
            int gene = firstParentChromosome[i];
            if (contains(leftFragmentOfSecondParent, gene)) {
                offspringChromosome[leftGeneIndex] = gene;
                leftGeneIndex++;
            } else if (notContains(secondParentFragment, gene)) {
                offspringChromosome[rightGeneIndex] = gene;
                rightGeneIndex++;
            }
            i++;
        }
        for (i = 0; i < secondParentFragment.length; i++) {
            offspringChromosome[firstIndex + i] = secondParentFragment[i];
        }
        return offspringChromosome;
    }

    private boolean contains(int[] source, int value) {
        for (int i = 0; i < source.length; i++) {
            if (source[i] == value) {
                return true;
            }
        }
        return false;
    }

    private boolean notContains(int[] source, int value) {
        return !contains(source, value);
    }
}
