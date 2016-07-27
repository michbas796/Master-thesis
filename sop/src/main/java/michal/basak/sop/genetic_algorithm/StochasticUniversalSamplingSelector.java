package michal.basak.sop.genetic_algorithm;

import java.util.Random;
import static michal.basak.sop.genetic_algorithm.Population.totalFitnessOf;

public class StochasticUniversalSamplingSelector implements IndividualSelector {
    
    double[] selectionProbability;
    int populationSize;
    
    @Override
    public Population selectIndividualsFrom(Population population) {        
        Population selectedIndividuals = new Population();
        populationSize = population.size();
        evaluateSelectionProbabilities(population);
        int pointer = new Random().nextInt();
        int sum = 0;        
        for (int i = 0; i < population.size(); i++) {
            sum += expectedCopiesNumberOfIndividual(i);
            while (sum > pointer) {                
                selectedIndividuals.add(population.getIndividual(i));
                pointer++;
            }
        }        
        return selectedIndividuals;
    }
    
    private double expectedCopiesNumberOfIndividual(int individual) {
        return populationSize * selectionProbability[individual];
    }
    
    private void evaluateSelectionProbabilities(Population population) {
        selectionProbability = new double[populationSize];
        int totalFitness = totalFitnessOf(population);        
        for (int i = 1; i < populationSize; i++) {
            selectionProbability[i] = 1.0 - ((double)(totalFitness - population.getIndividual(0).getFitness())/(double)totalFitness);
        }        
    }
}
