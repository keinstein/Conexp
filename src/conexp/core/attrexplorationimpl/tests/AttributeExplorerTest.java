/*
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 */
package conexp.core.attrexplorationimpl.tests;

import conexp.core.AttributeExplorer;
import conexp.core.Context;
import conexp.core.ImplicationSet;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.core.compareutils.DefaultCompareInfoFactory;
import conexp.core.compareutils.DiffMap;
import conexp.core.compareutils.ImplicationSetCompareSet;
import conexp.core.compareutils.ImplicationSetComparator;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AttributeExplorerTest extends TestCase {
    private static final Class THIS = AttributeExplorerTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    private void doTestAttributeExploration(int[][] context, int[][][] expQuerySequence, int[][][] contrExamples, int[][] expContext, int[][][] expImplicationTheory) {
        AttributeExplorer explorer = new AttributeExplorerImplementation();
        Context cxt = SetBuilder.makeContext(context);
        ImplicationSet implicationSet = new ImplicationSet(cxt);
        explorer.setContext(cxt);
        explorer.setImplicationSet(implicationSet);

        MockAttributeExplorerCallback attrExpCallback = new MockAttributeExplorerCallback();

        attrExpCallback.setExpSequence(MockAttributeExplorerCallback.makeAcceptImplicationInfoSequence(
                expQuerySequence));


        attrExpCallback.setContrExamples(MockAttributeExplorerCallback.makeContrExampleSequence(
                contrExamples
        ));

        explorer.setUserCallback(attrExpCallback);
        explorer.performAttributeExploration();

        ImplicationSet expImplicationSet = SetBuilder.makeImplicationSet(cxt, expImplicationTheory);
        if (!implicationSet.equalsToIsomorphism(expImplicationSet)) {
            ImplicationSetComparator comparator = new ImplicationSetComparator(expImplicationSet, implicationSet);
            comparator.dumpDifferencesToSout();
            fail();
        }
        assertEquals(SetBuilder.makeRelation(expContext), cxt.getRelation());
    }

    public void testFullAcceptanceModeOfAttributeExploration() {
        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{{{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}}};
        int[][][] contrExamples = new int[0][0][0];
        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 0}, {1, 1, 1}}};
        int[][] expContext = context;

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionMode() {

        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
            {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}},

            {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 0}, {0, 1, 1}}
        };
        int[][][] contrExamples = new int[][][]{{{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}}};
        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 0}, {0, 1, 1}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                                         {0, 1, 1}};

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionModeWithoutImplicationWithEmptyPremise() {

        int[][] context = new int[][]{{1, 1, 1}};
        int[][][] expQuerySequence = new int[][][]{
            {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {1, 1, 1}},
            {{MockAttributeExplorerCallback.REJECT_IMPLICATION}, {0, 0, 0}, {0, 1, 1}},
            {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 0, 1}, {0, 1, 0}},
            {{MockAttributeExplorerCallback.ACCEPT_IMPLICATION}, {0, 1, 0}, {0, 0, 1}}
        };
        int[][][] contrExamples = new int[][][]{{{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {0, 1, 1}},
                                                {{MockAttributeExplorerCallback.HAS_CONTREXAMPLE}, {1, 0, 0}}};

        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 1}, {0, 1, 0}},
                                                       {{0, 1, 0}, {0, 0, 1}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                                         {0, 1, 1},
                                         {1, 0, 0}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionModeWithRejectionOfUsualClosureNormalWay() {

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

        int[][][] expImplicationTheory = new int[][][]{{{0, 0, 1}, {0, 1, 0}},
                                                       {{1, 1, 0}, {0, 0, 1}}};
        int[][] expContext = new int[][]{{1, 1, 1},
                                         {0, 1, 1},
                                         {1, 0, 0},
                                         {0, 1, 0}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionModeWithRejectionOfUsualClosure() {

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
            {{0, 0, 1}, {0, 1, 0}},
            {{0, 1, 0}, {0, 0, 1}},
            {{1, 0, 0}, {0, 1, 1}}
        };
        int[][] expContext = new int[][]{{0, 0, 0},
                                         {1, 1, 1},
                                         {0, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionModeWithContrExampleNotContainingPremise() {

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
            {{0, 0, 1}, {0, 1, 0}},
            {{0, 1, 0}, {0, 0, 1}},
            {{1, 0, 0}, {0, 1, 1}}
        };
        int[][] expContext = new int[][]{{0, 0, 0},
                                         {1, 1, 1},
                                         {0, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }

    public void testInteractionModeWithContrExampleThatIsNotARealContrExample() {
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
            {{0, 0, 1}, {1, 1, 0}},
            {{0, 1, 0}, {1, 0, 1}},
            {{1, 0, 0}, {0, 1, 1}}
        };

        int[][] expContext = new int[][]{{0, 0, 0},
                                         {1, 1, 1}};
        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);

    }

    public void testInteractionModeWithContrExampleViolatingAcceptedImplications() {


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
            {{0, 0, 0, 1}, {0, 0, 1, 0}},
            {{0, 0, 1, 0}, {0, 0, 0, 1}},
            {{0, 1, 0, 0}, {1, 0, 0, 0}},
            {{1, 0, 0, 0}, {0, 1, 0, 0}}
        };
        int[][] expContext = new int[][]{{0, 0, 1, 1},
                                         {1, 1, 0, 0}};

        doTestAttributeExploration(context, expQuerySequence, contrExamples, expContext, expImplicationTheory);
    }


    public void testAttributeExplorationSequence() {
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
