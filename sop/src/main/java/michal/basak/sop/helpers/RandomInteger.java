package michal.basak.sop.helpers;

public class RandomInteger {

    public static RandomInteger getInstance() {
        return RandomIntegerHolder.INSTANCE;
    }

    private RandomInteger() {

    }

    public int getFromRange(int lowerBound, int upperBound) {
        int numberOfIntegers = upperBound - lowerBound;
        int indexOfSelectedInteger = 0;
        if (numberOfIntegers > 1) {
            double[] distribution = new double[numberOfIntegers];
            double distributionValue = 0;
            for (int i = 0; i < distribution.length; i++) {
                distributionValue += 1.0 / numberOfIntegers;
                distribution[i] = distributionValue;
            }
            double random = Math.random();

            for (int i = 0; i < distribution.length; i++) {
                if (random < distribution[i]) {
                    indexOfSelectedInteger = i;
                    break;
                }
            }
        }
        return lowerBound + indexOfSelectedInteger;
    }

    private static class RandomIntegerHolder {

        private static final RandomInteger INSTANCE = new RandomInteger();
    }

}
