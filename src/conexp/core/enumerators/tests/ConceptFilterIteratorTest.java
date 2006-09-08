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
import conexp.core.enumerators.ConceptFilterIterator;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class ConceptFilterIteratorTest extends TestCase {


    public static void testExp() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{1, 0},
                {0, 1}}, new DepthSearchCalculator());
        testFilterEnumerator(lat, new int[]{0, 0}, new int[][]{{0, 0}});
        testFilterEnumerator(lat, new int[]{0, 1}, new int[][]{{0, 0},
                {0, 1}});
        testFilterEnumerator(lat, new int[]{1, 0}, new int[][]{{0, 0},
                {1, 0}});
        testFilterEnumerator(lat, new int[]{1, 1}, new int[][]{{0, 0},
                {1, 0},
                {0, 1},
                {1, 1}});
    }


    public static void testExp3() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});
        int count = calculateElementsInFilterIterator(lat.getBottom());
        assertEquals(8, count);
    }

    public static void testExp4() {
        final int[][] relation = new int[][]{{0, 1, 1, 1},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 0}};
        Lattice lat = SetBuilder.makeLattice(relation);
        int count = calculateElementsInFilterIterator(lat.getBottom());
        assertEquals(16, count);
    }

    public static void testSpecialCaseForIncALgorithm() {
        int[][] rel = {
                {1, 0, 1, 0, 0},
                {0, 1, 1, 1, 0},
                //{1, 0, 1, 1, 0},
                // {0, 1, 0, 0, 1},
                // {1, 0, 1, 0, 0},
                // {0, 0, 1, 0, 0},
                // {1, 0, 0, 1, 0},
                //{0, 1, 0, 1, 1},
                //{0, 0, 1, 0, 1},
                // {0, 1, 1, 0, 0},
                //  {0, 0, 0, 0, 1},
                //  {1, 1, 1, 1, 1},
        };
        Lattice lat = SetBuilder.makeLattice(rel);
        int count = calculateElementsInFilterIterator(lat.getBottom());
        assertEquals(lat.conceptsCount(), count);
    }


    private static int calculateElementsInFilterIterator(final LatticeElement bottom) {
        ConceptFilterIterator iterator = new ConceptFilterIterator(bottom);
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 0:47:15)
     */
    private static void testFilterEnumerator(Lattice lat, int[] elIntent, int[][] expFilter) {
        LatticeElement el = SetBuilder.findLatticeElementWithIntent(lat, elIntent);
        assertNotNull("Couldn't find expected lattice element", el);
        ExpectationSet expSet = SetBuilder.makeExpectationSetForIntents("extectedFilter", expFilter);
        fillActualIntentsFromLatticeEnumerator(new ConceptFilterIterator(el), expSet);
        expSet.verify();
    }

    public static void testLinear() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {1, 1, 1}}, new DepthSearchCalculator());
        testFilterEnumerator(lat, new int[]{1, 0, 0}, new int[][]{{1, 0, 0}});
        testFilterEnumerator(lat, new int[]{1, 1, 0}, new int[][]{{1, 0, 0},
                {1, 1, 0}});
        testFilterEnumerator(lat, new int[]{1, 1, 1}, new int[][]{{1, 0, 0},
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
