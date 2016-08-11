package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;

public class StochasticUniversalSamplingSelector extends FitnessBasedSelector implements IndividualSelector {

    @Override
    public void selectIndividuals(Population population, Population selectedIndividuals) {
        selectedIndividuals.clear();
        int populationSize = population.size();
        population.shuffle();
        double distanceBetweenPointers = 1.0 / populationSize;
        double pointer = Math.random() * distanceBetweenPointers;
        double[] distributionFunction = evaluateDistributionFunction(population);
        for (int i = 0; i < populationSize; i++) {
            while (distributionFunction[i] > pointer) {
                selectedIndividuals.add(population.getIndividual(i));
                pointer += distanceBetweenPointers;
            }
        }
    }

}
