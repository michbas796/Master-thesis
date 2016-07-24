package michal.basak.sop.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteWheelSelector implements IndividualSelector {
    
    @Override
    public Population selectIndividuals(Population population) {
        Population selectedIndividuals = new Population();
        population.sortFromWorstToBest();
        List<Double>distributionFuction = evaluateDistributionFunction(population);
        for (int i = 0; i < population.size(); i++) {
            double randomNumber = Math.random();
            for (int j = 0; j < distributionFuction.size(); j++) {
                if (randomNumber < distributionFuction.get(j)) {
                    selectedIndividuals.add(population.getIndividual(j));
                    break;
                }
            }
        }
        return selectedIndividuals;
    }
   
    private static List<Double> evaluateDistributionFunction(Population population) { 
        List<Double> probabilities = new ArrayList<>();
        int individualsFitnessSum = sumOfIndividualsFitness(population);
        double probability = 1.0 - ((double)(individualsFitnessSum - population.getIndividual(0).getFitness())/(double)individualsFitnessSum);
        probabilities.add(probability);
        for (int i = 1; i < population.size(); i++) {
            Individual currentIndividual = population.getIndividual(i);
            probability = probabilities.get(i - 1) + 1.0 - ((double)(individualsFitnessSum - currentIndividual.getFitness())/(double)individualsFitnessSum);
            probabilities.add(probability);
        }
        return probabilities;
    }
    
    private static int sumOfIndividualsFitness(Population population) {
        int fitnessSum = 0;
        for (Individual i : population) {
            fitnessSum += i.getFitness();
        }
        return fitnessSum;
    }
}
