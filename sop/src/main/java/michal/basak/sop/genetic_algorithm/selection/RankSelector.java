package michal.basak.sop.genetic_algorithm.selection;

import static java.util.stream.Collectors.toCollection;
import java.util.stream.*;
import michal.basak.sop.genetic_algorithm.*;
import static michal.basak.sop.genetic_algorithm.selection.RouletteWheel.selectRandomSlotFrom;

public class RankSelector implements IndividualSelector {

    private final Population selectedIndividuals = new Population();
    private Population sortedPopulation;

    @Override
    public Population selectIndividualsFrom(Population population) {
        selectedIndividuals.clear();
        sortedPopulation = sortFromWorstToBest(population);
        double[] rouletteWheelSlots = RouletteWheel.evaluateSlots(evaluateProbabilities());
        for (int i = 0; i < population.size(); i++) {
            int selectedIndividualNumber = selectRandomSlotFrom(rouletteWheelSlots);
            selectedIndividuals.add(sortedPopulation.getIndividual(selectedIndividualNumber));
        }
        return selectedIndividuals;
    }

    private Population sortFromWorstToBest(Population population) {
        return population.stream()
                .sorted((individual1, individual2) -> individual2.cost() - individual1.cost())
                .collect(toCollection(Population::new));
    }

    private double[] evaluateProbabilities() {
        int populationSize = sortedPopulation.size();
        double[] ranks = new double[populationSize];
        double[] probabilities = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            ranks[i] = (double) (i + 1) / populationSize;
        }
        double ranksSum = DoubleStream.of(ranks).sum();
        for (int i = 0; i < populationSize; i++) {
            probabilities[i] = ranks[i] / ranksSum;
        }
        return probabilities;
    }
}
