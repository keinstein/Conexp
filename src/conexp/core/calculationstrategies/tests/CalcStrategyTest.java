/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.BinaryRelation;
import conexp.core.Concept;
import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.compareutils.ConceptCollectionCompareSet;
import conexp.core.compareutils.DefaultCompareInfoFactory;
import conexp.core.compareutils.DiffMap;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

public abstract class CalcStrategyTest extends TestCase {

    protected ConceptCalcStrategy calcStrategy;
    protected ConceptsCollection conceptSet;

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
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {1, 1, 0},
            {0, 1, 1},
            {1, 0, 1}
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
        int[][] linear = new int[][]{
            {0, 0, 0, 1},
            {0, 0, 1, 1},
            {0, 1, 1, 1},
            {1, 1, 1, 1}
        };
        doTestCalcStrategyForExpectedIntentsAndExtents(linear, linear, linear, 3);
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.02.01 1:30:42)
     */
    public void testNominal() {
        doTestCalcStrategyForExpectedIntentsAndExtents(
                new int[][]{
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
                },
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
        int[][] linear = new int[][]{
            {1, 0},
            {1, 1}
        };
        doTestCalcStrategyForExpectedIntentsAndExtents(linear, linear, new int[][]{{1, 1}, {0, 1}}, 1);
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
            DiffMap map = new DiffMap(new DefaultCompareInfoFactory());
            if (!map.compareSets(new ConceptCollectionCompareSet(SetBuilder.makeConceptSet(input)),
                    new ConceptCollectionCompareSet(conceptSet))) {
                map.dumpDifferences(new java.io.PrintWriter(System.out, true));
            }
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
        int[][] rel = {
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
        doTestCalcStrategyForExpectedIntentsAndExtents(rel, expIntents, expextedExtents, 43);

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
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {1, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 1},
            {0, 1, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 1, 1, 0, 1, 0, 0}
        },
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
