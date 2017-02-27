package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.helpers.*;

public class RandomPathGenerator extends AbstractPathGenerator {

    public RandomPathGenerator(CitiesGraph citiesGraph) {
        super(citiesGraph);
    }

    @Override
    public int[] generate() {
        Arrays.fill(path, 0, path.length, -1);
        path[0] = startNode;
        for (int i = 1; i < path.length - 1; i++) {
            path[i] = randomAcceptableNode();
        }
        path[path.length - 1] = endNode;
        return path;
    }

    protected int randomAcceptableNode() {
        findAcceptableNodes();
        Collections.shuffle(acceptableNodes);
        if (acceptableNodes.size() > 1) {
            return acceptableNodes.get(RandomInteger.getFromRange(0, acceptableNodes.size()));
        }
        return acceptableNodes.get(0);
    }

}
