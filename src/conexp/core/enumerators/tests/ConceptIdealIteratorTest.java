/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.ConceptIterator;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumerators.ConceptIdealIterator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class ConceptIdealIteratorTest extends TestCase {
    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 0:47:15)
     */
    private static void testEnumerator(Lattice lat, int[] elIntent, int[][] expEnum) {
        LatticeElement el = SetBuilder.findLatticeElementWithIntent(lat, elIntent);
        ExpectationSet expSet = SetBuilder.makeExpectationSetForIntents("extectedEnumeration", expEnum);
        fillActualIntentsFromLatticeEnumerator(new ConceptIdealIterator(el), expSet);
        expSet.verify();
    }

    public static void testExp() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{1, 0},
                {0, 1}}, new DepthSearchCalculator());
        testEnumerator(lat, new int[]{1, 1}, new int[][]{{1, 1}});
        testEnumerator(lat, new int[]{0, 1}, new int[][]{{1, 1},
                {0, 1}});
        testEnumerator(lat, new int[]{1, 0}, new int[][]{{1, 1},
                {1, 0}});
        testEnumerator(lat, new int[]{0, 0}, new int[][]{{0, 0},
                {1, 0},
                {0, 1},
                {1, 1}});

    }

    public static void testLinear() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {1, 1, 1}}, new DepthSearchCalculator());
        testEnumerator(lat, new int[]{1, 1, 1}, new int[][]{{1, 1, 1}});
        testEnumerator(lat, new int[]{1, 1, 0}, new int[][]{{1, 1, 0},
                {1, 1, 1}});
        testEnumerator(lat, new int[]{1, 0, 0}, new int[][]{{1, 0, 0},
                {1, 1, 0},
                {1, 1, 1}});

    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 0:33:07)
     *
     * @param iter   conexp.core.LatticeEnumerator
     * @param expSet com.xpdeveloper.testing.ExpectationSet
     */
    private static void fillActualIntentsFromLatticeEnumerator(ConceptIterator iter, ExpectationSet expSet) {
        while (iter.hasNext()) {
            expSet.addActual(iter.nextConcept().getAttribs());
        }
    }

}
