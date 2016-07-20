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
    private List<List<Integer>> acceptableNextNodes;
    
    public CitiesGraph(File inputFile) {       
        loadAdjacencyMatrixFromFile(inputFile);
        assignAcceptableNextNodes();
    }
    
    public int getEdgeWeight(int startNode, int endNode) {
        return adjacencyMatrix.get(startNode).get(endNode);
    }
    
    public List<Integer> getRandomHamiltonianPath() {        
        HamiltonianPath path = new HamiltonianPath();        
        return path.generate();
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
      
    private void assignAcceptableNextNodes() {
        acceptableNextNodes = new ArrayList<>();
        for (int rowNum = 0; rowNum < adjacencyMatrix.size(); rowNum++) {
            List<Integer> row = adjacencyMatrix.get(rowNum);
            List<Integer> currentCityAcceptableFollowers = new ArrayList<>();
            for (int colNum = 0; colNum < adjacencyMatrix.size(); colNum++) {
                if (row.get(colNum) != PRECEDENCE_CONSTRAINT) {
                    currentCityAcceptableFollowers.add(colNum);
                }
            }
            acceptableNextNodes.add(currentCityAcceptableFollowers);
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
            findFirsAndLastNode();            
        }
        
        public List<Integer> generate() {
            path.add(startNode);
            int previousNode = startNode;
            int addedNodes = 1;
            while (addedNodes < numberOfNodes - 1) {
                int randomNode = getRandomNode(acceptableNextNodes.get(previousNode));
                if (isAcceptable(randomNode)) {
                    path.add(randomNode);  
                    addedNodes++;            
                }
            }
            path.add(endNode);
            return path;
        }
        
        private void findFirsAndLastNode() {
            startNode = NOT_FOUND;
            endNode = NOT_FOUND;        
            for (int i = 0; i < acceptableNextNodes.size(); i++) {
                int numberOfNextNodes = acceptableNextNodes.get(i).size();
                if (numberOfNextNodes == 0) {
                    endNode = i;               
                } else if (numberOfNextNodes == numberOfNodes - 1) {
                    startNode = i;
                }
                if (startNode != NOT_FOUND && endNode != NOT_FOUND) {
                    break;
                }
            }
        }
        
        private boolean isAcceptable(int newNode) {
            for (int node : path) {
                if (!acceptableNextNodes.get(node).contains(newNode)) {
                    return false;
                }               
            }
            return true;
        }
        
        private int getRandomNode(List<Integer> nodes) {
            Random random = new Random();
            int nodeIndex = random.nextInt(nodes.size());
            int result = nodes.get(nodeIndex);        
            return result;       
        }
                
    }
               
}
