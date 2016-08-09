package michal.basak.sop.genetic_algorithm.selection;

import michal.basak.sop.genetic_algorithm.Population;

public interface IndividualSelector {

    Population selectIndividualsFrom(Population population);
}
