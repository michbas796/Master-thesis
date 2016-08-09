package michal.basak.sop.genetic_algorithm;

import java.io.File;
import java.net.URL;
import java.util.List;
import michal.basak.sop.genetic_algorithm.individuals.Individual;
import michal.basak.sop.genetic_algorithm.path_generation.PartiallyGreedyPathGenerator;
import michal.basak.sop.genetic_algorithm.path_generation.RandomPathGenerator;
import michal.basak.sop.genetic_algorithm.selection.BinaryTournamentSelector;
import michal.basak.sop.genetic_algorithm.selection.TournamentSelector;
import michal.basak.sop.test_helpers.PathTest;
import org.junit.Before;
import org.junit.Test;

public class GeneticAlgorithmTest {
    
    CitiesGraph citiesGraph;
    GeneticAlgorithm geneticAlgorithm;
    
    public GeneticAlgorithmTest() {
    }
            
    @Before
    public void setUp() {
        URL url = this.getClass().getResource("br17.10.sop");
        File testFile = new File(url.getFile());
        citiesGraph = new CitiesGraph(testFile);         
        GeneticAlgorithmParams params = new GeneticAlgorithmParams(citiesGraph);
        geneticAlgorithm = new GeneticAlgorithm(params);
        params.setSelector(new TournamentSelector(2));
        params.setPathGenerator(new RandomPathGenerator(citiesGraph));
        params.setMaxNumberOfGenerations(100);
        params.setPopulationSize(10000);
        params.setMutationProbability(0.9999);
        geneticAlgorithm.setParams(params);
    }
        
    /**
     * Test of call method, of class GeneticAlgorithm.
     */
    @Test
    public void testCall() {               
        GeneticAlgorithm.Results result = geneticAlgorithm.call();
        Individual foundIndividual = result.getBestIndividual();
        List<Integer> path = foundIndividual.getChromosome();
        PathTest.test(path, citiesGraph);
    }
        
}
