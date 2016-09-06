package michal.basak.sop.genetic_algorithm.mutation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.helpers.*;

public class RightPathExchangeMutation implements Mutation {

    private int leftIndex;
    private int rightIndex;
    private final List<Integer> rightPath = new LinkedList<>();
    private int geneFromLeft;
    private int geneFromRight;
    private List<Integer> chromosome;
    private final CitiesGraph citiesGraph;

    public RightPathExchangeMutation(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
    }

    @Override
    public void changeChromosome(List<Integer> chromosome) {
        this.chromosome = chromosome;
        evaluateIndices();
        prepareRightPath();
        exchangeLeftGeneWithRightPath();
    }

    private void evaluateIndices() {
        RandomInteger random = RandomInteger.getInstance();
        do {
            leftIndex = random.getFromRange(1, chromosome.size() - 3);
            rightIndex = leftIndex + 1;
            geneFromLeft = chromosome.get(leftIndex);
            geneFromRight = chromosome.get(rightIndex);
        } while (isPrecedenceConstraintBetween(geneFromRight, geneFromLeft));
        rightPath.add(geneFromRight);
    }

    private void prepareRightPath() {
        rightPath.clear();
        int currentIndex = rightIndex + 1;
        if (currentIndex < chromosome.size()) {
            geneFromRight = chromosome.get(currentIndex);
        }
        while (currentIndex < chromosome.size() - 1 && isNotPrecedenceConstraintBetween(geneFromRight, geneFromLeft)) {
            rightPath.add(geneFromRight);
            currentIndex++;
            geneFromRight = chromosome.get(currentIndex);
        }
    }

    private void exchangeLeftGeneWithRightPath() {
        if (rightPath.size() > 0) {
            int randomIndex = RandomInteger.getInstance().getFromRange(rightIndex, rightIndex + rightPath.size());
            chromosome.remove(leftIndex);
            chromosome.add(randomIndex, geneFromLeft);
        }
    }

    private boolean isPrecedenceConstraintBetween(int firstGene, int secondGene) {
        return citiesGraph.weightOfEdge(firstGene, secondGene) == CitiesGraph.PRECEDENCE_CONSTRAINT;
    }

    private boolean isNotPrecedenceConstraintBetween(int firstGene, int secondGene) {
        return citiesGraph.weightOfEdge(firstGene, secondGene) != CitiesGraph.PRECEDENCE_CONSTRAINT;
    }

}
