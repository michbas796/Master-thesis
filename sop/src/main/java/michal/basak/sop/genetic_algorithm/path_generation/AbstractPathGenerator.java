package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;

public abstract class AbstractPathGenerator implements PathGenerator {

    protected final int[] path;
    protected final CitiesGraph citiesGraph;
    protected final Map<Integer, Set<Integer>> obligatoryPredecessors;
    protected final int startNode;
    protected final int endNode;
    protected final List<Integer> acceptableNodes;
    protected final int pathLength;

    public AbstractPathGenerator(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        this.obligatoryPredecessors = citiesGraph.getObligatoryPredecessors();
        this.startNode = citiesGraph.getStartNode();
        this.endNode = citiesGraph.getEndNode();
        path = new int[citiesGraph.numberOfCities()];
        pathLength = path.length;
        acceptableNodes = new LinkedList<>();
    }

    @Override
    public abstract int[] generate();

    protected void findAcceptableNodes() {
        Set<Integer> currentNodeObligatoryPredecessors;
        boolean pathContainsAllObligatoryPredecessorsOfCurrentNode;
        acceptableNodes.clear();
        for (int i = 1; i < pathLength - 1; i++) {
            pathContainsAllObligatoryPredecessorsOfCurrentNode = true;
            if (pathNotContains(i)) {
                currentNodeObligatoryPredecessors = obligatoryPredecessors.get(i);
                for (Integer predecessor : currentNodeObligatoryPredecessors) {
                    if (pathNotContains(predecessor)) {
                        pathContainsAllObligatoryPredecessorsOfCurrentNode = false;
                        break;
                    }
                }
                if (pathContainsAllObligatoryPredecessorsOfCurrentNode) {
                    acceptableNodes.add(i);
                }
            }
        }
    }

    private boolean pathNotContains(int element) {
        for (int i = 0; i < pathLength; i++) {
            if (path[i] == element) {
                return false;
            }
        }
        return true;
    }
}
