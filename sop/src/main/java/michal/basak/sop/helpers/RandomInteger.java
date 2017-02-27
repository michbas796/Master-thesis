package michal.basak.sop.helpers;

import java.util.*;

public class RandomInteger {

    public static int getFromRange(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }
}
