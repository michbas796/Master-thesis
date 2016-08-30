package michal.basak.sop.application;

import java.io.*;
import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;
import michal.basak.sop.genetic_algorithm.population_replacing.*;
import michal.basak.sop.genetic_algorithm.selection.*;

public class Application {

    private static String[] inputParams;
    private static CitiesGraph citiesGraph;
    private static GeneticAlgorithmParams algParams;
    private static final List<String> STOP_CONDITION_PARAMS = new ArrayList<>();
    private static final List<String> SELECTION_METHOD_PARAMS = new ArrayList<>();
    private static final List<String> POPULATION_REPLACE_PARAMS = new ArrayList<>();
    private static final List<String> PATH_GENERATION_PARAMS = new ArrayList<>();

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
                citiesGraph = new CitiesGraph(inputFile);
                validateInputParamsAndExitIfInvalid();
                setAlgorithmParameters();
                GeneticAlgorithm algorithm = new GeneticAlgorithm(algParams);
                GeneticAlgorithm.Results results = algorithm.call();
                print(results);
            } else {
                printUsageNotesAndExit();
            }
        }
    }

    private static void print(GeneticAlgorithm.Results results) {
        System.out.println(results.getBestIndividual().getCost());
        System.out.println(results.getBestIndividual().getChromosome());
        System.out.println(results.getMeanPopulationCost());
        System.out.println("Czas wykonania: " + results.getExecutionTimeInMilliseconds() + "ms");
    }

    private static void setAlgorithmParameters() {
        algParams = new GeneticAlgorithmParams(citiesGraph);
        for (int i = 0; i < inputParams.length; i++) {
            try {
                switch (inputParams[i]) {
                    case "-g":
                        algParams.setMaxNumberOfGenerations(Integer.parseInt(inputParams[i + 1]));
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.GENERATIONS_NUMBER);
                        i++;
                        break;
                    case "-t":
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.TIME);
                        algParams.setMaxExecutionTimeInMilliseconds(Long.parseLong(inputParams[i + 1]));
                        break;
                    case "-mf":
                        algParams.setStopCondition(GeneticAlgorithmParams.StopCondition.MEAN_FITNESS);
                        algParams.setMaxNumberOfGenerations(Integer.parseInt(inputParams[i + 1]));
                        i++;
                        break;
                    case "-p":
                        algParams.setPopulationSize(Integer.parseInt(inputParams[i + 1]));
                        i++;
                        break;
                    case "-rw":
                        algParams.setSelector(new RouletteWheelSelector());
                        break;
                    case "-ts":
                        algParams.setSelector(new TournamentSelector(Integer.parseInt(inputParams[i + 1])));
                        break;
                    case "-rs":
                        algParams.setSelector(new RankSelector());
                        break;
                    case "-s":
                        algParams.setSelector(new StochasticUniversalSamplingSelector());
                        break;
                    case "-fr":
                        algParams.setReplacer(new FullReplacer());
                        break;
                    case "-er":
                        algParams.setReplacer(new ElitaryReplacer(Integer.parseInt(inputParams[i + 1])));
                        break;
                    case "-mp":
                        algParams.setMutationProbability(Double.parseDouble(inputParams[i + 1]));
                        break;
                    case "-rpg":
                        algParams.setPathGenerator(new RandomPathGenerator(citiesGraph));
                        break;
                    case "-gpg":
                        algParams.setPathGenerator(new PartiallyGreedyPathGenerator(citiesGraph));
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
        System.out.println("-g <liczba pokoleń> | -t <czas obliczeń> | -mf <liczba pokoleń> ");
        System.out.println("-g określa maksmalną liczbę pokoleń");
        System.out.println("-t określa maksymalny czas obliczeń");
        System.out.println("-mf określa warunek stopu oparty na średnim dopasowaniu populacji. <liczba pokoleń> oznacza maksymalną dopuszczalną liczbę pokoleń bez poprawy średniego dopasowania.");
        System.out.println("Liczba osobników w populacji:");
        System.out.println("-p <liczebność populacji>");
        System.out.println("Metoda generowania tras w populacji początkowej:");
        System.out.println("-rpg generuje losowe trasy");
        System.out.println("-gpg tworzy trasy z użyciem fragmetów częściowo wygenerowanych przez algorytm zachłanny");
        System.out.println("Metoda selekcji:");
        System.out.println("-rw | -ts <rozmiar turnieju> | -rs | -s ");
        System.out.println("-rw selekcja proporcjonalna (koło ruletki)");
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
        for (int i = 0; i < inputParams.length; i++) {
            if (paramsSet.contains(inputParams[i])) {
                printUsageNotesAndExit();
            } else {
                paramsSet.add(inputParams[i]);
            }
            assignParamsToGroups(inputParams[i]);
            if (inputParams[i].matches("-g|-t|-mf|-ts|-er|-p")) {
                if (i + 1 >= inputParams.length || !inputParams[i + 1].matches("\\d+")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (inputParams[i].matches("-mp")) {
                if (i + 1 >= inputParams.length || !inputParams[i + 1].matches("0.\\d+")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (!inputParams[i].matches("-rw|-rs|-s|-fr|-rpg|-gpg")) {
                printUsageNotesAndExit();
            }
        }
        if (STOP_CONDITION_PARAMS.size() > 1 || SELECTION_METHOD_PARAMS.size() > 1 || POPULATION_REPLACE_PARAMS.size() > 1 || PATH_GENERATION_PARAMS.size() > 1) {
            printUsageNotesAndExit();
        }
    }

    private static void assignParamsToGroups(String param) {
        if (param.matches("-g|-t|-mf")) {
            STOP_CONDITION_PARAMS.add(param);
        } else if (param.matches("-rw|-rs|-ts|-s")) {
            SELECTION_METHOD_PARAMS.add(param);
        } else if (param.matches("-fr|er")) {
            POPULATION_REPLACE_PARAMS.add(param);
        } else if (param.matches("-rpg|-gpg")) {
            PATH_GENERATION_PARAMS.add(param);
        }
    }
}
