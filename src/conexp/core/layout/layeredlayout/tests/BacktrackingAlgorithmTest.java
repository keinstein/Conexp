/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;

import conexp.core.layout.layeredlayout.BacktrackingAlgorithm;
import junit.framework.TestCase;

import java.util.Arrays;


public class BacktrackingAlgorithmTest extends TestCase {
    public static void testBactracking() {
        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        algorithm.setRange(-11, 11);
        algorithm.setStep(1);
        double[] solutionVector = {0, 0};
        algorithm.firstPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-11, -11}, solutionVector));
        algorithm.nextPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-11, -10}, solutionVector));
        solutionVector = new double[]{-11, 11};
        algorithm.nextPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-10, -11}, solutionVector));
    }

    public static void testHasMorePoints() {
        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        algorithm.setRange(-11, 11);
        algorithm.setStep(1);

        double[] solutionVector = {-11, -11};

        assertTrue(algorithm.hasMorePoints(solutionVector));
        solutionVector = new double[]{11, 11};
        assertFalse(algorithm.hasMorePoints(solutionVector));

    }


}
