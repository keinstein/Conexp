/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.associations.AssociationCoverCalculator;
import conexp.core.associations.AssociationRule;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.collection.CollectionFactory;

import java.util.List;
import java.util.Set;

public class AssociationCoverCalculatorTest extends TestCase {
    private static final Class THIS = AssociationCoverCalculatorTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public static void testFindMaximalFrequentItemsetsForNormalLattice() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{1}});
        List expList = CollectionFactory.createDefaultList();
        expList.add(lat.getZero());
        List actual = (new AssociationCoverCalculator()).calculatePredTableAndInitQueueWithMaximalFrequentItemsets(lat);
        assertEquals(expList, actual);
    }

    public static void testFindMaximalFrequentItemsetsForIcebergLattice() {
        Lattice lat = SetBuilder.makeIcebergLattice(new int[][]{
                {1, 0},
                {0, 1}
        }, 1);
        assertEquals(3, lat.conceptsCount());

        List expList = fillListWithElementWithSpecifiedIntents(lat, new int[][]{
                {1, 0},
                {0, 1}
        });
        List actual = (new AssociationCoverCalculator()).calculatePredTableAndInitQueueWithMaximalFrequentItemsets(lat);
        assertListContentEqual(expList, actual);
    }

    private static List fillListWithElementWithSpecifiedIntents(Lattice lat, int[][] intentsOfExpectedElements) {
        List expList = CollectionFactory.createDefaultList();
        for (int i = 0; i < intentsOfExpectedElements.length; i++) {
            expList.add(SetBuilder.findLatticeElementWithIntent(lat, intentsOfExpectedElements[i]));
        }
        return expList;
    }

    private static void assertListContentEqual(List expected, List actual) {
        Set first = CollectionFactory.createDefaultSet(expected);
        Set second = CollectionFactory.createDefaultSet(actual);
        assertEquals(first, second);
    }

    public static void testFindParentWithMaximalSupportAndMinimalLengthCreatingAssociation() {
        Lattice lat = SetBuilder.makeIcebergLattice(new int[][]{
                {1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1},
                {1, 1, 1, 0, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 1, 0, 1}
        }, 2);
        assertEquals(6, lat.conceptsCount());

        LatticeElement expFoundedParent = SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 0, 1, 0, 0});
        LatticeElement startPoint = SetBuilder.findLatticeElementWithIntent(lat, new int[]{1, 1, 1, 0, 1});
        assertNotNull(startPoint);
        LatticeElement actual = AssociationCoverCalculator.findParentWithMaximalSupportAndMinimalLengthCreatingAssociation(startPoint,
                0.5);
        assertEquals(expFoundedParent, actual);
    }

    public static void testFindParentWithMaximalSupportAndMinimalLengthCreatingAssociationWhenConfidenceIsNotSufficient() {
        //in these lattice, element {1, 0, 0} will be having bigger support, than other elements of equal size
        // size of context 4
        // size of support of current {1, 0, 0} - 2
        Lattice lat = SetBuilder.makeIcebergLattice(new int[][]{
                {0},
                {1},
                {1}
        }, 0);
        assertEquals(2, lat.conceptsCount());

        LatticeElement expFoundedParent = lat.getZero();
        LatticeElement startPoint = SetBuilder.findLatticeElementWithIntent(lat, new int[]{1});
        assertNotNull(startPoint);
        LatticeElement actual = AssociationCoverCalculator.findParentWithMaximalSupportAndMinimalLengthCreatingAssociation(startPoint,
                0.8);
        assertEquals(expFoundedParent, actual);
    }

    public static void testThereAreNoRuleThatCovers() {
        final int[] BE = {0, 1, 0, 0, 1};
        final int[] ABCE = {1, 1, 1, 0, 1};
        final int[] C = new int[]{0, 0, 1, 0, 0};

        final int[][] relation = new int[][]{
                {1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1},
                ABCE,
                BE,
                ABCE
        };
        Context cxt = SetBuilder.makeContext(relation);
        Lattice lat = SetBuilder.makeIcebergLattice(cxt, 2);
        assertEquals(6, lat.conceptsCount());
        DependencySet cover = new DependencySet(cxt);

        LatticeElement premise = SetBuilder.findLatticeElementWithIntent(lat, C);
        LatticeElement conclusion = SetBuilder.findLatticeElementWithIntent(lat, ABCE);
        assertTrue(AssociationCoverCalculator.thereAreNoRuleThatCoversCurrent(cover, premise, conclusion));

        cover.addDependency(AssociationRule.makeFromItemsets(premise, conclusion));

        assertEquals(false, AssociationCoverCalculator.thereAreNoRuleThatCoversCurrent(cover, premise, conclusion));
        assertEquals(false, AssociationCoverCalculator.thereAreNoRuleThatCoversCurrent(cover,
                SetBuilder.findLatticeElementWithIntent(lat, BE),
                conclusion));

        assertEquals(true, AssociationCoverCalculator.thereAreNoRuleThatCoversCurrent(cover,
                SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 0, 0, 0, 0}),
                SetBuilder.findLatticeElementWithIntent(lat, C)));
    }

    public static void testFindCover() {
        final int[] ABCE = {1, 1, 1, 0, 1};
        final int[][] relation = new int[][]{
                {1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1},
                ABCE,
                {0, 1, 0, 0, 1},
                ABCE
        };

        Context cxt = SetBuilder.makeContext(relation);
        Lattice lat = SetBuilder.makeIcebergLattice(cxt, 2);
        DependencySet cover = new DependencySet(cxt);
        AssociationCoverCalculator coverCalculator = new AssociationCoverCalculator();
        coverCalculator.findAssociationsRuleCover(cover, lat, 0.4);
        assertEquals(1, cover.getSize());
        assertEquals(AssociationRule.makeFromItemsets(lat.getOne(),
                SetBuilder.findLatticeElementWithIntent(lat, ABCE)),
                cover.getDependency(0));

    }

}
