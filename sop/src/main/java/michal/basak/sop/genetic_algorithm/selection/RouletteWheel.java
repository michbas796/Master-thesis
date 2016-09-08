package michal.basak.sop.genetic_algorithm.selection;

import java.util.stream.*;
import michal.basak.sop.genetic_algorithm.*;

public abstract class FitnessBasedSelector {

    protected static double[] evaluateDistributionFunction(Population population) {
        double[] fitness = evaluateFitness(population);
        double[] distributionFunction = new double[population.size()];
        double totalFitness = DoubleStream.of(fitness).sum();
        distributionFunction[0] = fitness[0] / totalFitness;
        for (int i = 1; i < population.size(); i++) {
            distributionFunction[i] = distributionFunction[i - 1] + fitness[i] / totalFitness;
        }
        return distributionFunction;
    }

    protected static double[] evaluateFitness(Population population) {
        double[] fitness = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            fitness[i] = 1.0 / population.getIndividual(i).cost();
        }
        return fitness;
    }

}
