package michal.basak.sop.genetic_algorithm.selection;

public abstract class RouletteWheel {

    public static double[] evaluateSlots(double[] probabilities) {
        double[] distributionFunction = new double[probabilities.length];
        distributionFunction[0] = probabilities[0];
        for (int i = 1; i < distributionFunction.length; i++) {
            distributionFunction[i] = distributionFunction[i - 1] + probabilities[i];
        }
        return distributionFunction;
    }

    public static int selectRandomSlotFrom(double[] slots) {
        double randomNumber = Math.random();
        for (int slotNumber = 0; slotNumber < slots.length; slotNumber++) {
            if (randomNumber < slots[slotNumber]) {
                return slotNumber;
            }
        }
        return slots.length - 1;
    }

}
