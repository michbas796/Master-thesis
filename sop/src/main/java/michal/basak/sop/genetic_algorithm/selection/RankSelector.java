package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;

public class RankSelector extends FitnessBasedSelector implements IndividualSelector {

    private double[] distributionFunction;

    @Override
    public void selectIndividuals(Population population, Population selectedIndividuals) {
        selectedIndividuals.clear();
        population.sortFromWorstToBest();
        distributionFunction = evaluateDistributionFunction(population);
        for (int i = 0; i < population.size(); i++) {
            double randomNumber = Math.random();
            for (int j = 0; j < distributionFunction.length; j++) {
                if (randomNumber < distributionFunction[j]) {
                    selectedIndividuals.add(population.getIndividual(j));
                    break;
                }
            }
        }
    }

}
