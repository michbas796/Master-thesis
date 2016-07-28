package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Individual;
import michal.basak.sop.genetic_algorithm.Population;
import static michal.basak.sop.genetic_algorithm.Population.totalFitnessOf;

public abstract class IndividualSelector {
    public abstract Population selectIndividualsFrom(Population population);

    protected static double[] evaluateDistributionFunction(Population population) { 
        double[] distributionFunction = new double[population.size()];
        int individualsFitnessSum = totalFitnessOf(population);
        double probability = 1.0 - ((double)(individualsFitnessSum - population.getIndividual(0).getFitness())/(double)individualsFitnessSum);
        distributionFunction[0] = probability;
        for (int i = 1; i < population.size(); i++) {
            Individual currentIndividual = population.getIndividual(i);
            probability = distributionFunction[i - 1] + 1.0 - ((double)(individualsFitnessSum - currentIndividual.getFitness())/(double)individualsFitnessSum);
            distributionFunction[i] = probability;
        }
        return distributionFunction;
    }
}
