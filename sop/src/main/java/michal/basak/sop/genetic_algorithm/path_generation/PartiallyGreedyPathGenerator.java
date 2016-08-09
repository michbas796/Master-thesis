package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.CitiesGraph;
import michal.basak.sop.helpers.RandomInteger;

public class PartiallyGreedyPathGenerator extends RandomPathGenerator {

    public PartiallyGreedyPathGenerator(CitiesGraph citiesGraph) {
        super(citiesGraph);
    }

    @Override
    public List<Integer> generate() {
        path.clear();
        path.add(startNode);
        int greedyStart = RandomInteger.getInstance().getFromRange(1, pathLength - 2);
        for (int i = 1; i < greedyStart; i++) {
            path.add(randomAcceptableNode());
        }
        for (int i = greedyStart; path.size() < pathLength - 1; i++) {
            path.add(bestAcceptableNode());
        }
        path.add(endNode);
        return path;
    }

    private int bestAcceptableNode() {
        findAcceptableNodes();
        int lastNodeFromPath = path.get(path.size() - 1);
        int bestNode = acceptableNodes.get(0);
        for (int i = 1; i < acceptableNodes.size(); i++) {
            int currentNode = acceptableNodes.get(i);
            if (citiesGraph.weightOfEdge(lastNodeFromPath, currentNode) < citiesGraph.weightOfEdge(lastNodeFromPath, bestNode)) {
                bestNode = currentNode;
            }
        }
        return bestNode;
    }

}
