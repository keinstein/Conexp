package conexp.core.layout.layeredlayout.tests;

import junit.framework.TestCase;
import conexp.core.layout.layeredlayout.BacktrackingAlgorithm;

import java.util.Arrays;

import util.StringUtil;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class BacktrackingAlgorithmTest extends TestCase {
    public void testBactracking(){
        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        algorithm.setRange(-11, 11);
        algorithm.setStep(1);
        double [] solutionVector = {0, 0};
        algorithm.firstPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-11, -11}, solutionVector));
        algorithm.nextPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-11, -10}, solutionVector));
        solutionVector = new double[]{-11, 11};
        algorithm.nextPoint(solutionVector);
        assertTrue(Arrays.equals(new double[]{-10, -11}, solutionVector));
    }

    public void testHasMorePoints(){
        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        algorithm.setRange(-11, 11);
        algorithm.setStep(1);

        double [] solutionVector = {-11, -11};

        assertTrue(algorithm.hasMorePoints(solutionVector));
        solutionVector = new double[]{11, 11};
        assertFalse(algorithm.hasMorePoints(solutionVector));

    }


}
