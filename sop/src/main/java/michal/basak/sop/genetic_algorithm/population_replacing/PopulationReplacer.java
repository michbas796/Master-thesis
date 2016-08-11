package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.*;

public interface PopulationReplacer {

    void replace(Population currentPopulation, Population offspringsPopulation);
}
