/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ModifiableSet;
import conexp.core.associations.AssociationMiner;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public abstract class AssociationMinerTest extends TestCase {
    public void testAssociationMiner() {
        AssociationMiner miner = makeAssociationMiner();
        Context cxt = SetBuilder.makeContext(new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        });
        DependencySet dependencySet = new DependencySet(cxt);
        miner.setContext(cxt);
        miner.findAssociations(dependencySet, 2, 0.5);
        Set actualDependencies = new HashSet();
        for (int i = 0; i < dependencySet.getSize(); i++) {
            actualDependencies.add(dependencySet.getDependency(i));
        }
        Set expectedDependencies = new HashSet();
        expectedDependencies.add(AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 0}, 3, new int[]{1, 0, 0}, 2));
        expectedDependencies.add(AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 0}, 3, new int[]{0, 1, 0}, 2));
        expectedDependencies.add(AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 0}, 3, new int[]{0, 0, 1}, 2));
        assertEquals(expectedDependencies, actualDependencies);
    }

    public void testGetSupportForSet() {
        AssociationMiner miner = makeAssociationMiner();
        Context cxt = SetBuilder.makeContext(new int[][]{
                {0, 0, 0},
                {0, 1, 1}
        });
        DependencySet dependencySet = new DependencySet(cxt);
        miner.setContext(cxt);
        miner.findAssociations(dependencySet, 0, 0);
        final ModifiableSet one = SetBuilder.makeSet(new int[]{0, 0, 0});
        assertEquals(2, miner.supportForSet(one));
        assertEquals(2, miner.supportForClosedSet(one));

        assertEquals(1, miner.supportForSet(SetBuilder.makeSet(new int[]{0, 0, 1})));
    }

    protected abstract AssociationMiner makeAssociationMiner();
}
