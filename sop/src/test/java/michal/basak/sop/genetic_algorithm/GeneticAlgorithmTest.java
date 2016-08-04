/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michal.basak.sop.genetic_algorithm;

import java.io.File;
import java.net.URL;
import java.util.List;
import static michal.basak.sop.genetic_algorithm.CitiesGraphTest.citiesGraph;
import michal.basak.sop.genetic_algorithm.individuals.Individual;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał
 */
public class GeneticAlgorithmTest {
    
    CitiesGraph citiesGraph;
    GeneticAlgorithm geneticAlgorithm;
    
    public GeneticAlgorithmTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        URL url = this.getClass().getResource("br17.10.sop");
        File testFile = new File(url.getFile());
        citiesGraph = new CitiesGraph(testFile);
        geneticAlgorithm = new GeneticAlgorithm(citiesGraph);       
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of call method, of class GeneticAlgorithm.
     */
    @Test
    public void testCall() {               
        GeneticAlgorithm.Results result = geneticAlgorithm.call();
        Individual foundIndividual = result.getBestIndividual();
        List<Integer> path = foundIndividual.getChrmosome();
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = i + 1; j < path.size(); j++) {
                assertNotEquals("Nieprawidłowa kolejność węzłów: " + path.get(i) + " i " + path.get(j) + "\n" + path.toString(), -1, citiesGraph.weightOfEdge(path.get(i), path.get(j)));
            }
        }        
    }

    /**
     * Test of getParams method, of class GeneticAlgorithm.
     */
    /*
    @Test
    public void testGetParams() {
        System.out.println("getParams");
        GeneticAlgorithm instance = null;
        GeneticAlgorithmParams expResult = null;
        GeneticAlgorithmParams result = instance.getParams();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    /**
     * Test of setParams method, of class GeneticAlgorithm.
     */
    /*
    @Test
    public void testSetParams() {
        System.out.println("setParams");
        GeneticAlgorithmParams params = null;
        GeneticAlgorithm instance = null;
        instance.setParams(params);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    
}
