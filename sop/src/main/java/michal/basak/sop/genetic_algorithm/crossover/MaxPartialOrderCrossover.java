package michal.basak.sop.genetic_algorithm.crossover;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.helpers.*;

public class MaxPartialOrderCrossover implements Crossover {

    private final int[][] firstParentMatrix;
    private final int[][] secondParentMatrix;
    private final int[][] matricesIntersection;
    private final Map<Integer, Integer> nodesAndNumberOfItsPredecessors = new HashMap<>();
    private final List<Integer> maxPartialOrder = new ArrayList<>();
    private final List<Integer> currentPartialOrder = new ArrayList<>();
    private final Map<Integer, Set<Integer>> obligatoryPredecessors;
    private final List<Integer> offspringChromosome = new LinkedList<>();
    private final List<Integer> firstParentCopy = new LinkedList<>();
    private final List<Integer> secondParentCopy = new LinkedList<>();
    private final Set<Integer> obligatoryPredecessorsOfPartialOrderNode = new HashSet<>();
    private final int START_NODE;
    private final int END_NODE;
    private final int MATRIX_SIZE;
    private final TwoPointCrossover twoPointCrossover = new TwoPointCrossover();

    public MaxPartialOrderCrossover(CitiesGraph citiesGraph) {
        START_NODE = citiesGraph.getStartNode();
        END_NODE = citiesGraph.getEndNode();
        MATRIX_SIZE = citiesGraph.numberOfCities();
        obligatoryPredecessors = citiesGraph.getObligatoryPredecessors();
        firstParentMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        secondParentMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        matricesIntersection = new int[MATRIX_SIZE][MATRIX_SIZE];
    }

    @Override
    public List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {
        initialize(firstParentChromosome, secondParentChromosome);
        intersectParentMatrices();
        findMaxPartialOrder();
        firstParentCopy.removeAll(maxPartialOrder);
        secondParentCopy.removeAll(maxPartialOrder);
        offspringChromosome.addAll(twoPointCrossover.makeOffspringChromosome(firstParentCopy, secondParentCopy));
        insertMaxPartialOrderElements();
        return offspringChromosome;
    }

    private void insertMaxPartialOrderElements() {
        RandomInteger randomInteger = RandomInteger.getInstance();
        int insertionIndex = offspringChromosome.size() - 1;
        int minIndex;
        int maxIndex;
        int lastNodeFromMaxPartialOrder;
        Set<Integer> obligatoryPredecessorsOfCurrentGene;
        while (maxPartialOrder.size() > 0) {
            obligatoryPredecessorsOfPartialOrderNode.clear();
            maxPartialOrder.stream().forEach((node) -> {
                obligatoryPredecessorsOfPartialOrderNode.addAll(obligatoryPredecessors.get(node));
            });
            minIndex = 1;
            maxIndex = insertionIndex;
            lastNodeFromMaxPartialOrder = maxPartialOrder.remove(maxPartialOrder.size() - 1);
            for (int i = 1; i < maxIndex; i++) {
                int currentGene = offspringChromosome.get(i);
                obligatoryPredecessorsOfCurrentGene = obligatoryPredecessors.get(currentGene);
                if (obligatoryPredecessorsOfCurrentGene.contains(lastNodeFromMaxPartialOrder)) {
                    maxIndex = i;
                    break;
                }
                if (obligatoryPredecessorsOfPartialOrderNode.contains(currentGene)) {
                    minIndex = i + 1;
                }
            }
            insertionIndex = randomInteger.getFromRange(minIndex, maxIndex + 1);
            offspringChromosome.add(insertionIndex, lastNodeFromMaxPartialOrder);
        }
    }

    private void initialize(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {
        nodesAndNumberOfItsPredecessors.clear();
        currentPartialOrder.clear();
        maxPartialOrder.clear();
        offspringChromosome.clear();
        firstParentCopy.clear();
        secondParentCopy.clear();
        firstParentCopy.addAll(firstParentChromosome);
        secondParentCopy.addAll(secondParentChromosome);
        makeMatrixRepresentation(firstParentChromosome, firstParentMatrix);
        makeMatrixRepresentation(secondParentChromosome, secondParentMatrix);
    }

    private void findMaxPartialOrder() {
        nodesAndNumberOfItsPredecessors.remove(START_NODE);
        while (nodesAndNumberOfItsPredecessors.size() > 0) {
            currentPartialOrder.clear();
            buildPartialOrder(currentPartialOrder);
            if (currentPartialOrder.size() > maxPartialOrder.size()) {
                maxPartialOrder.clear();
                maxPartialOrder.addAll(currentPartialOrder);
            } else if (currentPartialOrder.size() == maxPartialOrder.size()) {
                if (Math.random() < 0.5) {
                    maxPartialOrder.clear();
                    maxPartialOrder.addAll(currentPartialOrder);
                }
            }
        }
    }

    private void buildPartialOrder(List<Integer> currentPartialOrder) {
        int currentNode = findNodeWithFewestPredecessors();
        nodesAndNumberOfItsPredecessors.remove(currentNode);
        int minNumberOfPredecessors;
        int successorWithMinNumberOfPredecessors;
        while (currentNode != END_NODE) {
            currentPartialOrder.add(currentNode);
            minNumberOfPredecessors = Integer.MAX_VALUE;
            successorWithMinNumberOfPredecessors = END_NODE;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                if (matricesIntersection[currentNode][i] == 1) {
                    Integer predecessorsNumber = nodesAndNumberOfItsPredecessors.get(i);
                    if (predecessorsNumber != null && predecessorsNumber < minNumberOfPredecessors) {
                        minNumberOfPredecessors = predecessorsNumber;
                        successorWithMinNumberOfPredecessors = i;
                    }
                }
            }
            currentNode = successorWithMinNumberOfPredecessors;
        }
    }

    private int findNodeWithFewestPredecessors() {
        int nodeWithFewestPredecessors = END_NODE;
        int minPredecessors = MATRIX_SIZE;
        for (Integer node : nodesAndNumberOfItsPredecessors.keySet()) {
            int predecessorsNumber = nodesAndNumberOfItsPredecessors.get(node);
            if (predecessorsNumber < minPredecessors) {
                minPredecessors = predecessorsNumber;
                nodeWithFewestPredecessors = node;
            }
        }
        return nodeWithFewestPredecessors;
    }

    private void intersectParentMatrices() {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            nodesAndNumberOfItsPredecessors.put(i, 0);
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int matrixElement = firstParentMatrix[j][i] * secondParentMatrix[j][i];
                matricesIntersection[j][i] = matrixElement;
                if (matrixElement == 1) {
                    int predecessors = nodesAndNumberOfItsPredecessors.get(i);
                    predecessors++;
                    nodesAndNumberOfItsPredecessors.replace(i, predecessors);
                }
            }
        }
    }

    private void makeMatrixRepresentation(List<Integer> chromosome, int[][] matrix) {
        int currentGene;
        int currentGeneSuccesor;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < MATRIX_SIZE - 1; i++) {
            currentGene = chromosome.get(i);
            for (int j = i + 1; j < MATRIX_SIZE; j++) {
                currentGeneSuccesor = chromosome.get(j);
                matrix[currentGene][currentGeneSuccesor] = 1;
            }
        }
    }

}
