package michal.basak.sop.genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        int numberOfNodes = adjacencyMatrix.size();
        //TODO
        return new int[10];
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
