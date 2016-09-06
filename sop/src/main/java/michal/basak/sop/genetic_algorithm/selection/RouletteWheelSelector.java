package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;

public class RouletteWheelSelector extends FitnessBasedSelector implements IndividualSelector {

    private final Population selectedIndividuals = new Population();

    @Override
    public Population selectIndividualsFrom(Population population) {
        selectedIndividuals.clear();
        double[] distributionFunction = evaluateDistributionFunction(population);
        for (int i = 0; i < population.size(); i++) {
            double randomNumber = Math.random();
            for (int j = 0; j < distributionFunction.length; j++) {
                if (randomNumber < distributionFunction[j]) {
                    selectedIndividuals.add(population.getIndividual(j));
                    break;
                }
            }
        }
        return selectedIndividuals;
    }

}
