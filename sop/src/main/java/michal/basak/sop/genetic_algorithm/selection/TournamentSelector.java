package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.individuals.*;
import michal.basak.sop.helpers.*;

public class TournamentSelector implements IndividualSelector {

    private int tournamentSize;
    private Individual[] individualsInTournament;

    public TournamentSelector(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        individualsInTournament = new Individual[tournamentSize];
    }

    @Override
    public void selectIndividuals(Population population, Population selectedIndividuals) {
        selectedIndividuals.clear();
        for (int i = 0; i < population.size(); i++) {
            selectedIndividuals.add(bestIndividualFromTournament(population));
        }
    }

    private Individual bestIndividualFromTournament(Population population) {
        RandomInteger random = RandomInteger.getInstance();
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
