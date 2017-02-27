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
        for (int i = 0; i <= population.size() / 2; i++) {
            selectedIndividuals.add(bestIndividualFromTournament(population));
        }
        return selectedIndividuals;
    }

    private Individual bestIndividualFromTournament(Population population) {
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            individualsInTournament[i] = population.getIndividual(RandomInteger.getFromRange(0, population.size()));
        }
        int bestIndividualIndex = 0;
        for (int i = 1; i < TOURNAMENT_SIZE; i++) {
            int currentIndividualCost = individualsInTournament[i].cost();
            int bestIndividualCost = individualsInTournament[bestIndividualIndex].cost();
            if (currentIndividualCost < bestIndividualCost) {
                bestIndividualIndex = i;
            } else if (currentIndividualCost == bestIndividualCost) {
                if (Math.random() < 0.5) {
                    bestIndividualIndex = i;
                }
            }
        }
        return individualsInTournament[bestIndividualIndex];
    }
}
