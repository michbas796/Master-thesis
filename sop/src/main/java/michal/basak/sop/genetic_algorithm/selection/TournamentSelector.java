package michal.basak.sop.genetic_algorithm.selection;

import java.util.Random;
import michal.basak.sop.genetic_algorithm.Individual;
import michal.basak.sop.genetic_algorithm.Population;

public class TournamentSelector extends IndividualSelector {
    
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
        Random random = new Random();       
        Individual[] individualsInTournament = new Individual[tournamentSize];
        for (int i = 0; i < tournamentSize; i++) {
            individualsInTournament[i] = population.getIndividual(random.nextInt(population.size()));
        }
        int bestIndividualIndex = 0;
        for (int i = 1; i < tournamentSize; i++) {
            if (individualsInTournament[i].getFitness() < individualsInTournament[bestIndividualIndex].getFitness()) {
                bestIndividualIndex = i;
            }
        }
        return individualsInTournament[bestIndividualIndex];
    }
}
