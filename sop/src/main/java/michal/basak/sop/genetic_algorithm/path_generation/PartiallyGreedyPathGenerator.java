package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.helpers.*;

public class PartiallyGreedyPathGenerator extends RandomPathGenerator {

    int lastAddedElementIndex;

    public PartiallyGreedyPathGenerator(CitiesGraph citiesGraph) {
        super(citiesGraph);
    }

    @Override
    public int[] generate() {
        Arrays.fill(path, 0, path.length, -1);
        path[0] = startNode;
        int greedyStart = RandomInteger.getFromRange(1, pathLength - 2);
        lastAddedElementIndex = 0;
        for (int i = 1; i < greedyStart; i++) {
            path[i] = randomAcceptableNode();
            lastAddedElementIndex = i;
        }
        for (int i = greedyStart; i < pathLength - 1; i++) {
            path[i] = bestAcceptableNode();
            lastAddedElementIndex = i;
        }
        path[pathLength - 1] = endNode;
        return path;
    }

    private int bestAcceptableNode() {
        findAcceptableNodes();
        int lastNodeFromPath = path[lastAddedElementIndex];
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
