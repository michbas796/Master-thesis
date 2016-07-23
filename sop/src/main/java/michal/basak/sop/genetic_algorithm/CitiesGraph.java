package michal.basak.sop.genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CitiesGraph {
    public static final int PRECEDENCE_CONSTRAINT = -1;    
    private List<List<Integer>> adjacencyMatrix;
    private List<List<Integer>> precedenceConstraints; 
    
    public CitiesGraph(File inputFile) {       
        loadAdjacencyMatrixFromFile(inputFile);
        assignPrecedenceConstraints();
    }
    
    public int getEdgeWeight(int startNode, int endNode) {
        return adjacencyMatrix.get(startNode).get(endNode);
    }
    
    public List<Integer> getRandomHamiltonianPath() {        
        return new HamiltonianPath().generate();
    }
    
    public List<Integer> getEmptyPath() {
        return new HamiltonianPath().emptyPath();
    }
        
    public int numberOfCities() {
        return adjacencyMatrix.size();
    }
    
    private void loadAdjacencyMatrixFromFile(File inputFile) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile))) {            
            String currentLine;               
            adjacencyMatrix = new ArrayList<>();
            while ((currentLine = fileReader.readLine()) != null) {
                parseFileLine(currentLine);                         
            }            
        } catch (IOException e) {
            //TODO prawdopodobnie powinno wyżucić nowy wyjątek.
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
      
     private void assignPrecedenceConstraints() {         
        precedenceConstraints = new ArrayList<>();
        for (int rowNum = 0; rowNum < adjacencyMatrix.size(); rowNum++) {
            List<Integer> row = adjacencyMatrix.get(rowNum);
            List<Integer> currentCityPrecedenceConstraints = new ArrayList<>();
            for (int colNum = 0; colNum < adjacencyMatrix.size(); colNum++) {
                if (row.get(colNum) == PRECEDENCE_CONSTRAINT) {
                    currentCityPrecedenceConstraints.add(colNum);
                }
            }
            precedenceConstraints.add(currentCityPrecedenceConstraints);
        }
    }
      
    private class HamiltonianPath {
        private final List<Integer> path;
        private final int numberOfNodes;
        private int startNode;
        private int endNode;
        
        private final int NOT_FOUND = -1;
        
        public HamiltonianPath() {
            path = new LinkedList<>();
            numberOfNodes = adjacencyMatrix.size();                        
        }
        
        public List<Integer> generate() { 
            findFirstAndLastNode();
            path.add(startNode);
            addRandomNodesToPath();
            path.add(endNode);
            repairRandomPath();
            return path;
        }
        
        public List<Integer> emptyPath() {
            path.clear();
            return path;
        }
        
        private void addRandomNodesToPath() {
            List<Integer> nodesToAdd = new ArrayList<>();
            nodesToAdd.addAll(precedenceConstraints.get(endNode));            
            nodesToAdd.remove((Integer)startNode); //TODO: do przemyślenia
            while (nodesToAdd.size() > 0) {
                path.add(takeRandomNodeFrom(nodesToAdd));
            }
        }
        
        private void repairRandomPath() {
            for (int currentNodeIndex = 1; currentNodeIndex < path.size() - 1; currentNodeIndex++) {
                int currentNode = path.get(currentNodeIndex);
                int successorIndex = currentNodeIndex + 1;
                while (successorIndex < path.size()) {
                    int successor = path.get(successorIndex);
                    if (precedenceConstraints.get(currentNode).contains(successor)) {
                        int tmp = path.set(currentNodeIndex, successor);
                        path.set(successorIndex, tmp);
                        currentNode = successor;
                        continue;
                    }
                    successorIndex++;
                }
            }
        }
        
        private void findFirstAndLastNode() {
            startNode = NOT_FOUND;
            endNode = NOT_FOUND;        
            for (int i = 0; i < precedenceConstraints.size(); i++) {
                int numberOfConstraints = precedenceConstraints.get(i).size();
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
                       
        private int takeRandomNodeFrom(List<Integer> nodes) {
            Random random = new Random();
            int nodeIndex = random.nextInt(nodes.size());            
            int result = nodes.get(nodeIndex); 
            nodes.remove(nodeIndex);
            return result;       
        }
                
    }
               
}
