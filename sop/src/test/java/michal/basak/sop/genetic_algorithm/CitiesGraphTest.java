/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michal.basak.sop.genetic_algorithm;

import java.io.File;
import java.net.URL;
import java.util.List;
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
public class CitiesGraphTest {
    
    static CitiesGraph citiesGraph;
    
    public CitiesGraphTest() {
        
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of weightOfEdge method, of class CitiesGraph.
     */
    /*
    @org.junit.Test
    public void testWeightOfEdge() {
        System.out.println("weightOfEdge");
        int startNode = 0;
        int endNode = 0;
        CitiesGraph instance = null;
        int expResult = 0;
        int result = instance.weightOfEdge(startNode, endNode);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    *

    /**
     * Test of getRandomHamiltonianPath method, of class CitiesGraph.
     */
    @org.junit.Test
    public void testGetRandomHamiltonianPath() {
        System.out.println("getRandomHamiltonianPath");             
        List<Integer> path = citiesGraph.getRandomHamiltonianPath();
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = 1; j < path.size(); j++) {
                assertNotEquals("Nieprawidłowa kolejność węzłów: " + path.get(i) + " i " + path.get(j), -1, citiesGraph.weightOfEdge(path.get(i), path.get(j)));                
            }
        }             
    }

    /**
     * Test of getEmptyPath method, of class CitiesGraph.
     */
    @org.junit.Test
    public void testGetEmptyPath() {
        System.out.println("getEmptyPath");               
        List<Integer> result = citiesGraph.getEmptyPath();
        assertNotEquals(null, result);
        assertEquals(0, result.size());       
    }

    /**
     * Test of numberOfCities method, of class CitiesGraph.
     */
    /*
    @org.junit.Test
    public void testNumberOfCities() {
        System.out.println("numberOfCities");
        CitiesGraph instance = null;
        int expResult = 0;
        int result = instance.numberOfCities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    /**
     * Test of getObligatoryPredecessorsOfNode method, of class CitiesGraph.
     */
    /*
    @org.junit.Test
    public void testGetObligatoryPredecessorsOfNode() {
        System.out.println("getObligatoryPredecessorsOfNode");
        int nodeNumber = 0;
        CitiesGraph instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.getObligatoryPredecessorsOfNode(nodeNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
}
