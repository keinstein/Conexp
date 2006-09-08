/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;


public class BasicStatistics {
    /**
     * BasicStatistics constructor comment.
     */
    private BasicStatistics() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 12:27:04)
     *
     * @param timeData long[]
     * @return double
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
     *
     * @param timeOfExecution long
     * @return double
     */
    public static double calcVariation(long[] timeOfExecution) {
        if (null == timeOfExecution) {
            return 0;
        }
        final int size = timeOfExecution.length;
        if (size <= 1) {
            return 0;
        }

        long dispSum = 0;
        long totalSum = sumOfArray(timeOfExecution);

        for (int i = size; --i >= 0;) {
            long tempDelta = size * timeOfExecution[i] - totalSum;
            dispSum += tempDelta * tempDelta;
        }
        return (double) dispSum / (double) (size * (size - 1));
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 12:32:21)
     *
     * @param array long[]
     * @return long
     */
    public static long sumOfArray(long[] array) {
        long totalSum = 0;
        for (int i = array.length; --i >= 0;) {
            totalSum += array[i];
        }
        return totalSum;
    }
}
