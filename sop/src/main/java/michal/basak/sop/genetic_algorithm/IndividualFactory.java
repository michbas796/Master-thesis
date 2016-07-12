
package michal.basak.sop.genetic_algorithm;

public class IndividualFactory {

    public Individual createIndividual(CitiesGraph citiesGraph) {
        return new Individual(citiesGraph);
    }
        
}
