/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.BinaryRelation;
import conexp.core.Concept;
import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.compareutils.ConceptCollectionComparator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

public abstract class CalcStrategyTest extends TestCase {

    protected ConceptCalcStrategy calcStrategy;
    protected ConceptsCollection conceptSet;
    public static final int[][] BOOLEAN_3_ATTR = new int[][]{
                {1, 1, 0},
                {0, 1, 1},
                {1, 0, 1}
            };
    public static final int[][] LINEAR_4_NODE_4_ATTR = new int[][]{
                {0, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 1, 1, 1},
                {1, 1, 1, 1}
            };
    public static final int[][] NOMINAL_4_NODE_4_ATTR = new int[][]{
                        {1, 0, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                    };
    public static final int[][] LINEAR_2_NODE_2_ATTR = new int[][]{
                {1, 0},
                {1, 1}
            };
    public static final int[][] DEPTH_SEARCH_ERROR_5_ATTR = new int[][]{
                {1, 0, 1, 0, 0},
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0},
                {0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {0, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0},
                {1, 0, 1, 1, 0}
            };
    public static final int[][] NEXT_CLOSURE_ERROR_8_ATTR = new int[][]{
                {1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1},
                {0, 1, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 1, 0, 1, 0, 0}
            };

    protected void setUp() {
        calcStrategy = makeCalcStrategy();
    }

    protected void tearDown() throws Exception {
        calcStrategy.tearDown();
        calcStrategy = null;

        conceptSet = null;
        super.tearDown();
    }

    public void testBuildLexicograficalTree() {
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {1, 0, 1, 1, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 0, 0, 1}
        },
                new int[][]{
                    {0, 0, 0, 0, 0},
                    {1, 0, 1, 1, 0},
                    {1, 0, 0, 1, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 0, 1},
                    {0, 0, 1, 1, 0},
                    {0, 0, 0, 1, 0},
                    {0, 1, 0, 0, 0},
                    {1, 1, 1, 1, 1}
                },
                new int[][]{
                    {0, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1},
                    {0, 0, 1, 1},
                    {1, 0, 1, 0},
                    {1, 1, 1, 0},
                    {1, 1, 1, 1}
                },
                12
        );
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.02.01 1:27:46)
     */
    public void testExponential() {
        doTestCalcStrategyForExpectedIntentsAndExtents(BOOLEAN_3_ATTR,
                new int[][]{
                    {0, 0, 0},
                    {0, 0, 1},
                    {0, 1, 0},
                    {0, 1, 1},
                    {1, 0, 0},
                    {1, 0, 1},
                    {1, 1, 0},
                    {1, 1, 1}
                },
                new int[][]{
                    {0, 0, 0},
                    {0, 0, 1},
                    {0, 1, 0},
                    {0, 1, 1},
                    {1, 0, 0},
                    {1, 0, 1},
                    {1, 1, 0},
                    {1, 1, 1}
                },
                12
        );
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.02.01 1:18:42)
     */
    public void testLinear() {
        doTestCalcStrategyForExpectedIntentsAndExtents(LINEAR_4_NODE_4_ATTR, LINEAR_4_NODE_4_ATTR, LINEAR_4_NODE_4_ATTR, 3);
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.02.01 1:30:42)
     */
    public void testNominal() {
        doTestCalcStrategyForExpectedIntentsAndExtents(
                NOMINAL_4_NODE_4_ATTR,
                new int[][]{
                    {0, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1},
                    {1, 1, 1, 1}
                },
                new int[][]{
                    {0, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1},
                    {1, 1, 1, 1}
                },
                8
        );
    }


    /**
     * Insert the method's description here.
     * Creation date: (01.07.01 21:26:44)
     */
    protected abstract ConceptCalcStrategy makeCalcStrategy();


    /**
     * Insert the method's description here.
     * Creation date: (24.02.01 1:18:42)
     */
    public void testLin2() {
        doTestCalcStrategyForExpectedIntentsAndExtents(LINEAR_2_NODE_2_ATTR, LINEAR_2_NODE_2_ATTR, new int[][]{{1, 1}, {0, 1}}, 1);
    }

    protected void buildIntentsSetAndFillExpectationSet(BinaryRelation rel, com.mockobjects.ExpectationSet expSet, com.mockobjects.ExpectationSet expSetExtents, int expectedEdgeCount) {
        conceptSet = makeConceptCollection();
        commonTestForAllStrategies(rel, conceptSet, expSet, expSetExtents);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 21:57:53)
     */
    protected void commonTestForAllStrategies(BinaryRelation rel, ConceptsCollection conceptSet, ExpectationSet expSet, ExpectationSet expSetExtents) {
        setupStrategy(conceptSet);
        generateIntents();
        testIntentsAndExtentsSizes(conceptSet, rel);
        ConceptSetTestUtils.fillExpectationSetByIntentsFromLattice(expSet, conceptSet);
        ConceptSetTestUtils.fillExpectationSetByExtentsFromLattice(expSetExtents, conceptSet);
    }


    protected void doTestCalcStrategyForExpectedIntentsAndExtents(int[][] input, int[][] expOutputIntent, int[][] expOutputExtents, int expectedEdgeCount) {
        ExpectationSet expSet = SetBuilder.makeExpectationSetForIntents("Expected Intents", expOutputIntent);
        ExpectationSet extSetExtents = SetBuilder.makeExpectationSetForIntents("Expected Extents", expOutputExtents);
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        buildIntentsSetAndFillExpectationSet(rel, expSet, extSetExtents, expectedEdgeCount);
        expSet.verify();
        extSetExtents.verify();
    }


    protected void doTestCalcStrategyForExpectedSizeForFullLatticeCase(int[][] input, int expectedSize) {
        ConceptsCollection conceptSet = buildConceptCollection(input);
        if (conceptSet.conceptsCount() != expectedSize) {
            ConceptCollectionComparator comparator = new ConceptCollectionComparator(
                    SetBuilder.makeConceptSet(input),
                    conceptSet
            );
            comparator.dumpDifferencesToSout();
            assertEquals(expectedSize, conceptSet.conceptsCount());
        }
    }

    private ConceptsCollection buildConceptCollection(int[][] input) {
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        ConceptsCollection conceptSet = makeConceptCollection();
        setupStrategy(conceptSet);
        calcStrategy.calculateConceptSet();
        return conceptSet;
    }

    protected void generateIntents() {
        calcStrategy.calculateConceptSet();
    }


    protected ConceptsCollection makeConceptCollection() {
        return new ConceptsCollection();
    }


    protected abstract void setupStrategy(ConceptsCollection coll);


    public void testBadDepthSearchCalc() {
        int expIntents[][] = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1},
            {0, 0, 0, 1, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 1},
            {0, 0, 1, 1, 0},
            {0, 0, 1, 1, 1},
            {0, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {0, 1, 0, 1, 0},
            {0, 1, 0, 1, 1},
            {0, 1, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 0, 0, 0, 0},
            {1, 0, 0, 0, 1},
            {1, 0, 0, 1, 0},
            {1, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 0, 1, 1, 0},
            {1, 1, 1, 1, 1}
        };
        int expextedExtents[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0},
            {0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0},
            {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}

        };
        doTestCalcStrategyForExpectedIntentsAndExtents(DEPTH_SEARCH_ERROR_5_ATTR, expIntents, expextedExtents, 43);

    }


    /**
     * Insert the method's description here.
     * Creation date: (22.07.01 8:01:24)
     */
    public void testBadNourineRaynaud() {
        int[][] rel = {
            {1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1},
            {1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
            {0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
            {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1},
            {0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0}
        };
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(rel, 3381);

    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 22:02:30)
     */
    protected void testIntentsAndExtentsSizes(ConceptsCollection conceptSet, BinaryRelation rel) {
        for (int i = conceptSet.conceptsCount(); --i >= 0;) {
            Concept con = conceptSet.conceptAt(i);
            assertEquals(rel.getRowCount(), con.getObjects().size());
            assertEquals(rel.getColCount(), con.getAttribs().size());
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.02.01 23:19:10)
     */
    public void testNextClosedSetHardLattice() {
        doTestCalcStrategyForExpectedIntentsAndExtents(NEXT_CLOSURE_ERROR_8_ATTR,
                new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1, 0, 0, 1},
                    {0, 0, 0, 1, 0, 0, 0, 1},
                    {0, 1, 1, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 1, 0},
                    {1, 0, 0, 0, 0, 0, 1, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0, 1, 0, 0},
                    {0, 0, 1, 1, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 0, 0}
                },
                new int[][]{
                    {1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 1, 0},
                    {0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0},
                    {1, 1, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 0, 1},
                    {0, 1, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 1, 0, 1},
                    {0, 1, 0, 1, 0, 0}
                },
                24
        );
    }
}
