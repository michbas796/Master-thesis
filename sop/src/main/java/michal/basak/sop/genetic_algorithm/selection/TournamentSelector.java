package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Population;
import michal.basak.sop.genetic_algorithm.individuals.Individual;
import michal.basak.sop.helpers.RandomInteger;

public class TournamentSelector implements IndividualSelector {

    int tournamentSize;

    public TournamentSelector(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Population selectIndividualsFrom(Population population) {
        Population selectedIndividuals = new Population();
        for (int i = 0; i < population.size(); i++) {
            selectedIndividuals.add(bestIndividualFromTournament(population));
        }
        return selectedIndividuals;
    }

    private Individual bestIndividualFromTournament(Population population) {
        RandomInteger random = RandomInteger.getInstance();
        Individual[] individualsInTournament = new Individual[tournamentSize];
        for (int i = 0; i < tournamentSize; i++) {
            individualsInTournament[i] = population.getIndividual(random.getFromRange(0, population.size()));
        }
        int bestIndividualIndex = 0;
        for (int i = 1; i < tournamentSize; i++) {
            if (individualsInTournament[i].getCost() < individualsInTournament[bestIndividualIndex].getCost()) {
                bestIndividualIndex = i;
            }
        }
        return individualsInTournament[bestIndividualIndex];
    }
}
