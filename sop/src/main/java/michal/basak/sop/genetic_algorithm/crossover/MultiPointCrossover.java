package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;
import michal.basak.sop.helpers.*;

public class MultiPointCrossover implements CrossoverOperator {

    private List<Integer> selectedIndices;
    private List<Integer> genesFromSecondParent = new ArrayList<>();

    @Override
    public List<int[]> makeOffspringChromosomes(int[] firstParentChromosome, int[] secondParentChromosome) {
        List<int[]> offspringChromosomes = new ArrayList<>();
        selectedIndices = new LinkedList<>();
        int chromosomeSize = firstParentChromosome.length;
        int currentIndex = 3;
        selectedIndices.add(0);
        while (currentIndex < chromosomeSize - 4) {
            int selectedIndex = RandomInteger.getFromRange(currentIndex, chromosomeSize - 3);
            selectedIndices.add(selectedIndex);
            currentIndex = selectedIndex + 3;
        }
        selectedIndices.add(chromosomeSize - 1);
        offspringChromosomes.add(makeChromosome(firstParentChromosome, secondParentChromosome));
        offspringChromosomes.add(makeChromosome(secondParentChromosome, firstParentChromosome));
        return offspringChromosomes;
    }

    public int[] makeChromosome(int[] firstParentChromosome, int[] secondParentChromosome) {
        int[] offspringChromosome = new int[firstParentChromosome.length];
        for (int si = 1; si < selectedIndices.size(); si++) {
            int selectedIndex = selectedIndices.get(si - 1);
            int nextSelectedIndex = selectedIndices.get(si);
            offspringChromosome[selectedIndex] = secondParentChromosome[selectedIndex];
            genesFromSecondParent.clear();
            for (int i = selectedIndex + 1; i < nextSelectedIndex; i++) {
                genesFromSecondParent.add(secondParentChromosome[i]);
            }
            for (int i = selectedIndex + 1; i < nextSelectedIndex; i++) {
                for (int j = 1; j < firstParentChromosome.length; j++) {
                    int geneFromFirstParent = firstParentChromosome[j];
                    if (genesFromSecondParent.contains(geneFromFirstParent)) {
                        offspringChromosome[i] = geneFromFirstParent;
                        genesFromSecondParent.remove((Integer) geneFromFirstParent);
                        break;
                    }
                }
            }
        }
        offspringChromosome[offspringChromosome.length - 1] = secondParentChromosome[secondParentChromosome.length - 1];
        return offspringChromosome;
    }

}
