package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.*;

public interface IndividualSelector {

    void selectIndividuals(Population population, Population selectedIndividuals);
}
