package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.Population;

public class ElitaryReplacer implements PopulationReplacer{
    int eliteSize;
    
    public ElitaryReplacer(int eliteSize) {
        this.eliteSize = eliteSize;
    }
    
    @Override
    public Population replace(Population currentPopulation, Population offspringsPopulation) {
        Population nextPopulation = new Population();
        currentPopulation.sortFromBestToWorst();
        for (int i = 0; i < eliteSize; i++) {
            nextPopulation.add(offspringsPopulation.getIndividual(i));
        }
        offspringsPopulation.sortFromBestToWorst();
        for (int i = 0; i < offspringsPopulation.size() - eliteSize; i++ ) {
            nextPopulation.add(offspringsPopulation.getIndividual(i));
        }
        return nextPopulation;        
    }
}
