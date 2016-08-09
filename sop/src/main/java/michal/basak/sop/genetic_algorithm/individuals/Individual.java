package michal.basak.sop.genetic_algorithm.individuals;

import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;
import michal.basak.sop.helpers.*;

public class Individual {

    private int cost;
    private final List<Integer> chromosome;
    private static CitiesGraph citiesGraph;

    public Individual(CitiesGraph citiesGraph, PathGenerator pathGenerator) {
        if (Individual.citiesGraph == null) {
            Individual.citiesGraph = citiesGraph;
        }
        chromosome = pathGenerator.generate();
        evaluateCost();
    }

    private Individual(List<Integer> chromosome) {
        this.chromosome = chromosome;
        evaluateCost();
    }

    private void evaluateCost() {
        cost = 0;
        for (int i = 0; i < chromosome.size() - 1; i++) {
            int edgeStart = chromosome.get(i);
            int edgeEnd = chromosome.get(i + 1);
            cost += citiesGraph.weightOfEdge(edgeStart, edgeEnd);
        }
    }

    public int getCost() {
        return cost;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }

    public Offsprings crossoverWith(Individual secondParent) {
        Offsprings offsprings = new Offsprings();
        List<Integer> firstOffspringChromosome = makeOffspringChromosome(this.chromosome, secondParent.chromosome);
        List<Integer> secondOffspringChromosome = makeOffspringChromosome(secondParent.chromosome, this.chromosome);
        offsprings.firstOffspring = new Individual(firstOffspringChromosome);
        offsprings.secondOffspring = new Individual(secondOffspringChromosome);
        return offsprings;
    }

    public void mutate(double mutationProbability) {
        if (Math.random() < mutationProbability) {
            Mutation mutation = new Mutation();
            mutation.changeChromosome();
        }
    }

    private List<Integer> makeOffspringChromosome(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {
        List<Integer> offspringChromosome = new LinkedList<>(); //TODO: do przemyślenia
        int edgeStart = firstParentChromosome.get(1);
        int edgeEnd = firstParentChromosome.get(2);

        int firstParentEdgeElementIndex = 0;
        for (int i = secondParentChromosome.size() - 1; i > 0; i--) {
            int currentElement = secondParentChromosome.get(i);
            if (currentElement == edgeStart || currentElement == edgeEnd) {
                firstParentEdgeElementIndex = i;
                break;
            }
        }

        for (int i = 0; i <= firstParentEdgeElementIndex; i++) {
            offspringChromosome.add(secondParentChromosome.get(i));
        }

        for (int i = 3; i < firstParentChromosome.size(); i++) {
            int value = firstParentChromosome.get(i);
            if (offspringChromosome.contains(value)) {
                continue;
            }
            offspringChromosome.add(value);
        }
        return offspringChromosome;
    }

    public class Offsprings {

        Individual firstOffspring;
        Individual secondOffspring;

        private Offsprings() {
        }

        public Individual getFirst() {
            return firstOffspring;
        }

        public Individual getSecond() {
            return secondOffspring;
        }
    }

    private class Mutation {

        private int leftIndex;
        private int rightIndex;
        private final List<Integer> rightPath = new LinkedList<>();
        private int geneFromLeft;
        private int geneFromRight;

        public void changeChromosome() {
            evaluateIndices();
            prepareRightPath();
            exchangeLeftGeneWithRightPath();
        }

        private void evaluateIndices() {
            RandomInteger random = RandomInteger.getInstance();
            do {
                leftIndex = random.getFromRange(1, chromosome.size() - 3);
                rightIndex = leftIndex + 1;
                geneFromLeft = chromosome.get(leftIndex);
                geneFromRight = chromosome.get(rightIndex);
            } while (isPrecedenceConstraintBetween(geneFromRight, geneFromLeft));
            rightPath.add(geneFromRight);
        }

        private void prepareRightPath() {
            int currentIndex = rightIndex + 1;
            if (currentIndex < chromosome.size()) {
                geneFromRight = chromosome.get(currentIndex);
            }
            while (currentIndex < chromosome.size() - 1 && isNotPrecedenceConstraintBetween(geneFromRight, geneFromLeft)) {
                rightPath.add(geneFromRight);
                currentIndex++;
                geneFromRight = chromosome.get(currentIndex);
            }
        }

        private void exchangeLeftGeneWithRightPath() {
            chromosome.remove(leftIndex);
            chromosome.removeAll(rightPath);
            chromosome.addAll(leftIndex, rightPath);
            chromosome.add(leftIndex + rightPath.size(), geneFromLeft);
        }

        private boolean isPrecedenceConstraintBetween(int firstGene, int secondGene) {
            return citiesGraph.weightOfEdge(firstGene, secondGene) == CitiesGraph.PRECEDENCE_CONSTRAINT;
        }

        private boolean isNotPrecedenceConstraintBetween(int firstGene, int secondGene) {
            return citiesGraph.weightOfEdge(firstGene, secondGene) != CitiesGraph.PRECEDENCE_CONSTRAINT;
        }

    }

}
