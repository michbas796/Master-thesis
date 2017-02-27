package michal.basak.sop.genetic_algorithm.mutation;

public interface MutationOperator {

    int[] changeChromosome(int[] chromosome);

    void setMutationProbability(double mutationProbability);
}
