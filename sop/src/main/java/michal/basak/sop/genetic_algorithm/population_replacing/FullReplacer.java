package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.*;

public class FullReplacer implements PopulationReplacer {

    @Override
    public void replace(Population currentPopulation, Population offspringsPopulation) {
        currentPopulation.clear();
        currentPopulation.addAll(offspringsPopulation);
    }

}
