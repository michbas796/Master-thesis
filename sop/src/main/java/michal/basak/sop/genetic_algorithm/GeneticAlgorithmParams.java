package michal.basak.sop.genetic_algorithm;

public class GeneticAlgorithmParams {
        int maxGenerationsNumber;        
        int populationSize;               
        double mutationProbability;        
        IndividualFactory individualFactory;
        IndividualSelector selector;
        PopulationReplacer replacer;
        StopCondition stopCondition;

        public GeneticAlgorithmParams() {
            maxGenerationsNumber = 10000;
            populationSize = 10;
            mutationProbability = 0.1;           
            individualFactory = new IndividualFactory();
            selector = new RouletteWheelSelector();
            replacer = new FullReplacer();
            stopCondition = StopCondition.GENERATIONS_NUMBER;            
        }
                               
        public void setMaxNumberOfGenerations(int maxGenerationsNumber) {
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
        
        public enum StopCondition {
            GENERATIONS_NUMBER, MEAN_FITNESS, TIME
        }
    }
    
    
