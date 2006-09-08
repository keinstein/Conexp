/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl.tests;

import conexp.core.AttributeExplorer;
import conexp.core.Context;
import conexp.core.ImplicationSet;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.core.compareutils.ImplicationSetComparator;
import conexp.core.tests.ImplicationsBuilder;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

public class AttributeExplorerTest extends TestCase {

    private static void doTestAttributeExploration(int[][] context, int[][][] expQuerySequence, int[][][] contrExamples, int[][] expContext, int[][][] expImplicationTheory) {
        AttributeExplorer explorer = new AttributeExplorerImplementation();
        Context cxt = SetBuilder.makeContext(context);
        ImplicationSet implicationSet = new ImplicationSet(cxt);
        explorer.setContext(cxt);
        explorer.setImplicationSet(implicationSet);

        MockAttributeExplorerCallback attrExpCallback = new MockAttributeExplorerCallback();

        attrExpCallback.setExpSequence(MockAttributeExplorerCallback.makeAcceptImplicationInfoSequence(expQuerySequence));


        attrExpCallback.setContrExamples(MockAttributeExplorerCallback.makeContrExampleSequence(contrExamples));

        explorer.setUserCallback(attrExpCallback);
        explorer.performAttributeExploration();

        ImplicationSet expImplicationSet = ImplicationsBuilder.makeImplicationSet(cxt, expImplicationTheory);
        if (!implicationSet.equalsToIsomorphism(expImplicationSet)) {
            ImplicationSetComparator comparator = new ImplicationSetComparator(expImplicationSet, implicationSet);
            comparator.dumpDifferencesToSout();
            fail();
        }
        assertEquals(SetBuilder.makeRelation(expContext), cxt.getRelation());
    }

    public static void testFullAcceptanceModeOfAttributeExploration() {
        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{{{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}}};
        int[][][] contrExamples = new int[0][0][0];
        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 0}, {1, 1, 1}, {1}}};
        int[][] expContext = context;

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionMode() {

        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}},

                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 0}, {0, 1, 1}}
        };
        int[][][] contrExamples = new int[][][]{{{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}}};
        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 0}, {0, 1, 1}, {2}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                {0, 1, 1}};

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionModeWithoutImplicationWithEmptyPremise() {

        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}},
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {0, 1, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1}, {0, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 1, 0}, {0, 0, 1}}
        };
        int[][][] contrExamples = new int[][][]{{{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}},
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {1, 0, 0}}};

        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 1}, {0, 1, 0}, {2}},
                {{0, 1, 0}, {0, 0, 1}, {2}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                {0, 1, 1},
                {1, 0, 0}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionModeWithRejectionOfUsualClosureNormalWay() {

        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}},
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {0, 1, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1}, {0, 1, 0}},
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 1, 0}, {0, 0, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {1, 1, 0}, {0, 0, 1}}
        };
        int[][][] contrExamples = new int[][][]{
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}},
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {1, 0, 0}},
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 0}}
        };

        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 1}, {0, 1, 0}, {2}},
                {{1, 1, 0}, {0, 0, 1}, {1}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                {0, 1, 1},
                {1, 0, 0},
                {0, 1, 0}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionModeWithRejectionOfUsualClosure() {

        int[][] context = new int[][]{{0, 0, 0},
                {1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 1}, {1, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1}, {0, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 1, 0}, {0, 0, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {1, 0, 0}, {0, 1, 1}}
        };
        int[][][] contrExamples = new int[][][]{{{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}}};

        int[][][] expImplicationTheory = new int[][][]{
                {{0, 0, 1}, {0, 1, 0}, {2}},
                {{0, 1, 0}, {0, 0, 1}, {2}},
                {{1, 0, 0}, {0, 1, 1}, {1}}
        };
        int[][] expContext = new int[][]{{0, 0, 0},
                {1, 1, 1},
                {0, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionModeWithContrExampleNotContainingPremise() {

        int[][] context = new int[][]{{0, 0, 0},
                {1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 1}, {1, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1}, {0, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 1, 0}, {0, 0, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {1, 0, 0}, {0, 1, 1}}
        };
        int[][][] contrExamples = new int[][][]{
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {1, 0, 0}},
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}}
        };

        int[][][] expImplicationTheory = new int[][][]{
                {{0, 0, 1}, {0, 1, 0}, {2}},
                {{0, 1, 0}, {0, 0, 1}, {2}},
                {{1, 0, 0}, {0, 1, 1}, {1}}
        };
        int[][] expContext = new int[][]{{0, 0, 0},
                {1, 1, 1},
                {0, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public static void testInteractionModeWithContrExampleThatIsNotARealContrExample() {
        int[][] context = new int[][]{{0, 0, 0},
                {1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 1}, {1, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 1, 0}, {1, 0, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {1, 0, 0}, {0, 1, 1}}
        };

        int[][][] contrExamples = new int[][][]{
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {1, 1, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, null}
        };

        int[][][] expImplicationTheory = new int[][][]{
                {{0, 0, 1}, {1, 1, 0}, {1}},
                {{0, 1, 0}, {1, 0, 1}, {1}},
                {{1, 0, 0}, {0, 1, 1}, {1}}
        };

        int[][] expContext = new int[][]{{0, 0, 0},
                {1, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);

    }

    public static void testInteractionModeWithContrExampleViolatingAcceptedImplications() {


        int[][] context = new int[][]{{0, 0, 1, 1},
                {1, 1, 0, 0}};

        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 0, 1}, {0, 0, 1, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1, 0}, {0, 0, 0, 1}},
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 1, 0, 0}, {1, 0, 0, 0}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {1, 0, 0, 0}, {0, 1, 0, 0}}
        };

        int[][][] contrExamples = new int[][][]{
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 0, 1}},
                {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, null}
        };

        int[][][] expImplicationTheory = new int[][][]{
                {{0, 0, 0, 1}, {0, 0, 1, 0}, {1}},
                {{0, 0, 1, 0}, {0, 0, 0, 1}, {1}},
                {{0, 1, 0, 0}, {1, 0, 0, 0}, {1}},
                {{1, 0, 0, 0}, {0, 1, 0, 0}, {1}}
        };
        int[][] expContext = new int[][]{{0, 0, 1, 1},
                {1, 1, 0, 0}};

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }


    public static void testAttributeExplorationSequence() {
        int[][] context = {{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        int[][][] expQuerySequence = new int[][][]{
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 1, 1}, {1, 1, 0, 0}},
                {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 1, 0, 1}, {1, 0, 1, 0}},
                {{MockAttributeExplorerCallback.STOP}, {0, 1, 1, 0}, {1, 0, 0, 1}}
        };

        int[][][] contrExamples = new int[][][]{
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 0, 1, 1}},
                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 0, 1}}
        };

        int[][] expContext = new int[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 1, 0, 1}};

        int[][][] expImplicationTheory = new int[][][]{
        };
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);

    }

    //todo test for contradictory contr examples
    //todo test for user interuption

}
