package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;

public class IndividualFactory {

    private final CitiesGraph citiesGraph;
    private final PathGenerator pathGenerator;

    public IndividualFactory(CitiesGraph citiesGraph, PathGenerator pathGenerator) {
        this.citiesGraph = citiesGraph;
        this.pathGenerator = pathGenerator;
    }

    public Individual createIndividual() {
        return new Individual(citiesGraph, pathGenerator.generate());
    }

    public Individual createIndividual(List<Integer> chromosome) {
        return new Individual(citiesGraph, chromosome);
    }

}
