package conexp.core.enumcallbacks.tests;

import conexp.core.BinaryRelation;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.enumcallbacks.ConceptSetCallback;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for NextClosedSetBuilderCallbackTest
 */

public class NextClosedSetBuilderCallbackTest extends TestCase {
    private static final Class THIS = NextClosedSetBuilderCallbackTest.class;

    public void testSmoke() {
        NextClosedSetCalculator calc = new NextClosedSetCalculator();
        BinaryRelation rel = SetBuilder.makeRelation(new int[][]{{1, 0, 0}, {1, 1, 0}, {0, 0, 1}});
        calc.setRelation(rel);
        Lattice lat = new Lattice();
        calc.setCallback(new conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback(lat));
        calc.calculateConceptSet();
        assertEquals(2, lat.getOne().getPredCount());
        assertEquals(2, lat.getZero().getSuccCount());
   }

    public static Test suite() {
        return new TestSuite(THIS);
    }


    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 16:48:03)
     */
    protected static Lattice makeLatticeForRelationByNextClosedSet(int[][] rel) {
        Lattice lat = new Lattice();
        NextClosedSetCalculator calc = new NextClosedSetCalculator();
        calc.setRelation(SetBuilder.makeRelation(rel));
        calc.setCallback(new ConceptSetCallback(lat));
        calc.calculateConceptSet();
        return lat;
    }


    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 16:49:28)
     */
    protected NextClosedSetLatticeBuilderCallback prepareCallbackForRelation(int[][] rel) {
        Lattice lat = makeLatticeForRelationByNextClosedSet(rel);

        NextClosedSetLatticeBuilderCallback callback = new NextClosedSetLatticeBuilderCallback(lat);
        return callback;
    }
}