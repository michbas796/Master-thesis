package michal.basak.sop.genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CitiesGraph {
    public static final int PRECEDENCE_CONSTRAINT = -1;
    public static final int NO_EDGE = 0;
    private ArrayList<ArrayList<Integer>> adjacencyMatrix;
    
    public CitiesGraph(File inputFile) {       
        try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile))) {
            Scanner scanner;
            String currentLine;        
            while ((currentLine = fileReader.readLine()) != null) {
                scanner = new Scanner(currentLine);
                ArrayList<Integer> adjacencyMatrixRow = null;
                while (scanner.hasNextInt()) {                    
                    adjacencyMatrixRow = new ArrayList<>();
                    adjacencyMatrixRow.add(scanner.nextInt());
                }
                scanner.close();
                if (adjacencyMatrixRow != null && adjacencyMatrixRow.size() > 1) {
                    adjacencyMatrix.add(adjacencyMatrixRow);
                }           
            } 
        } catch (IOException e) {
            //TODO prawdopodobnie powinno wyżucić nowy wyjątek.
        }
    }
    
    public int getEdgeWeight(int startNode, int endNode) {
        return adjacencyMatrix.get(startNode).get(endNode);
    }
    
    public int[] getRandomHamiltonianPath() {
        //TODO
        return new int[10];
    }
        
}
