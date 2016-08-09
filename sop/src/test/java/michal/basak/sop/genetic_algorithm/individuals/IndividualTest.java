package michal.basak.sop.genetic_algorithm.individuals;

import java.io.File;
import java.net.URL;
import michal.basak.sop.genetic_algorithm.CitiesGraph;
import michal.basak.sop.genetic_algorithm.path_generation.PathGenerator;
import michal.basak.sop.genetic_algorithm.path_generation.RandomPathGenerator;
import michal.basak.sop.test_helpers.PathTest;
import org.junit.Before;
import org.junit.Test;

public class IndividualTest {
    
    CitiesGraph citiesGraph;
    PathGenerator pathGenerator;
    
    public IndividualTest() {
    }
        
            
    @Before
    public void setUp() {
        URL url = this.getClass().getResource("br17.10.sop");
        File testFile = new File(url.getFile());
        citiesGraph = new CitiesGraph(testFile);
        pathGenerator = new RandomPathGenerator(citiesGraph);
    }
            
    /**
     * Test of crossoverWith method, of class Individual.
     */
    @Test
    public void testCrossoverWith() {
        for (int i = 0; i < 1000; i++) {
            Individual firstParent = new Individual(citiesGraph, pathGenerator);
            Individual secondParent = new Individual(citiesGraph, pathGenerator);
            Individual.Offsprings expResult = null;
            Individual.Offsprings result = firstParent.crossoverWith(secondParent);
            PathTest.test(result.getFirst().getChromosome(), citiesGraph);
            PathTest.test(result.getSecond().getChromosome(), citiesGraph);
        }        
    }

    /*
    /**
     * Test of mutate method, of class Individual.
     */   
    @Test
    public void testMutate() {
        for (int i = 0; i < 1000; i++) {
            double mutationProbability = 1.0;
            Individual instance = new Individual(citiesGraph, pathGenerator);
            instance.mutate(mutationProbability);
            PathTest.test(instance.getChromosome(), citiesGraph);
        }
    }    
    
}
