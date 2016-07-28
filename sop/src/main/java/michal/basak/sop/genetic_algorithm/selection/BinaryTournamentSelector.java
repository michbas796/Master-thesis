package michal.basak.sop.genetic_algorithm.selection;

import java.util.Random;
import michal.basak.sop.genetic_algorithm.Population;
import michal.basak.sop.genetic_algorithm.individuals.Individual;

public class BinaryTournamentSelector extends IndividualSelector{

    @Override
    public Population selectIndividualsFrom(Population population) {
        Population selectedIndividuals = new Population();
        for (int i = 0; i < population.size(); i++) {
            selectedIndividuals.add(bestIndividualFromTwoRandomlySelected(population));
        }
        return selectedIndividuals;        
    }
    
    private Individual bestIndividualFromTwoRandomlySelected(Population population) {
        Random random = new Random();
        int firstRandomIndex = random.nextInt(population.size());
        int secondRandomIndex = random.nextInt(population.size());
        Individual firstIndividual = population.getIndividual(firstRandomIndex);
        Individual secondIndividual = population.getIndividual(secondRandomIndex);
        if (firstIndividual.getFitness() < secondIndividual.getFitness()) {
            return firstIndividual;
        }
        return secondIndividual;
    }
    
}
