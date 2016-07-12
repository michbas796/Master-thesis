
package michal.basak.sop.genetic_algorithm;


public class FullReplacer implements PopulationReplacer {
       
    @Override
    public void replace(Population currentPopulation, Population newPopulation){
        currentPopulation = newPopulation;
    }
    
}
