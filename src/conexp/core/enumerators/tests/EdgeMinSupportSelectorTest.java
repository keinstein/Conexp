/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.Lattice;
import conexp.core.enumerators.EdgeMinSupportSelector;
import conexp.core.enumerators.SimpleEdgeIterator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class EdgeMinSupportSelectorTest extends TestCase {

    private static void doTestEdgeSelector(int[][] cxt, int minSupport, int[][][][] expEdges) {
        ExpectationSet exp = new ExpectationSet("expected edges");
        for (int i = 0; i < expEdges.length; i++) {
            exp.addExpected(SetBuilder.makeEdge(expEdges[i][0], expEdges[i][1]));
        }

        Lattice lat = SetBuilder.makeLattice(cxt);
        SimpleEdgeIterator selector = new EdgeMinSupportSelector(lat, minSupport);

        while (selector.hasNextEdge()) {
            exp.addActual(selector.nextEdge());
        }
        exp.verify();
    }

    public static void testAgainEdgeSelector() {
        int[][] cxt = new int[][]{
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1}};
        int minSupport = 3;
        int[][][][] expEdges = new int[][][][]{
                {
                        {{0, 0, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0}},
                        {{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}}
                },
                {
                        {{0, 0, 0, 0, 1, 1, 1}, {1, 0, 1, 0, 1}},
                        {{0, 0, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0}}
                },
                {
                        {{0, 1, 1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}},
                        {{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}}
                }
        };
        doTestEdgeSelector(cxt, minSupport, expEdges);

    }

    public static void testEdgeSelector() {
        int[][] cxt = new int[][]{{1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int minSupport = 2;
        int[][][][] expEdges = new int[][][][]{
                {
                        {{0, 1, 1}, {1, 1, 0}},
                        {{1, 1, 1}, {1, 0, 0}}
                }
        };

        doTestEdgeSelector(cxt, minSupport, expEdges);
    }
}
