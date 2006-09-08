/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.Context;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.ModifiableBinaryRelation;

public abstract class ImplicationCalculatorTest extends ImplicationCalculatorBaseTest {


    public void testFindImplicationsForOneNodeLattice() {
        int[][] relation = new int[][]{{1, 1, 1}};
        int[][][] expImplicationsDescriptions = new int[][][]{{{0, 0, 0}, {1, 1, 1}, {1}}};

        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testFindImplicationsForOrderLattice() {
        int[][] relation = new int[][]{{0, 0, 1},
                {0, 1, 1},
                {1, 1, 1}};
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0}, {0, 0, 1}, {3}},
                {{1, 0, 1}, {0, 1, 0}, {1}},
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testFindImplicationsForLatticeWithEquivalentAttributes() {
        int[][] relation = new int[][]{{0, 0, 0, 1},
                {0, 0, 1, 1},
                {1, 1, 1, 1}};
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 0}, {0, 0, 0, 1}, {3}},
                {{0, 1, 0, 1}, {1, 0, 1, 0}, {1}},
                {{1, 0, 0, 1}, {0, 1, 1, 0}, {1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testFindImplicationsWithSeveralEquivalentAttributes() {
        int[][] relation = new int[][]{{0, 0, 0, 0, 1},
                {0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1}};
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {3}},
                {{0, 0, 0, 1, 1}, {0, 0, 1, 0, 0}, {2}},
                {{0, 0, 1, 0, 1}, {0, 0, 0, 1, 0}, {2}},
                {{0, 1, 0, 0, 1}, {1, 0, 1, 1, 0}, {1}},
                {{1, 0, 0, 0, 1}, {0, 1, 1, 1, 0}, {1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testFindImplicationForNominalCaseWithFullUnitElement() {
        int[][] relation = new int[][]{{0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 1}
        };
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 1, 1}, {1, 0, 0}, {1}},
                {{1, 0, 1}, {0, 1, 0}, {1}},
                {{1, 1, 0}, {0, 0, 1}, {1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testFindImplicationsForBooleanLattice() {
        int[][] relation = new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };

        int[][][] expImplicationsDescriptions = new int[0][0][0];

        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testForCaseOfTwoJoiningBranchesThatProduceInteractactionImplicationWithTheThirdBranch() {
        int[][] relation = new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 1}
        };
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{1, 0, 1}, {0, 1, 0}, {1}},
                {{1, 1, 0}, {0, 0, 1}, {1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testImplicationCalculatorForFailing3() {
        int[][] relation = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0}
        };
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {1}},
                {{0, 0, 0, 1, 0}, {0, 0, 1, 0, 0}, {1}},
                {{0, 0, 0, 0, 1}, {1, 1, 1, 1, 0}, {0}},
                {{0, 1, 0, 0, 0}, {1, 0, 1, 1, 1}, {0}},
                {{1, 0, 0, 0, 0}, {0, 1, 1, 1, 1}, {0}},

        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testImplicationCalculatorForFailing4() {
        int[][] relation = new int[][]{
                {0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 1, 1}, {1, 1, 1, 0, 0}, {0}},
                {{0, 0, 1, 0, 1}, {1, 1, 0, 1, 0}, {0}},
                {{0, 0, 1, 1, 0}, {1, 1, 0, 0, 1}, {0}},
                {{0, 1, 0, 0, 0}, {1, 0, 1, 1, 1}, {0}},
                {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testImplicationCalculatorGeneratingInteractingForCaseWhenNodesHasCommonPart() {
        int[][] relation = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };

        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 1, 0, 1}, {1, 1, 0, 1, 0}},
                {{0, 1, 0, 0, 0}, {1, 0, 1, 1, 1}},
                {{1, 0, 0, 0, 0}, {0, 1, 1, 1, 1}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testImplicationCalculatorFailing6() {
        int[][] relation = new int[][]{
                {0, 1, 0, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 1, 0, 1, 0}
        };

        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 0, 1}, {0, 0, 1, 0, 0}, {1}},
                {{0, 0, 1, 1, 1}, {1, 1, 0, 0, 0}, {0}},
                {{0, 1, 0, 1, 0}, {1, 0, 0, 0, 0}, {1}},
                {{0, 1, 1, 0, 0}, {1, 0, 0, 1, 1}, {0}},
                {{1, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1}}

        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }

    public void testImplicationCalculatorRandomFailure7() {
        int[][] relation = new int[][]{
                {0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0}
        };

        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 0, 1}, {0, 0, 0, 1, 0}, {1}},
                {{0, 0, 1, 1, 0}, {1, 0, 0, 0, 0}, {1}},
                {{0, 1, 0, 1, 1}, {1, 0, 1, 0, 0}, {0}},
                {{0, 1, 1, 0, 0}, {1, 0, 0, 1, 1}, {0}},
                {{1, 0, 0, 1, 0}, {0, 0, 1, 0, 0}, {1}},
                {{1, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {1}},
                {{1, 0, 1, 1, 1}, {0, 1, 0, 0, 0}, {0}}

        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


    public void testBuildImplicationsWithContextWithSmallAttributeSetAndBigObjectSet() {
        ModifiableBinaryRelation rel = ContextFactoryRegistry.createRelation(14, 14);
        for (int i = 0; i < rel.getRowCount(); i++) {
            for (int j = 0; j < rel.getColCount(); j++) {
                if (j >= i) {
                    rel.setRelationAt(i, j, true);
                }
            }
        }
        rel.setDimension(120, 14);
        Context cxt = new Context(rel);
        ImplicationCalcStrategy calc = makePreparedCalculator(cxt);
        calc.setImplications(new ImplicationSet(cxt));
        calc.calcImplications();
    }

    public void testFindImplicationsFailingRandomTest2() {
        int[][] relation = new int[][]{
                {0, 0, 0, 0, 1},
                {0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0},
        };
        int[][][] expImplicationsDescriptions = new int[][][]{
                {{0, 0, 0, 1, 0}, {0, 0, 0, 0, 1}, {1}},
                {{0, 0, 1, 0, 1}, {1, 1, 0, 1, 0}, {0}},
                {{0, 1, 0, 0, 0}, {1, 0, 1, 1, 1}, {0}},
                {{1, 0, 0, 0, 0}, {0, 1, 1, 1, 1}, {0}}
        };
        doTestImplicationCalculator(relation, expImplicationsDescriptions);
    }


}
