package michal.basak.sop.helpers;

import java.util.concurrent.*;

public class RandomInteger {

    public static RandomInteger getInstance() {
        return RandomIntegerHolder.INSTANCE;
    }

    private RandomInteger() {

    }

    public int getFromRange(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(upperBound - lowerBound) + lowerBound;
    }

    private static class RandomIntegerHolder {

        private static final RandomInteger INSTANCE = new RandomInteger();
    }

}
