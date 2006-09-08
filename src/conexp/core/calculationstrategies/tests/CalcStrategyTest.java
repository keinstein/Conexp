/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.BinaryRelation;
import conexp.core.Concept;
import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.ModifiableSet;
import conexp.core.compareutils.ConceptCollectionComparator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

public abstract class CalcStrategyTest extends TestCase {

    protected ConceptCalcStrategy calcStrategy;
    protected ConceptsCollection conceptSet;
    private static final int[][] ONE_NODE_LATTICE = new int[][]{
            {1}
    };

    private static final int[][] TWO_NODE_LATTICE = new int[][]{
            {0}
    };
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
    private static final int[][] BAD_NOURINE_RAYNAUD = new int[][]{
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
    private static final int[][] LECTICAL_TREE_ERROR_CONTEXT = new int[][]{
            {1, 0, 1, 1, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 0, 0, 1}
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

    public void testOneNodeLattice() {
        doTestCalcStrategyForExpectedIntentsAndExtents(ONE_NODE_LATTICE, new int[][]{{1}},
                new int[][]{{1}},
                0);

    }


    public void testTwoNodeLattice() {
        doTestCalcStrategyForExpectedIntentsAndExtents(TWO_NODE_LATTICE, new int[][]{{0}, {1}},
                new int[][]{{1}, {0}},
                1);

    }

    public void testBuildLexicograficalTree() {
        doTestCalcStrategyForExpectedIntentsAndExtents(LECTICAL_TREE_ERROR_CONTEXT,
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
                12);
    }

    private void doTestCalcStrategyForExpectedConcepts(int[][] input, int[][][] concepts, int edgeCount) {
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        setupResultCollectorAndStrategy();
        generateIntents();
        checkResults(rel, concepts);
        checkEdgeCount(rel, edgeCount);
    }

    protected void checkResults(BinaryRelation rel, int[][][] concepts) {
        testIntentsAndExtentsSizes(conceptSet, rel);
        checkConcepts(concepts, conceptSet);

    }

    private static void checkConcepts(int[][][] concepts, final ConceptsCollection actualConcepts) {
        ConceptsCollection expConcepts = new ConceptsCollection();
        for (int i = 0; i < concepts.length; i++) {
            int[][] currentConcept = concepts[i];
            assertEquals(2, currentConcept.length);
            ModifiableSet extent = SetBuilder.makeSet(currentConcept[0]);
            ModifiableSet intent = SetBuilder.makeSet(currentConcept[1]);
            expConcepts.addElement(new Concept(extent, intent));
        }
        ConceptCollectionComparator comparator = new ConceptCollectionComparator(expConcepts, actualConcepts);
        if (!comparator.isEqual()) {
            comparator.dumpDifferencesToSout();
            assertTrue("concept set compare failed expected" + expConcepts + " but was " + actualConcepts, false);
        }
    }

    protected void setupResultCollectorAndStrategy() {
        conceptSet = makeConceptCollection();
        setupStrategy(conceptSet);
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
                12);
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
        doTestCalcStrategyForExpectedConcepts(NOMINAL_4_NODE_4_ATTR,
                new int[][][]{
                        {{1, 1, 1, 1}, {0, 0, 0, 0}},
                        {{0, 0, 0, 0}, {1, 1, 1, 1}},
                        {{0, 0, 0, 1}, {0, 0, 0, 1}},
                        {{0, 0, 1, 0}, {0, 0, 1, 0}},
                        {{0, 1, 0, 0}, {0, 1, 0, 0}},
                        {{1, 0, 0, 0}, {1, 0, 0, 0}},
                }
                , 8);
        doTestCalcStrategyForExpectedIntentsAndExtents(NOMINAL_4_NODE_4_ATTR,
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
                8);
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

    protected void buildIntentsSetAndFillExpectationSet(BinaryRelation rel, ExpectationSet expSet, ExpectationSet expSetExtents) {
        conceptSet = makeConceptCollection();
        setupStrategy(conceptSet);
        generateIntents();
        testIntentsAndExtentsSizes(conceptSet, rel);
        ConceptSetTestUtils.fillExpectationSetByIntentsFromLattice(expSet, conceptSet);
        ConceptSetTestUtils.fillExpectationSetByExtentsFromLattice(expSetExtents, conceptSet);
    }


    protected void doTestCalcStrategyForExpectedIntentsAndExtents(int[][] input, int[][] expOutputIntent, int[][] expOutputExtents, int expectedEdgeCount) {
        ExpectationSet expectationSetForIntents = SetBuilder.makeExpectationSetForIntents("Expected Intents", expOutputIntent);
        ExpectationSet expectationSetForExtents = SetBuilder.makeExpectationSetForIntents("Expected Extents", expOutputExtents);
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        buildIntentsSetAndFillExpectationSet(rel, expectationSetForIntents, expectationSetForExtents);
        expectationSetForIntents.verify();
        expectationSetForExtents.verify();
        checkEdgeCount(rel, expectedEdgeCount);
    }

    /**
     * this method should be overriden if one would like to check for expected edge count
     *
     * @param expectedEdgeCount
     */
    protected void checkEdgeCount(BinaryRelation rel, int expectedEdgeCount) {

    }


    protected void doTestCalcStrategyForExpectedSizeForFullLatticeCase(int[][] input, int expectedSize) {
        ConceptsCollection conceptsCollection = buildConceptCollection(input);
        if (conceptsCollection.conceptsCount() != expectedSize) {
            ConceptCollectionComparator comparator = new ConceptCollectionComparator(SetBuilder.makeConceptSet(input),
                    conceptsCollection);
            comparator.dumpDifferencesToSout();
            assertEquals(expectedSize, conceptsCollection.conceptsCount());
        }
    }

    private ConceptsCollection buildConceptCollection(int[][] input) {
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        ConceptsCollection conceptsCollection = makeConceptCollection();
        setupStrategy(conceptsCollection);
        calcStrategy.calculateConceptSet();
        return conceptsCollection;
    }

    protected void generateIntents() {
        calcStrategy.calculateConceptSet();
    }


    protected ConceptsCollection makeConceptCollection() {
        return new ConceptsCollection();
    }


    protected abstract void setupStrategy(ConceptsCollection coll);


    public void testBadDepthSearchCalc() {
        int[][] expIntents = {
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
        int[][] expextedExtents = {
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
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(BAD_NOURINE_RAYNAUD, 3381);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 22:02:30)
     */
    private static void testIntentsAndExtentsSizes(ConceptsCollection conceptSet, BinaryRelation rel) {
        final int rowCount = rel.getRowCount();
        final int colCount = rel.getColCount();
        for (int i = conceptSet.conceptsCount(); --i >= 0;) {
            Concept con = conceptSet.conceptAt(i);
            assertEquals(rowCount, con.getObjects().size());
            assertEquals(colCount, con.getAttribs().size());
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
                24);
    }

    public void testCharmExample() {
        doTestCalcStrategyForExpectedConcepts(new int[][]{
                {1, 1, 0, 1, 1},
                {0, 1, 1, 0, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0}
        },
                new int[][][]{
                        {{1, 1, 1, 1, 1, 1}, {0, 1, 0, 0, 0}},
                        {{0, 0, 0, 0, 1, 0}, {1, 1, 1, 1, 1}},
                        {{1, 1, 1, 1, 1, 0}, {0, 1, 0, 0, 1}},
                        {{0, 1, 0, 1, 1, 0}, {0, 1, 1, 0, 1}},
                        {{0, 0, 0, 1, 1, 0}, {1, 1, 1, 0, 1}},
                        {{1, 0, 1, 1, 1, 0}, {1, 1, 0, 0, 1}},
                        {{1, 0, 1, 0, 1, 0}, {1, 1, 0, 1, 1}},
                        {{1, 0, 1, 0, 1, 1}, {0, 1, 0, 1, 0}},
                        {{0, 0, 0, 0, 1, 1}, {0, 1, 1, 1, 0}},
                        {{0, 1, 0, 1, 1, 1}, {0, 1, 1, 0, 0}},
                }
                , 15);

    }
}
