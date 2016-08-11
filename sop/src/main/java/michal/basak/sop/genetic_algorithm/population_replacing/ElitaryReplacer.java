package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.*;

public class ElitaryReplacer implements PopulationReplacer {

    int eliteSize;
    Population nextPopulation = new Population();

    public ElitaryReplacer(int eliteSize) {
        this.eliteSize = eliteSize;
    }

    @Override
    public void replace(Population currentPopulation, Population offspringsPopulation) {
        nextPopulation.clear();
        currentPopulation.sortFromBestToWorst();
        for (int i = 0; i < eliteSize; i++) {
            nextPopulation.add(offspringsPopulation.getIndividual(i));
        }
        offspringsPopulation.sortFromBestToWorst();
        for (int i = 0; i < offspringsPopulation.size() - eliteSize; i++) {
            nextPopulation.add(offspringsPopulation.getIndividual(i));
        }
    }
}
