package conexp.core.compareutils.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.compareutils.ConceptCollectionCompareSet;
import conexp.core.compareutils.DefaultCompareInfoFactory;
import conexp.core.compareutils.DiffMap;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for DiffMapTest
 */

public class DiffMapTest extends TestCase {
    private static final Class THIS = DiffMapTest.class;

    protected static void compareConceptCollection(ConceptsCollection one, ConceptsCollection two, boolean expEquals, boolean expFirst, boolean expSecond, boolean expCommon) {
        DiffMap map = new DiffMap(new DefaultCompareInfoFactory());
        boolean ret = map.compareSets(new ConceptCollectionCompareSet(one), new ConceptCollectionCompareSet(two));
        assertEquals(expEquals, ret);
        assertEquals(expFirst, !map.getInFirst().isEmpty());
        assertEquals(expSecond, !map.getInSecond().isEmpty());
        assertEquals(expCommon, !map.getInBothButDifferent().isEmpty());
    }



    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testEquals() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{0, 0}, {0, 0}});
        compareConceptCollection(lat, lat, true, false, false, false);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 20:36:04)
     */
    public void testUnequal() {
        Lattice one = SetBuilder.makeLattice(new int[][]{{0, 0}, {1, 0}});
        Lattice two = SetBuilder.makeLattice(new int[][]{{0, 0}, {1, 1}});
        compareConceptCollection(one, two, false, true, false, false);
        compareConceptCollection(two, one, false, false, true, false);

        two = SetBuilder.makeLattice(new int[][]{{0, 0}, {0, 1}});
        compareConceptCollection(one, two, false, true, true, false);
        compareConceptCollection(two, one, false, true, true, false);
    }
}