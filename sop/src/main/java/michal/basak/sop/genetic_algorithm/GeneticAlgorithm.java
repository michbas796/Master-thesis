package michal.basak.sop.genetic_algorithm;

public class GeneticAlgorithm {
    private int currentGenerationNumber;
    private Population population;
    private Population selectedIndividuals;
    private Population offspringsPopulation;
    private Individual bestIndividual;   
    private double meanPopulationFitness;
    private double prevMeanPopulationFitness;
    private GeneticAlgorithmParams params;
    private int generationsWithNoFitnessProgress;
    private final CitiesGraph citiesGraph;

    public GeneticAlgorithm(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        params = new GeneticAlgorithmParams();
        population = new Population();        
    }
    
    public void run() {
        prepareFirstPopulation();       
        while (isNextGeneration()) {
            selectIndividuals();
            createOffspringsPopulation();
            replacePopulation();            
        }
    }
    
    private boolean isNextGeneration() {
        switch(params.stopCondition) {
            case GENERATIONS_NUMBER:
                return currentGenerationNumber < params.maxGenerationsNumber;
            case MEAN_FITNESS:
                if (meanPopulationFitness <= prevMeanPopulationFitness) {
                    generationsWithNoFitnessProgress++;
                } else {
                    generationsWithNoFitnessProgress = 0;
                }
                return generationsWithNoFitnessProgress < params.maxGenerationsNumber;
            case TIME:
                //TODO
                return true;
            default:
                return currentGenerationNumber < params.maxGenerationsNumber;
        }                
    }
           
    private void prepareFirstPopulation() { 
        currentGenerationNumber = 0;  
        generationsWithNoFitnessProgress = 0;
        Individual newIndividual;
        for (int i = 0; i < params.populationSize; i++) {
            newIndividual = params.individualFactory.createIndividual(citiesGraph);
            population.add(newIndividual);            
        }
        evaluateMeanFitness();
    }
    
    private void evaluateMeanFitness() {
        int individualsFitnessSum = 0;
        for (Individual i : population) {
            individualsFitnessSum += i.getFitness();
        }
        meanPopulationFitness = individualsFitnessSum / population.size();
    }
    private void mutate() {
        //TODO
    }
    
    private void selectIndividuals() {
        selectedIndividuals = params.selector.selectIndividuals(population);
    }
           
    private void createOffspringsPopulation() {
        offspringsPopulation = new Population();        
        for (int i = 0; i < selectedIndividuals.size(); i += 2) {
            Individual firstParent = selectedIndividuals.getIndividual(i);           
            Individual secondParent = selectedIndividuals.getIndividual(i + 1);
            Individual.Offsprings offsprings = firstParent.crossoverWith(secondParent);
            offspringsPopulation.add(offsprings.getFirst());
            offspringsPopulation.add(offsprings.getSecond());            
        }
    }
    
    private void replacePopulation() {
        currentGenerationNumber++;
        params.replacer.replace(population, offspringsPopulation);
        evaluateMeanFitness();
    }
    
    public GeneticAlgorithmParams getParams() {
        return params;
    }
        
    public void setParams(GeneticAlgorithmParams params) {
        this.params = params;
    }
    
    public Individual getBestIndividual() {
        return bestIndividual;
    }
    
    public double getMeanPopulationFitness() {
        return meanPopulationFitness;
    }          
}
