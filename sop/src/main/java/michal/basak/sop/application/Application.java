package michal.basak.sop.application;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import michal.basak.sop.genetic_algorithm.CitiesGraph;
import michal.basak.sop.genetic_algorithm.GeneticAlgorithm;

public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO obsługa argumentów
        CitiesGraph citiesGraph = new CitiesGraph();       
        GeneticAlgorithm algorithm = new GeneticAlgorithm(citiesGraph);
        GeneticAlgorithm.Params params = algorithm.getParams();
        //TODO ustawienie parametrów
        algorithm.setParams(params);
        Stopwatch stopwatch = Stopwatch.createStarted();
        algorithm.run();
        stopwatch.stop();
        long algorithmElapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        //TODO zapis wyników
    }
    
}
