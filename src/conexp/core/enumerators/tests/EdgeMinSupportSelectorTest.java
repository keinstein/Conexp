package conexp.core.enumerators.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.Lattice;
import conexp.core.enumerators.EdgeMinSupportSelector;
import conexp.core.enumerators.SimpleEdgeIterator;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for EdgeSelectorTest
 */

public class EdgeMinSupportSelectorTest extends TestCase {
    private static final Class THIS = EdgeMinSupportSelectorTest.class;

    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 23:40:55)
     */
    protected static void doTestEdgeSelector(int[][] cxt, int minSupport, int[][][][] expEdges) {
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

    public static Test suite() {
        return new TestSuite(THIS);
    }

    /**
     * Insert the method's description here.
     * Creation date: (20.06.01 1:42:58)
     */
    public void testAgainEdgeSelector() {
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

    public void testEdgeSelector() {
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