package michal.basak.sop.genetic_algorithm.population_replacing;

import michal.basak.sop.genetic_algorithm.Population;

public interface PopulationReplacer {
    Population replace(Population currentPopulation, Population offspringsPopulation);
}
