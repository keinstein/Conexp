package conexp.experimenter.framework;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 14:59:49)
 * @author
 */
public class BasicStatistics {
    /**
     * BasicStatistics constructor comment.
     */
    public BasicStatistics() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 12:27:04)
     * @return double
     * @param timeData long[]
     */
    public static double calculateAverage(long[] timeData) {
        if (null == timeData || timeData.length == 0) {
            return 0;
        }
        return (double) sumOfArray(timeData) / (double) timeData.length;
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 12:36:19)
     * @return double
     * @param timeOfExecution long
     */
    public static double calcVariation(long[] timeOfExecution) {
        final int size = timeOfExecution.length;
        if (null == timeOfExecution || size <= 1) {
            return 0;
        }

        long dispSum = 0;
        long totalSum = sumOfArray(timeOfExecution);

        for (int i = size; --i >= 0;) {
            long tempDelta = size * timeOfExecution[i] - totalSum;
            dispSum += tempDelta * tempDelta;
        }
        return ((double) dispSum) / (double) (size * (size - 1));
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 12:32:21)
     * @return long
     * @param array long[]
     */
    public static long sumOfArray(long[] array) {
        long totalSum = 0;
        for (int i = array.length; --i >= 0;) {
            totalSum += array[i];
        }
        return totalSum;
    }
}