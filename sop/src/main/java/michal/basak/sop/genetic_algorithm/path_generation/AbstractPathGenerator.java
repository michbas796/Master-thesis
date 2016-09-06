package michal.basak.sop.genetic_algorithm.path_generation;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;

public abstract class AbstractPathGenerator implements PathGenerator {

    protected final List<Integer> path;
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
        path = new LinkedList<>();
        acceptableNodes = new LinkedList<>();
        pathLength = obligatoryPredecessors.get(endNode).size() + 1;
    }

    public abstract List<Integer> generate();

    protected void findAcceptableNodes() {
        Set<Integer> currentNodeObligatoryPredecessors;
        boolean pathContainsAllObligatoryPredecessorsOfCurrentNode;
        acceptableNodes.clear();
        for (int i = 1; i < pathLength - 1; i++) {
            pathContainsAllObligatoryPredecessorsOfCurrentNode = true;
            if (!path.contains(i)) {
                currentNodeObligatoryPredecessors = obligatoryPredecessors.get(i);
                for (Integer predecessor : currentNodeObligatoryPredecessors) {
                    if (!path.contains(predecessor)) {
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

}
