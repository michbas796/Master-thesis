package michal.basak.sop.genetic_algorithm;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CitiesGraph {

    public static final int PRECEDENCE_CONSTRAINT = -1;
    private List<List<Integer>> adjacencyMatrix;
    private Map<Integer, Set<Integer>> obligatoryPredecessors;
    private int startNode;
    private int endNode;
    private final int NODE_NOT_FOUND = -1;

    public CitiesGraph(File inputFile) {
        loadAdjacencyMatrixFromFile(inputFile);
        assignObligatoryPredecessors();
        findFirstAndLastNode();
    }

    public int weightOfEdge(int startNode, int endNode) {
        return adjacencyMatrix.get(startNode).get(endNode);
    }

    public int numberOfCities() {
        return adjacencyMatrix.size();
    }

    public Set<Integer> getObligatoryPredecessorsOfNode(int nodeNumber) {
        return obligatoryPredecessors.get(nodeNumber);
    }

    private void loadAdjacencyMatrixFromFile(File inputFile) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            adjacencyMatrix = new ArrayList<>();
            while ((currentLine = fileReader.readLine()) != null) {
                parseFileLine(currentLine);
            }
        } catch (IOException e) {
            //TODO prawdopodobnie powinno wyrzucić nowy wyjątek.
        }
    }

    private void parseFileLine(String fileLine) {
        ArrayList<Integer> adjacencyMatrixRow = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileLine)) {
            while (scanner.hasNextInt()) {
                adjacencyMatrixRow.add(scanner.nextInt());
            }
        }
        if (adjacencyMatrixRow.size() > 1) {
            adjacencyMatrix.add(adjacencyMatrixRow);
        }
    }

    private void assignObligatoryPredecessors() {
        obligatoryPredecessors = new HashMap<>();
        for (int rowNum = 0; rowNum < adjacencyMatrix.size(); rowNum++) {
            List<Integer> row = adjacencyMatrix.get(rowNum);
            Set<Integer> currentCityPrecedenceConstraints = new HashSet<>();
            IntStream.range(0, adjacencyMatrix.size())
                    .filter(i -> row.get(i) == PRECEDENCE_CONSTRAINT)
                    .forEach(currentCityPrecedenceConstraints::add);
            obligatoryPredecessors.put(rowNum, currentCityPrecedenceConstraints);
        }
    }

    private void findFirstAndLastNode() {
        startNode = obligatoryPredecessors.entrySet().stream()
                .filter(nodesToPredecessorsSetMap -> nodesToPredecessorsSetMap.getValue().isEmpty())
                .map(nodesToPredecessorsSetMap -> nodesToPredecessorsSetMap.getKey())
                .findFirst().orElse(NODE_NOT_FOUND);
        endNode = obligatoryPredecessors.entrySet().stream()
                .filter(nodesToPredecessorsSetMap -> nodesToPredecessorsSetMap.getValue().size() == numberOfCities() - 1)
                .map(nodesToPredecessorsSetMap -> nodesToPredecessorsSetMap.getKey())
                .findFirst().orElse(NODE_NOT_FOUND);
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public Map<Integer, Set<Integer>> getObligatoryPredecessors() {
        return obligatoryPredecessors;
    }

}
