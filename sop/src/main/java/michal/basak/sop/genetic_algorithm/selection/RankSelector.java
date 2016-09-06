package michal.basak.sop.genetic_algorithm.selection;

import java.util.*;
import java.util.stream.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.individuals.*;

public class RankSelector extends FitnessBasedSelector implements IndividualSelector {

    private double[] distributionFunction;
    private final Population selectedIndividuals = new Population();

    @Override
    public Population selectIndividualsFrom(Population population) {
        selectedIndividuals.clear();
        List<Individual> sortedPopulation = population.stream()
                .sorted((i1, i2) -> i1.cost() - i2.cost())
                .collect(Collectors.toList());
        distributionFunction = evaluateDistributionFunction(population);
        for (int i = 0; i < sortedPopulation.size(); i++) {
            double randomNumber = Math.random();
            for (int j = 0; j < distributionFunction.length; j++) {
                if (randomNumber < distributionFunction[j]) {
                    selectedIndividuals.add(sortedPopulation.get(j));
                    break;
                }
            }
        }
        return selectedIndividuals;
    }

}
