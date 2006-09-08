/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability.tests;

import conexp.core.BinaryRelation;
import conexp.core.stability.PointStabilityCalculator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;



public abstract class PointStabilityCalculatorBaseTest extends TestCase {
    protected static final double PRECISION = 0.01;

    public void testCalculateStabilityForSetThatIsNotInLattice() {
        int[][] arrRelation = new int[][]{
                {0, 0, 1}
        };
        PointStabilityCalculator oneConceptStabilityCalculator = makePointStabilityCalculator();
        oneConceptStabilityCalculator.setRelation(SetBuilder.makeRelation(arrRelation));
        assertEquals(0, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{0, 1, 1})), OneConceptPointStabilityCalculatorTest.PRECISION);

    }

    public void testCalculateStabilityForConcept() {
        //formalization of the idea of algorithm:
        int[][] arrRelation = new int[][]{
                {0, 1},
                {1, 0}
        };

        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        PointStabilityCalculator oneConceptStabilityCalculator = makePointStabilityCalculator();
        oneConceptStabilityCalculator.setRelation(relation);
        assertEquals(0.25, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{0, 1})), OneConceptPointStabilityCalculatorTest.PRECISION);
        assertEquals(0.25, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{0, 0})), OneConceptPointStabilityCalculatorTest.PRECISION);
        assertEquals(0.25, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{1, 0})), OneConceptPointStabilityCalculatorTest.PRECISION);
        assertEquals(0.25, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{1, 1})), OneConceptPointStabilityCalculatorTest.PRECISION);

    }

    protected abstract PointStabilityCalculator makePointStabilityCalculator();

    public void testCalculateStabilityForCaseOfOnlyOneConcept() {
        int[][] arrRelation = new int[][]{
                {1, 1},
                {1, 1}
        };

        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        PointStabilityCalculator oneConceptStabilityCalculator = makePointStabilityCalculator();
        oneConceptStabilityCalculator.setRelation(relation);

        assertEquals(1, oneConceptStabilityCalculator.getPointStabilityOfSet(SetBuilder.makeSet(new int[]{1, 1})), OneConceptPointStabilityCalculatorTest.PRECISION);

    }
}
