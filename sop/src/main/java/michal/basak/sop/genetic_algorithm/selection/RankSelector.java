package michal.basak.sop.genetic_algorithm.selection;

import java.util.stream.*;
import michal.basak.sop.genetic_algorithm.*;

public class RankSelector implements IndividualSelector {

    private final Population selectedIndividuals = new Population();

    @Override
    public Population selectIndividualsFrom(Population population) {
        selectedIndividuals.clear();
        Population sortedPopulation = sortFromWorstToBest(population);
        double[] rank = evaluateRanks(population);
        for (int i = 0; i < sortedPopulation.size(); i++) {
            double randomNumber = Math.random();
            for (int j = 0; j < rank.length; j++) {
                if (randomNumber < rank[j]) {
                    selectedIndividuals.add(sortedPopulation.getIndividual(j));
                    break;
                }
            }
        }
        return selectedIndividuals;
    }

    private Population sortFromWorstToBest(Population population) {
        return population.stream()
                .sorted((individual1, individual2) -> individual2.cost() - individual1.cost())
                .collect(Collectors.toCollection(Population::new));
    }

    private double[] evaluateRanks(Population population) {
        int populationSize = population.size();
        double[] rank = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            rank[i] = (double) (i + 1) / populationSize;
        }
        return rank;
    }

}
