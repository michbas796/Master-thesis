package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;

public abstract class FitnessBasedSelector {

    protected static double[] evaluateDistributionFunction(Population population) {
        double[] fitness = evaluateFitness(population);
        double[] distributionFunction = new double[population.size()];
        int totalFitness = evaluateTotalFitness(fitness);
        distributionFunction[0] = fitness[0] / totalFitness;
        for (int i = 1; i < population.size(); i++) {
            distributionFunction[i] = distributionFunction[i - 1] + fitness[i] / (totalFitness); //+ population.getBestIndividual().getCost());
        }
        return distributionFunction;
    }

    protected static double[] evaluateFitness(Population population) {
        double[] fitness = new double[population.size()];
        int highestCost = population.getWorstIndividual().cost();
        int lowestCost = population.getBestIndividual().cost();
        for (int i = 0; i < population.size(); i++) {
            fitness[i] = highestCost - population.getIndividual(i).cost() + lowestCost;
        }
        return fitness;
    }

    private static int evaluateTotalFitness(double[] fitnesses) {
        int totalFitness = 0;
        for (int i = 0; i < fitnesses.length; i++) {
            totalFitness += fitnesses[i];
        }
        return totalFitness;
    }

}
