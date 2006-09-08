/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;

import conexp.core.layout.layeredlayout.DirectionVectorEvaluationResultsPair;
import junit.framework.TestCase;
import util.testing.TestUtil;

import java.util.Arrays;


public class DirectionVectorEvaluationResultsPairTest extends TestCase {
    public static void testSetDirectionVectorAndEvaluationResults() {
        DirectionVectorEvaluationResultsPair pair = new DirectionVectorEvaluationResultsPair();
        double[] directionVectors = {0, 1, 2};
        pair.setDirectionVectors(directionVectors);
        assertTrue(Arrays.equals(directionVectors, pair.getDirectionVectors()));
        directionVectors[0] = 3;
        assertFalse(Arrays.equals(directionVectors, pair.getDirectionVectors()));

        double[] evaluation = {0, -1};
        pair.setEvaluation(evaluation);
        assertTrue(Arrays.equals(evaluation, pair.getEvaluation()));
        evaluation[0] = 3;
        assertFalse(Arrays.equals(evaluation, pair.getEvaluation()));
    }

    public static void testPartialOrder() {
        DirectionVectorEvaluationResultsPair one = new DirectionVectorEvaluationResultsPair();
        one.setDirectionVectors(new double[]{1, 2, 3});
        one.setEvaluation(new double[]{0, 0});

        DirectionVectorEvaluationResultsPair two = new DirectionVectorEvaluationResultsPair();
        two.setDirectionVectors(new double[]{2, 2, 3});
        two.setEvaluation(new double[]{0, 1});

        assertTrue(two.isLesserThan(one));

        one.setEvaluation(new double[]{1, 0});
        assertFalse(two.isLesserThan(one));

        one.setEvaluation(new double[]{0, 1});
        assertFalse(two.isLesserThan(one));
        assertTrue(two.isEqual(one));
        assertTrue(one.isEqual(two));
    }

    public static void testEqualsAndHashCode() {
        DirectionVectorEvaluationResultsPair one = new DirectionVectorEvaluationResultsPair();
        DirectionVectorEvaluationResultsPair two = new DirectionVectorEvaluationResultsPair();
        TestUtil.testEqualsAndHashCode(one, two);
        one.setEvaluation(new double[]{0, 1});
        two.setEvaluation(new double[]{0, 1});
        TestUtil.testEqualsAndHashCode(one, two);

        one.setDirectionVectors(new double[]{0, 1, 2});
        TestUtil.testNotEquals(one, two);
    }


}
