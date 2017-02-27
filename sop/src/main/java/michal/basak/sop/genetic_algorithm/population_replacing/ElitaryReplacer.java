package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.*;

public class ElitaryReplacer implements PopulationReplacer {

    private final int ELITE_SIZE;
    private final Population nextPopulation = new Population();

    public ElitaryReplacer(int eliteSize) {
        this.ELITE_SIZE = eliteSize;
    }

    @Override
    public Population replace(Population currentPopulation, Population offspringsPopulation) {
        nextPopulation.clear();
        currentPopulation.sortFromBestToWorst();
        for (int i = 0; i < ELITE_SIZE; i++) {
            nextPopulation.add(currentPopulation.getIndividual(i));
        }
        offspringsPopulation.sortFromBestToWorst();
        for (int i = 0; i < offspringsPopulation.size() - ELITE_SIZE; i++) {
            nextPopulation.add(offspringsPopulation.getIndividual(i));
        }
        return nextPopulation;
    }
}
