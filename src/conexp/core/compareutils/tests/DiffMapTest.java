/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.compareutils.ConceptCollectionCompareSet;
import conexp.core.compareutils.DefaultCompareInfoFactory;
import conexp.core.compareutils.DiffMap;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class DiffMapTest extends TestCase {
    protected static void compareConceptCollection(ConceptsCollection one, ConceptsCollection two, boolean expEquals, boolean expFirst, boolean expSecond, boolean expCommon) {
        DiffMap map = new DiffMap(DefaultCompareInfoFactory.getInstance());
        boolean ret = map.compareSets(new ConceptCollectionCompareSet(one), new ConceptCollectionCompareSet(two));
        assertEquals(expEquals, ret);
        assertEquals(expFirst, !map.getInFirst().isEmpty());
        assertEquals(expSecond, !map.getInSecond().isEmpty());
        assertEquals(expCommon, !map.getInBothButDifferent().isEmpty());
    }


    public static void testEquals() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{0, 0}, {0, 0}});
        compareConceptCollection(lat, lat, true, false, false, false);
    }

    public static void testUnequal() {
        Lattice one = SetBuilder.makeLattice(new int[][]{{0, 0}, {1, 0}});
        Lattice two = SetBuilder.makeLattice(new int[][]{{0, 0}, {1, 1}});
        compareConceptCollection(one, two, false, true, false, true);
        compareConceptCollection(two, one, false, false, true, true);

        two = SetBuilder.makeLattice(new int[][]{{0, 0}, {0, 1}});
        compareConceptCollection(one, two, false, true, true, false);
        compareConceptCollection(two, one, false, true, true, false);
    }
}
