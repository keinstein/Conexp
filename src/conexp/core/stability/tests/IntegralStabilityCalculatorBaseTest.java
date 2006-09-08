/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability.tests;

import conexp.core.BinaryRelation;
import conexp.core.stability.IntegralStabilityCalculator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;



public abstract class IntegralStabilityCalculatorBaseTest extends TestCase {
    private static final double PRECISION = 0.01;

    public void testCalculateStabilityForLatticeCaseWithOneObject() {

        final int[][] arrRelation = new int[][]{
                {1, 1, 1}
        };

        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        IntegralStabilityCalculator stabilityCalculator = makeIntegralStabilityCalculator();
        stabilityCalculator.setRelation(relation);
        assertEquals(1.0, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 1, 1})), PRECISION);
    }

    protected abstract IntegralStabilityCalculator makeIntegralStabilityCalculator();

    public void testCalculateStabilityForLatticeCaseWithTwoObjects() {

        int[][] arrRelation = new int[][]{
                {0, 1},
                {1, 0}
        };

        IntegralStabilityCalculator stabilityCalculator = buildStabilityCalculator(arrRelation);
        assertEquals(0.25, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 0})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 1})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 0})), PRECISION);
        assertEquals(1.0, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 1})), PRECISION);
    }

    private IntegralStabilityCalculator buildStabilityCalculator(int[][] arrRelation) {
        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        IntegralStabilityCalculator stabilityCalculator = makeIntegralStabilityCalculator();
        stabilityCalculator.setRelation(relation);
        return stabilityCalculator;
    }

    public void testCase3() {
        int[][] arrRelation = new int[][]{
                {1, 0, 1, 0},
                {1, 1, 0, 0},
                {0, 1, 0, 1}
        };
        IntegralStabilityCalculator stabilityCalculator = buildStabilityCalculator(arrRelation);
        assertEquals(0.25, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 0, 0, 0})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 1, 0, 1})), PRECISION);
        assertEquals(0.25, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 0, 0, 0})), PRECISION);
        assertEquals(0.25, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 1, 0, 0})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 1, 0, 0})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 0, 1, 0})), PRECISION);
        assertEquals(1.0, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 1, 1, 1})), PRECISION);
    }
}
