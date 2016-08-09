package michal.basak.sop.genetic_algorithm.path_generation;

import java.io.File;
import java.net.URL;
import java.util.List;
import michal.basak.sop.genetic_algorithm.CitiesGraph;
import michal.basak.sop.test_helpers.PathTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RandomPathGeneratorTest {
    
    RandomPathGenerator generator;
    CitiesGraph citiesGraph;
    
    public RandomPathGeneratorTest() {
    }
           
    @Before
    public void setUp() {
        URL url = this.getClass().getResource("br17.10.sop");
        File testFile = new File(url.getFile());
        citiesGraph = new CitiesGraph(testFile);
        generator = new PartiallyGreedyPathGenerator(citiesGraph);
    }

    /**
     * Test of generate method, of class RandomPathGenerator.
     */
    @Test
    public void testGenerate() {
        List<Integer> path = generator.generate();
        PathTest.test(path, citiesGraph);
    }

    /**
     * Test of randomAcceptableNode method, of class RandomPathGenerator.
     */
    @Test
    public void testRandomAcceptableNode() {
        //TODO
    }
    
}
