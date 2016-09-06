package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.individuals.*;
import michal.basak.sop.helpers.*;

public class TournamentSelector implements IndividualSelector {

    private int tournamentSize;
    private Individual[] individualsInTournament;
    private Population selectedIndividuals = new Population();

    public TournamentSelector(int tournamentSize) {
        this.tournamentSize = tournamentSize;
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
        for (int i = 0; i < tournamentSize; i++) {
            individualsInTournament[i] = population.getIndividual(random.getFromRange(0, population.size()));
        }
        int bestIndividualIndex = 0;
        for (int i = 1; i < tournamentSize; i++) {
            if (individualsInTournament[i].cost() < individualsInTournament[bestIndividualIndex].cost()) {
                bestIndividualIndex = i;
            }
        }
        return individualsInTournament[bestIndividualIndex];
    }
}
