package michal.basak.sop.genetic_algorithm.mutation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;

public class NeighborExchangeMutation implements MutationOperator {

    private int[] chromosomeCopy;
    private final CitiesGraph citiesGraph;
    private double mutationProbability;

    public NeighborExchangeMutation(CitiesGraph citiesGraph, double mutationProbability) {
        this.citiesGraph = citiesGraph;
        this.mutationProbability = mutationProbability;
    }

    @Override
    public int[] changeChromosome(int[] chromosome) {
        chromosomeCopy = Arrays.copyOf(chromosome, chromosome.length);
        int currentGene;
        int geneFromRight;
        for (int i = 1; i < chromosomeCopy.length - 1; i++) {
            if (Math.random() < mutationProbability) {
                currentGene = chromosomeCopy[i];
                geneFromRight = chromosomeCopy[i + 1];
                if (isNotPrecedenceConstraintBetween(geneFromRight, currentGene)) {
                    chromosomeCopy[i] = geneFromRight;
                    chromosomeCopy[i + 1] = currentGene;
                }
            }
        }
        return chromosomeCopy;
    }

    @Override
    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    private boolean isNotPrecedenceConstraintBetween(int firstGene, int secondGene) {
        return citiesGraph.weightOfEdge(firstGene, secondGene) != CitiesGraph.PRECEDENCE_CONSTRAINT;
    }

}
