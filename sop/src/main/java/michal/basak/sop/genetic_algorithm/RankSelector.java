package michal.basak.sop.genetic_algorithm;

import java.util.Random;
import org.omg.CORBA.portable.IndirectionException;

public class RankSelector implements IndividualSelector {
        
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
    
    private double[] evaluateSelectionProbabilities(Population population) {
        int populationSize = population.size();
        double[] selectionProbabilities = new double[populationSize];        
        for (int i = 0; i < populationSize; i++) {
            selectionProbabilities[i] = (double)i / populationSize;
        }
        return selectionProbabilities;
    }
    
    private double[] evaluateDistributionFunction(Population population) {
        distributionFunction = new double[population.size()];
        double[] selectionProbability = evaluateSelectionProbabilities(population);
        distributionFunction[0] = selectionProbability[0];
        for (int i = 1; i < population.size(); i++) {
            distributionFunction[i] = distributionFunction[i - 1] + selectionProbability[i];
        }
        return distributionFunction;
    }
            
}
