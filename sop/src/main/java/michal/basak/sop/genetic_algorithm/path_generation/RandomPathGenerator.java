package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.helpers.*;

public class RandomPathGenerator extends AbstractPathGenerator {

    public RandomPathGenerator(CitiesGraph citiesGraph) {
        super(citiesGraph);
    }

    @Override
    public List<Integer> generate() {
        path.clear();
        path.add(startNode);
        while (path.size() < pathLength - 1) {
            path.add(randomAcceptableNode());
        }
        path.add(endNode);
        return path;
    }

    protected int randomAcceptableNode() {
        findAcceptableNodes();
        Collections.shuffle(acceptableNodes);
        RandomInteger random = RandomInteger.getInstance();
        if (acceptableNodes.size() > 1) {
            return acceptableNodes.get(random.getFromRange(0, acceptableNodes.size()));
        }
        return acceptableNodes.get(0);
    }

}
