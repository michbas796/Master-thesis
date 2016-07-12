package michal.basak.sop.genetic_algorithm;

public class GeneticAlgorithm {
    private int currentGenerationNumber;
    private Population population;
    private Population selectedIndividuals;
    private Population offsprings;
    private Individual bestIndividual;   
    private double meanPopulationFitness;
    private double prevMeanPopulationFitness;
    private Params params;
    private int generationsWithNoFitnessProgress;
    private CitiesGraph citiesGraph;

    public GeneticAlgorithm(CitiesGraph citiesGraph) {
        this.citiesGraph = citiesGraph;
        params = new Params();
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
        }
        
        return currentGenerationNumber < params.maxGenerationsNumber; 
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
    
    private void crossover(Individual firstParent, Individual secondParent) {
        //TODO
    }
    
    private void createOffspringsPopulation() {
        for (int i = 0; i < selectedIndividuals.size(); i += 2) {
            crossover(selectedIndividuals.getIndividual(i), selectedIndividuals.getIndividual(i));
        }
    }
    
    private void replacePopulation() {
        currentGenerationNumber++;
        params.replacer.replace(population, offsprings);
        evaluateMeanFitness();
    }
    
    public Params getParams() {
        return params;
    }
        
    public void setParams(Params params) {
        this.params = params;
    }
    
    public Individual getBestIndividual() {
        return bestIndividual;
    }
    
    public double getMeanPopulationFitness() {
        return meanPopulationFitness;
    }
            
    
    public class Params {
        int maxGenerationsNumber;        
        int populationSize;               
        double mutationProbability;        
        IndividualFactory individualFactory;
        IndividualSelector selector;
        PopulationReplacer replacer;
        StopCondition stopCondition;

        public Params() {
            maxGenerationsNumber = 10000;
            populationSize = 10;
            mutationProbability = 0.1;           
            individualFactory = new IndividualFactory();
            selector = new RouletteWheelSelector();
            replacer = new FullReplacer();
            stopCondition = StopCondition.GENERATIONS_NUMBER;            
        }
                
        public void setMaxGenerations(int maxGenerationsNumber) {
            this.maxGenerationsNumber = maxGenerationsNumber;
        }
        
        public void setPopulationSize(int populationSize) {
            this.populationSize = populationSize;
        }
                       
        public void setSelector(IndividualSelector selector) {
            this.selector = selector;
        }
        
        public void setReplacer(PopulationReplacer replacer) {
            this.replacer = replacer;
        }
        
        public void setIndividalFactory(IndividualFactory individualFactory) {
            this.individualFactory = individualFactory;
        }
        
        public void setStopCondition(StopCondition stopCondition) {
            this.stopCondition = stopCondition;
        }
    }
    
    public enum StopCondition {
        GENERATIONS_NUMBER, MEAN_FITNESS, TIME
    }
}
