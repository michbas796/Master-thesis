package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Population;

public class RankSelector extends FitnessBasedSelector implements IndividualSelector {

    private double[] distributionFunction;

    @Override
    public Population selectIndividualsFrom(Population population) {

        Population selectedIndividuals = new Population();
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
        return selectedIndividuals;
    }

}
