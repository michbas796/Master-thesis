package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.*;

public class FullReplacer implements PopulationReplacer {

    @Override
    public Population replace(Population currentPopulation, Population offspringsPopulation) {
        return offspringsPopulation;
    }

}
