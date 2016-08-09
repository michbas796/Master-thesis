package michal.basak.sop.genetic_algorithm;

import java.io.*;
import java.util.*;

public class CitiesGraph {

    public static final int PRECEDENCE_CONSTRAINT = -1;
    private List<List<Integer>> adjacencyMatrix;
    private List<List<Integer>> obligatoryPredecessors;
    private int startNode;
    private int endNode;
    private final int NOT_FOUND = -1;

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

    public List<Integer> getObligatoryPredecessorsOfNode(int nodeNumber) {
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
        obligatoryPredecessors = new ArrayList<>();
        for (int rowNum = 0; rowNum < adjacencyMatrix.size(); rowNum++) {
            List<Integer> row = adjacencyMatrix.get(rowNum);
            List<Integer> currentCityPrecedenceConstraints = new ArrayList<>();
            for (int colNum = 0; colNum < adjacencyMatrix.size(); colNum++) {
                if (row.get(colNum) == PRECEDENCE_CONSTRAINT) {
                    currentCityPrecedenceConstraints.add(colNum);
                }
            }
            obligatoryPredecessors.add(currentCityPrecedenceConstraints);
        }
    }

    private void findFirstAndLastNode() {
        startNode = NOT_FOUND;
        endNode = NOT_FOUND;
        int numberOfNodes = adjacencyMatrix.size();
        for (int i = 0; i < obligatoryPredecessors.size(); i++) {
            int numberOfConstraints = obligatoryPredecessors.get(i).size();
            if (numberOfConstraints == 0) {
                startNode = i;
            } else if (numberOfConstraints == numberOfNodes - 1) {
                endNode = i;
            }
            if (startNode != NOT_FOUND && endNode != NOT_FOUND) {
                break;
            }
        }
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public List<List<Integer>> getObligatoryPredecessors() {
        return obligatoryPredecessors;
    }

}
