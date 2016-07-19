package michal.basak.sop.genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class CitiesGraph {
    public static final int PRECEDENCE_CONSTRAINT = -1;
    public static final int NO_EDGE = 0;
    private List<List<Integer>> adjacencyMatrix;
    private List<Set<Integer>> precedenceConstraints;
    
    public CitiesGraph(File inputFile) {       
        loadAdjacencyMatrixFromFile(inputFile);
        assignPrecedenceConstraints();
    }
    
    public int getEdgeWeight(int startNode, int endNode) {
        return adjacencyMatrix.get(startNode).get(endNode);
    }
    
    public int[] getRandomHamiltonianPath(int startNode, int endNode) {
        //TODO do przemyślenia sposób losowania i sprawdzania poprawności tworzenia ścieżki
        int numberOfNodes = adjacencyMatrix.size();
        ArrayList<Integer> nodesList = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            if (i == startNode || i == endNode) {
                continue;
            }
            nodesList.add(i);
        }
        int[] path = new int[numberOfNodes];
        path[0] = startNode;
        int previousNode = startNode;
        for (int i = 1; i < numberOfNodes - 1; i++) {
            int randomNode;
            do {            
                randomNode = getRandomNode(nodesList);
            } while (precedenceConstraints.get(previousNode).contains(randomNode));
            path[i] = randomNode;
        }
        path[numberOfNodes-1] = endNode;
        return path;
    }
    
    public int getRandomNode(List<Integer> nodes) {
        Random random = new Random();
        int nodeIndex = random.nextInt(nodes.size());
        int result = nodes.get(nodeIndex);
        nodes.remove(nodeIndex);
        return result;       
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
            Set<Integer> currentCityPrecedenceConstraints = new HashSet<>();
            for (int colNum = 0; colNum < adjacencyMatrix.size(); colNum++) {
                if (row.get(colNum) == PRECEDENCE_CONSTRAINT) {
                    currentCityPrecedenceConstraints.add(colNum);
                }
            }
            precedenceConstraints.add(currentCityPrecedenceConstraints);
        }
    }
               
}
