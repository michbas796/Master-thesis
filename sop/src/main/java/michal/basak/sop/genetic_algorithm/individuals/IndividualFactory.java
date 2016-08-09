package michal.basak.sop.genetic_algorithm.individuals;

import michal.basak.sop.genetic_algorithm.CitiesGraph;
import michal.basak.sop.genetic_algorithm.path_generation.PathGenerator;

public class IndividualFactory {

    public Individual createIndividual(CitiesGraph citiesGraph, PathGenerator pathGenerator) {
        return new Individual(citiesGraph, pathGenerator);
    }

}
