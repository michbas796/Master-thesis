package michal.basak.sop.genetic_algorithm;

public class CitiesGraph {
    public static int PRECEDENCE_CONSTRAINT = -1;
    public static int NO_EDGE = 0;
    private Integer[][] adjacencyMatrix;
    
    public CitiesGraph() {
        //TODO
    }
    
    public int getEdgeWeight(int startNode, int endNode) {
        return adjacencyMatrix[startNode][endNode];
    }
    
    public int[] getRandomHamiltonianPath() {
        //TODO
        return new int[10];
    }
    
    
    
}
