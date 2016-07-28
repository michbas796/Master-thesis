
package michal.basak.sop.genetic_algorithm.individuals;

import michal.basak.sop.genetic_algorithm.CitiesGraph;

public class IndividualFactory {

    public Individual createIndividual(CitiesGraph citiesGraph) {
        return new Individual(citiesGraph);
    }
        
}
