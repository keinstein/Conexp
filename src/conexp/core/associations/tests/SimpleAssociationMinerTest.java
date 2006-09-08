/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import com.mockobjects.ExpectationList;
import conexp.core.Lattice;
import conexp.core.associations.AssociationMiner;
import conexp.core.associations.BaseAssociationMiner;
import conexp.core.associations.SimpleAssociationMiner;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Iterator;
import java.util.List;


public class SimpleAssociationMinerTest extends AssociationMinerTest {
    private static final Class THIS = SimpleAssociationMinerTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected AssociationMiner makeAssociationMiner() {
        return new SimpleAssociationMiner();
    }

    public static void testEdgeSelector() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1}});

        List edges = BaseAssociationMiner.findFrequentEdgesSortedByConfidence(lat, 0.40, 3);

        ExpectationList expRules = new ExpectationList("ExpectedRules");
        expRules.addExpected(SetBuilder.makeEdge(new int[][]{{0, 0, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0}},
                new int[][]{{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}}));
        expRules.addExpected(SetBuilder.makeEdge(new int[][]{{0, 0, 0, 0, 1, 1, 1}, {1, 0, 1, 0, 1}},
                new int[][]{{0, 0, 1, 1, 1, 1, 1}, {1, 0, 1, 0, 0}}));
        expRules.addExpected(SetBuilder.makeEdge(new int[][]{{0, 1, 1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}},
                new int[][]{{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}}));
        Iterator iter = edges.iterator();
        while (iter.hasNext()) {
            expRules.addActual(iter.next());
        }
        expRules.verify();

        assertEquals("Should be expected value", 1, 1);
    }
}
