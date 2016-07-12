package michal.basak.sop.genetic_algorithm;
import java.util.Arrays;
public class ElitaryReplacer implements PopulationReplacer{
    int eliteSize;
    
    public ElitaryReplacer(int eliteSize) {
        this.eliteSize = eliteSize;
    }
    
    @Override
    public void replace(Population currentPopulation, Population newPopulation) {
        //Arrays.sort(currentPopulation, ()->{currentPopulation[i].getFitness() > currentPopulation[i+1].getFitness()});
    }
}
