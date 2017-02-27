package michal.basak.sop.application;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import michal.basak.sop.genetic_algorithm.*;
import michal.basak.sop.genetic_algorithm.path_generation.*;
import michal.basak.sop.genetic_algorithm.population_replacing.*;
import michal.basak.sop.genetic_algorithm.selection.*;

public class Application {

    private static String[] inputParams;
    private static CitiesGraph citiesGraph;
    private static GeneticAlgorithmParams algParams;
    private static int stopConditionParams = 0;
    private static int selectionMethodParams = 0;
    private static int populationReplacementParams = 0;
    private static int pathGenerationParams = 0;
    private static int numberOfRepeatsParams = 0;
    private static int resultsFileParams = 0;
    private static int numberOfrepeats = 1;
    private static String resultsFileName;
    private static boolean resultsFileSelected = false;
    private static boolean meanFitnessFileSelected = false;
    private static boolean firstAlgorithmExecution = true;
    private static String inputFileName;
    private static boolean silentMode = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageNotesAndExit();
        } else {
            inputParams = Arrays.copyOfRange(args, 0, args.length - 1);
            inputFileName = args[args.length - 1];
            File inputFile = new File(inputFileName);
            if (inputFile.isFile()) {
                try {
                    citiesGraph = new CitiesGraph(inputFile);
                } catch (IOException ex) {
                    printUsageNotesAndExit();
                }
                validateInputParamsAndExitIfInvalid();
                setAlgorithmParameters();
                GeneticAlgorithm algorithm = new GeneticAlgorithm(algParams);
                for (int i = 0; i < numberOfrepeats; i++) {
                    GeneticAlgorithm.Results results = algorithm.call();
                    if (!silentMode) {
                        print(results);
                    }
                    if (resultsFileSelected || meanFitnessFileSelected) {
                        save(results);
                    }
                }
            } else {
                printUsageNotesAndExit();
            }
        }
    }

    private static void print(GeneticAlgorithm.Results results) {
        System.out.println(results.getBestIndividual().cost());
        int[] bestChromosome = results.getBestIndividual().getChromosomeCopy();
        System.out.print("( ");
        for (int i : bestChromosome) {
            System.out.printf("%d ", i);
        }
        System.out.println(")");
        System.out.println(results.getMeanPopulationCosts());
        System.out.println("Czas wykonania: " + results.getExecutionTimeInMilliseconds() + "ms");
    }

    private static void setAlgorithmParameters() {
        algParams = new GeneticAlgorithmParams(citiesGraph);
        for (int i = 0; i < inputParams.length; i++) {
            try {
                switch (inputParams[i]) {
                    case "-g":
                        algParams.setMaxNumberOfGenerations(Integer.parseInt(inputParams[i + 1]));
                        i++;
                        break;
                    case "-p":
                        algParams.setPopulationSize(Integer.parseInt(inputParams[i + 1]));
                        i++;
                        break;
                    case "-ts":
                        algParams.setSelector(new TournamentSelector(Integer.parseInt(inputParams[i + 1])));
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
                    case "-r":
                        numberOfrepeats = Integer.parseInt(inputParams[i + 1]);
                        i++;
                        break;
                    case "-rf":
                        resultsFileName = inputParams[i + 1];
                        resultsFileSelected = true;
                        i++;
                        break;
                    case "-mff":
                        resultsFileName = inputParams[i + 1];
                        meanFitnessFileSelected = true;
                        i++;
                        break;
                    case "-sm":
                        silentMode = true;
                        break;
                }
            } catch (NumberFormatException e) {
                printUsageNotesAndExit();
            }

        }
    }

    private static void printUsageNotesAndExit() {
        System.out.println("Użycie:");
        System.out.println("sop [-opcje] <nazwa pliku z danymi>");
        System.out.println("Dostępne opcje:");
        System.out.println("Liczba powtórzeń wykonania algorytmu:");
        System.out.println("-r <liczba prób>");
        System.out.println("Warunek stopu algorytmu:");
        System.out.println("-g <liczba pokoleń>");
        System.out.println("-g określa maksmalną liczbę pokoleń");
        System.out.println("Liczba osobników w populacji:");
        System.out.println("-p <liczebność populacji>");
        System.out.println("Metoda generowania tras w populacji początkowej:");
        System.out.println("-rpg generuje losowe trasy");
        System.out.println("-gpg tworzy trasy z użyciem fragmetów częściowo wygenerowanych przez algorytm zachłanny");
        System.out.println("Metoda selekcji:");
        System.out.println("-ts <rozmiar turnieju>");
        System.out.println("Prawdopodobieństwo krzyżowania:");
        System.out.println("-cp <liczba z przedziału (0, 1>");
        System.out.println("Prawdopodobieństwo mutacji:");
        System.out.println("-mp <liczba z przedziału (0,1)>");
        System.out.println("Zastępowanie populacji:");
        System.out.println("-fr | -er <rozmiar elity>");
        System.out.println("-fr zastępuje całą populację populacją potomną");
        System.out.println("-er pozostawia najlepsze osobniki z populacji rodzicielskiej. Liczbę pozostawianych osobników określa rozmiar elity.");
        System.out.println("Zapis wyników:");
        System.out.println("-rf <nazwa pliku> - zapisuje do pliku długość najkrótszej znalezionej trasy oraz czas trwania obliczeń");
        System.out.println("-mff <nazwa pliku> - zapisuje średnie wartości przystosowania kolejnych populacji");
        System.out.println("Inne opcje");
        System.out.println("-sm wyłącza wyświetlanie wyników w konsoli");
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
            if (inputParams[i].matches("-g|-t|-r|-er|-p|-ts")) {
                if (i + 1 >= inputParams.length || !inputParams[i + 1].matches("\\d+")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (inputParams[i].matches("-mp|-cp")) {
                if (i + 1 >= inputParams.length || !inputParams[i + 1].matches("0.\\d+|1")) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (inputParams[i].matches("-rf|-mff")) {
                if (i + 1 >= inputParams.length) {
                    printUsageNotesAndExit();
                }
                i++;
            } else if (!inputParams[i].matches("-fr|-rpg|-gpg|-sm")) {
                printUsageNotesAndExit();
            }
        }
        if (theSameParamsAreRepeated()) {
            printUsageNotesAndExit();
        }
    }

    private static boolean theSameParamsAreRepeated() {
        return stopConditionParams > 1 || selectionMethodParams > 1
                || populationReplacementParams > 1 || pathGenerationParams > 1
                || numberOfRepeatsParams > 1 || resultsFileParams > 1;
    }

    private static void assignParamsToGroups(String param) {
        if (param.matches("-g|-t")) {
            stopConditionParams++;
        } else if (param.matches("-rs")) {
            selectionMethodParams++;
        } else if (param.matches("-fr|er")) {
            populationReplacementParams++;
        } else if (param.matches("-rpg|-gpg")) {
            pathGenerationParams++;
        } else if (param.matches("-r")) {
            numberOfRepeatsParams++;
        } else if (param.matches("-rf|-mff")) {
            resultsFileParams++;
        }
    }

    private static void save(GeneticAlgorithm.Results results) {
        File resultsFile = new File(resultsFileName + ".csv");
        Path resultsFilePath = resultsFile.toPath();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter resultsFileWriter = Files.newBufferedWriter(resultsFilePath, charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (firstAlgorithmExecution) {
                resultsFileWriter.write(inputFileName);
                resultsFileWriter.write(", ");
                for (String param : inputParams) {
                    resultsFileWriter.write(param);
                    resultsFileWriter.write(" ");
                }
                resultsFileWriter.newLine();
                firstAlgorithmExecution = false;
            }
            if (meanFitnessFileSelected) {
                List<Double> costs = results.getMeanPopulationCosts();
                for (Double cost : costs) {
                    resultsFileWriter.write(String.valueOf(cost));
                    resultsFileWriter.newLine();
                }
                resultsFileWriter.newLine();
            } else {
                resultsFileWriter.write(String.valueOf(results.getBestIndividual().cost()));
                resultsFileWriter.write(", ");
                resultsFileWriter.write(String.valueOf(results.getExecutionTimeInMilliseconds()));
                resultsFileWriter.newLine();
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
