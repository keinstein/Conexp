/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.core.layout.DefaultLayoutParameters;
import conexp.core.layout.FreeseLayout;
import conexp.core.layout.FreezeBaseLayout;
import conexp.core.layout.GenericLayouter;
import conexp.core.tests.SetBuilder;

import java.util.Collection;
import java.util.Iterator;


public class FreezeLayoutTest extends SimpleForceLayoutTest {
    private FreezeBaseLayout fLayout;
    private Lattice lat;

    private static void checkIncomparablesForIntent(FreezeBaseLayout fLayout, Lattice lat, int[] intent, int[][] expHighIncomparables) {
        Set sIntent = SetBuilder.makeSet(intent);
        ExpectationSet expSet = makeExpSetFromLatticeAndIntents("expSet for " + sIntent, lat, expHighIncomparables);
        fillExpectationSetFromCollection(expSet, fLayout.getHighIncomparablesForConcept(SetBuilder.findLatticeElementWithIntent(lat, intent)));
        expSet.verify();
    }

    private static void checkLowIncomparablesForIntent(FreezeBaseLayout fLayout, Lattice lat, int[] intent, int[][] expHighIncomparables) {
        Set sIntent = SetBuilder.makeSet(intent);
        ExpectationSet expSet = makeExpSetFromLatticeAndIntents("expSet for " + sIntent, lat, expHighIncomparables);
        fillExpectationSetFromCollection(expSet, fLayout.getLowIncomparablesForConcept(SetBuilder.findLatticeElementWithIntent(lat, intent)));
        expSet.verify();
    }

    private void checkRankForConceptWithIntent(int[] intent, int expRank) {
        assertEquals(expRank, fLayout.getConceptInfo(findElementForIntent(lat, intent)).rank);
    }

    private static void fillExpectationSetFromCollection(ExpectationSet expSet, Collection actual) {
        Iterator iter = actual.iterator();
        while (iter.hasNext()) {
            expSet.addActual(iter.next());
        }
    }

    private static LatticeElement findElementForIntent(Lattice lat, int[] currIntent) {
        return SetBuilder.findLatticeElementWithIntent(lat, currIntent);
    }

    private static ExpectationSet makeExpSetFromLatticeAndIntents(String name, Lattice lat, int[][] intents) {
        ExpectationSet expSet = new ExpectationSet(name);
        for (int i = intents.length; --i >= 0;) {
            expSet.addExpected(findElementForIntent(lat, intents[i]));
        }
        return expSet;
    }

    protected void setUp() {
        fLayout = new FreeseLayout();
        lat = SetBuilder.makeLattice(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {0, 0, 1}});
        fLayout.initLayout(lat, new DefaultLayoutParameters());
        fLayout.calcInitialPlacement();
    }

    public void testHighIncomparables() {
        assertEquals(5, lat.conceptsCount());

        checkIncomparablesForIntent(fLayout, lat, new int[]{0, 0, 0}, new int[0][0]);
        checkIncomparablesForIntent(fLayout, lat, new int[]{1, 0, 0}, new int[0][0]);
        checkIncomparablesForIntent(fLayout, lat, new int[]{1, 1, 0}, new int[][]{{0, 0, 1}});
        checkIncomparablesForIntent(fLayout, lat, new int[]{0, 0, 1}, new int[][]{{1, 0, 0}});
        checkIncomparablesForIntent(fLayout, lat, new int[]{1, 1, 1}, new int[0][0]);
    }

    public void testLowIncomparables() {
        assertEquals(5, lat.conceptsCount());

        checkLowIncomparablesForIntent(fLayout, lat, new int[]{0, 0, 0}, new int[0][0]);
        checkLowIncomparablesForIntent(fLayout, lat, new int[]{1, 0, 0}, new int[][]{{0, 0, 1}});
        checkLowIncomparablesForIntent(fLayout, lat, new int[]{1, 1, 0}, new int[0][0]);
        checkLowIncomparablesForIntent(fLayout, lat, new int[]{0, 0, 1}, new int[][]{{1, 1, 0}});
        checkLowIncomparablesForIntent(fLayout, lat, new int[]{1, 1, 1}, new int[0][0]);
    }

    public void testRanks() {
        assertEquals(5, lat.conceptsCount());
        checkRankForConceptWithIntent(new int[]{0, 0, 0}, 6);
        checkRankForConceptWithIntent(new int[]{1, 0, 0}, 4);
        checkRankForConceptWithIntent(new int[]{1, 1, 0}, 2);
        checkRankForConceptWithIntent(new int[]{0, 0, 1}, 3);
        checkRankForConceptWithIntent(new int[]{1, 1, 1}, 0);

    }

    protected GenericLayouter makeLayouter() {
        return new FreeseLayout();
    }
}
