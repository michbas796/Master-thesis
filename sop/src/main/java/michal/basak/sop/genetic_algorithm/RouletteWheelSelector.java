package michal.basak.sop.genetic_algorithm;

public class RouletteWheelSelector implements IndividualSelector {
    
    @Override
    public Population selectIndividuals(Population population) {
        Population selectedIndividuals = new Population();       
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
   
    private static double[] evaluateDistributionFunction(Population population) { 
        double[] distributionFunction = new double[population.size()];
        int individualsFitnessSum = sumOfIndividualsFitness(population);
        double probability = 1.0 - ((double)(individualsFitnessSum - population.getIndividual(0).getFitness())/(double)individualsFitnessSum);
        distributionFunction[0] = probability;
        for (int i = 1; i < population.size(); i++) {
            Individual currentIndividual = population.getIndividual(i);
            probability = distributionFunction[i - 1] + 1.0 - ((double)(individualsFitnessSum - currentIndividual.getFitness())/(double)individualsFitnessSum);
            distributionFunction[i] = probability;
        }
        return distributionFunction;
    }
    
    private static int sumOfIndividualsFitness(Population population) {
        int fitnessSum = 0;
        for (Individual i : population) {
            fitnessSum += i.getFitness();
        }
        return fitnessSum;
    }
}
