package michal.basak.sop.genetic_algorithm;
import java.util.Arrays;
public class ElitaryReplacer implements PopulationReplacer{
    int eliteSize;
    
    public ElitaryReplacer(int eliteSize) {
        this.eliteSize = eliteSize;
    }
    
    @Override
    public void replace(Population currentPopulation, Population newPopulation) {
        Population nextPopulation = new Population();
        currentPopulation.sortFromBestToWorst();
        for (int i = 0; i < eliteSize; i++) {
            nextPopulation.add(currentPopulation.getIndividual(i));
        }
        
        //TODO
    }
}
