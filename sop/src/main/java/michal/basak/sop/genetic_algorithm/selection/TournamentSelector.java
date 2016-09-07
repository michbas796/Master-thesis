package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.individuals.*;
import michal.basak.sop.helpers.*;

public class TournamentSelector implements IndividualSelector {

    private final int TOURNAMENT_SIZE;
    private final Individual[] individualsInTournament;
    private final Population selectedIndividuals = new Population();

    public TournamentSelector(int tournamentSize) {
        TOURNAMENT_SIZE = tournamentSize;
        individualsInTournament = new Individual[tournamentSize];
    }

    @Override
    public Population selectIndividualsFrom(Population population) {
        selectedIndividuals.clear();
        for (int i = 0; i < population.size(); i++) {
            selectedIndividuals.add(bestIndividualFromTournament(population));
        }
        return selectedIndividuals;
    }

    private Individual bestIndividualFromTournament(Population population) {
        RandomInteger random = RandomInteger.getInstance();
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            individualsInTournament[i] = population.getIndividual(random.getFromRange(0, population.size()));
        }
        int bestIndividualIndex = 0;
        for (int i = 1; i < TOURNAMENT_SIZE; i++) {
            if (individualsInTournament[i].cost() < individualsInTournament[bestIndividualIndex].cost()) {
                bestIndividualIndex = i;
            }
        }
        return individualsInTournament[bestIndividualIndex];
    }
}
