package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Population;
import michal.basak.sop.genetic_algorithm.individuals.Individual;
import michal.basak.sop.helpers.RandomInteger;

public class BinaryTournamentSelector implements IndividualSelector {

    @Override
    public Population selectIndividualsFrom(Population population) {
        Population selectedIndividuals = new Population();
        for (int i = 0; i < population.size(); i++) {
            selectedIndividuals.add(bestIndividualFromTwoRandomlySelected(population));
        }
        return selectedIndividuals;
    }

    private Individual bestIndividualFromTwoRandomlySelected(Population population) {
        RandomInteger random = RandomInteger.getInstance();
        int firstRandomIndex = random.getFromRange(0, population.size());
        int secondRandomIndex = random.getFromRange(0, population.size());
        Individual firstIndividual = population.getIndividual(firstRandomIndex);
        Individual secondIndividual = population.getIndividual(secondRandomIndex);
        if (firstIndividual.getCost() < secondIndividual.getCost()) {
            return firstIndividual;
        }
        return secondIndividual;
    }

}
