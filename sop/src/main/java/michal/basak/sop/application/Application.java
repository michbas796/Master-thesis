package michal.basak.sop.application;

import michal.basak.sop.genetic_algorithm.population_replacing.FullReplacer;
import michal.basak.sop.genetic_algorithm.population_replacing.ElitaryReplacer;
import michal.basak.sop.genetic_algorithm.selection.RankSelector;
import michal.basak.sop.genetic_algorithm.selection.RouletteWheelSelector;
import michal.basak.sop.genetic_algorithm.selection.TournamentSelector;
import michal.basak.sop.genetic_algorithm.selection.StochasticUniversalSamplingSelector;
import com.google.common.base.Stopwatch;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.selection.BinaryTournamentSelector;


public class Application {
    
    private static String[] inputParams;     
    private static GeneticAlgorithmParams algParams = new GeneticAlgorithmParams();
    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        inputParams = Arrays.copyOfRange(args, 0, args.length - 1);               
        if (args.length == 0) {
            printUsageNotesAndExit();
        } else {
            File inputFile = new File(args[args.length - 1]);
            if (inputFile.isFile()) {
                CitiesGraph citiesGraph = new CitiesGraph(inputFile);
                validateInputParamsAndExitIfInvalid();
                setAlgorithmParameters();
                GeneticAlgorithm algorithm = new GeneticAlgorithm(citiesGraph);
                algorithm.setParams(algParams);
                Stopwatch stopwatch = Stopwatch.createStarted();
                algorithm.run();
                stopwatch.stop();
                //TODO zapis wyników
                //tymczasowo:
                System.out.println(algorithm.getBestIndividual().getFitness());
            } else {
                printUsageNotesAndExit();               
            }
        }                 
    }

    private static void setAlgorithmParameters(){
        for (int i = 0; i < inputParams.length; i++) {
            try {
                switch (inputParams[i]) {
                    case "-g":
                        algParams.setMaxNumberOfGenerations(Integer.parseInt(inputParams[i+1]));
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.GENERATIONS_NUMBER);
                        i++;
                        break;
                    case "-t":
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.TIME);
                        //TODO
                        break;
                    case "-mf":
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.MEAN_FITNESS);
                        algParams.setMaxNumberOfGenerations(Integer.parseInt(inputParams[i+1]));
                        i++;
                        break;
                    case "-p":
                        algParams.setPopulationSize(Integer.parseInt(inputParams[i+1]));
                        i++;
                        break;
                    case "-rw":
                        algParams.setSelector(new RouletteWheelSelector());
                        break;
                    case "-ts":
                        algParams.setSelector(new TournamentSelector(Integer.parseInt(inputParams[i+1])));
                        break;
                    case "-rs":
                        algParams.setSelector(new RankSelector());
                        break;
                    case "-s":
                        algParams.setSelector(new StochasticUniversalSamplingSelector());
                        break;
                    case "-bt":
                        algParams.setSelector(new BinaryTournamentSelector());
                        break;
                    case "-fr":
                        algParams.setReplacer(new FullReplacer());
                        break;
                    case "-er":                       
                        algParams.setReplacer(new ElitaryReplacer(Integer.parseInt(inputParams[i+1])));
                        break;
                    case "-mp":                        
                        algParams.setMutationProbability(Double.parseDouble(inputParams[i+1]));
                        break;
            }
            } catch (NumberFormatException e) {
                //TODO
            }
           
        }
    }
    
    private static void printUsageNotesAndExit() {
        System.out.println("Użycie:");
        System.out.println("sop [-opcje] <nazwa pliku z danymi>");
        System.out.println("Dostępne opcje:");
        System.out.println("Warunek stopu algorytmu:");
        System.out.println("-g <liczba pokoleń> | -t <czas obliczeń> | -mf <liczba pokoleń> " );
        System.out.println("-g określa maksmalną liczbę pokoleń");
        System.out.println("-t określa maksymalny czas obliczeń");
        System.out.println("-mf określa warunek stopu oparty na średnim dopasowaniu populacji. <liczba pokoleń> oznacza maksymalną dopuszczalną liczbę pokoleń bez poprawy średniego dopasowania.");
        System.out.println("Liczba osobników w populacji:");
        System.out.println("-p <liczebność populacji>");
        System.out.println("Metoda selekcji:");
        System.out.println("-rw | -ts <rozmiar turnieju> |-bt | -rs | -s ");
        System.out.println("-rw selekcja proporcjonalna (koło ruletki)");
        System.out.println("-bt binarna selekcja turniejowa");
        System.out.println("-ts selekcja turniejowa");
        System.out.println("-s selekcja stochastyczna");
        System.out.println("Prawdopodobieństwo mutacji:");
        System.out.println("-mp <liczba z przedziału (0,1)>");
        System.out.println("Zastępowanie populacji:");
        System.out.println("-fr | -er <rozmiar elity>");
        System.out.println("-fr zastępuje całą populację populacją potomną");
        System.out.println("-er pozostawia najlepsze osobniki z populacji rodzicielskiej. Liczbę pozostawianych osobników określa rozmiar elity.");        
        System.exit(0);
    } 
    
    private static void validateInputParamsAndExitIfInvalid() {
        Set<String> paramsSet = new HashSet<>();
        List<String> stopConditionParamsList = new ArrayList<>();
        List<String> selectionMethodParamsList = new ArrayList<>();
        List<String> populationReplaceParamsList = new ArrayList<>();
        for (int i=0; i < inputParams.length; i++) {
            if (paramsSet.contains(inputParams[i])) {
                printUsageNotesAndExit();
            } else {
                paramsSet.add(inputParams[i]);
            }
            if (inputParams[i].matches("-g|-t|-mf")) {
                stopConditionParamsList.add(inputParams[i]);
            } else if (inputParams[i].matches("-rw|-rs|-ts|-s|-bt")) {
                selectionMethodParamsList.add(inputParams[i]);
            } else if(inputParams[i].matches("-fr|er")) {
                populationReplaceParamsList.add(inputParams[i]);
            }
            if (inputParams[i].matches("-g|-t|-mf|-ts|-er|-p")) {
                if (i+1 >= inputParams.length || !inputParams[i+1].matches("\\d+")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (inputParams[i].matches("-mp")) {
                if (i+1 >= inputParams.length || !inputParams[i+1].matches("0.\\d+")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else {
                if (!inputParams[i].matches("-rw|-rs|-s|-fr|-bt")) {
                    printUsageNotesAndExit();
                }
            }
        }
        if (stopConditionParamsList.size() > 1 || selectionMethodParamsList.size() > 1 || populationReplaceParamsList.size() > 1) {
            printUsageNotesAndExit();
        }        
    }   
}
