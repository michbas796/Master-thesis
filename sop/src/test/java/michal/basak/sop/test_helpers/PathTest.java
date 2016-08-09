package michal.basak.sop.test_helpers;

import java.util.List;
import michal.basak.sop.genetic_algorithm.CitiesGraph;
import static org.junit.Assert.*;

public class PathTest {

    public static void test(List<Integer> path, CitiesGraph citiesGraph) {
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = i + 1; j < path.size(); j++) {
                assertNotEquals("Nieprawidłowa kolejność węzłów: " + path.get(i) + " i " + path.get(j) + "\n" + path.toString(), -1, citiesGraph.weightOfEdge(path.get(i), path.get(j)));
            }
        }
    }

}
