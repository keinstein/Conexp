/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class IdealEnumeratorTest extends TestCase {
    private static final Class THIS = IdealEnumeratorTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 0:47:15)
     */
    private void testEnumerator(Lattice lat, int[] elIntent, int[][] expEnum) {
        LatticeElement el = SetBuilder.findLatticeElementWithIntent(lat, elIntent);
        ExpectationSet expSet = SetBuilder.makeExpectationSetForIntents("extectedEnumeration", expEnum);
        fillActualIntentsFromLatticeEnumerator((makeIterator(el, lat)), expSet);
        expSet.verify();
    }

    public void testExp() {
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

    public void testLinear() {
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
     * @param enum conexp.core.LatticeEnumerator
     * @param expSet com.xpdeveloper.testing.ExpectationSet
     */
    protected static void fillActualIntentsFromLatticeEnumerator(ConceptIterator enum, ExpectationSet expSet) {
        while (enum.hasNext()) {
            expSet.addActual(enum.nextConcept().getAttribs());
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.03.01 3:01:00)
     */
    public ConceptIterator makeIterator(LatticeElement el, Lattice lat) {
        return new ConceptIdealIterator(el);
    }
}
