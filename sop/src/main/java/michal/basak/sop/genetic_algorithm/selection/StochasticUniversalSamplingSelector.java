package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Population;
import static michal.basak.sop.genetic_algorithm.Population.totalFitnessOf;

public class StochasticUniversalSamplingSelector extends IndividualSelector {
    
    double[] selectionProbability;
    int populationSize;
    int totalFitnessOfPopulation;
    
    @Override
    public Population selectIndividualsFrom(Population population) {        
        Population selectedIndividuals = new Population();
        populationSize = population.size();        
        population.shuffle();             
        double distanceBetweenPointers = 1.0 / populationSize;
        double pointer = Math.random() * distanceBetweenPointers ;
        double[] distributionFunction = evaluateDistributionFunction(population);               
        for (int i = 0; i < populationSize; i++) {           
            while (distributionFunction[i] > pointer) {                
                selectedIndividuals.add(population.getIndividual(i));
                pointer += distanceBetweenPointers;
            }
        }        
        return selectedIndividuals;
    }
    
}
