/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.*;
import util.Assert;
import util.collection.disjointset.DisjointSetsSystem;
import util.comparators.ComparatorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class BaseAssociationMiner implements AssociationMiner {
    protected Context cxt;
    protected Lattice frequentItemsetLattice;
    protected ImplicationSet validImplications;

    public ImplicationSet getImplicationBase() {
        return validImplications;
    }


    /**
     * Insert the method's description here.
     * Creation date: (20.06.01 2:03:38)
     */
    protected static void findApproximateBaseByCruscal(DependencySet ret, ArrayList frequentEdges, int frequentSetCount) {
        DisjointSetsSystem cycleDetector = new DisjointSetsSystem(frequentSetCount);
        Iterator frequentEdgeIter = frequentEdges.iterator();
        while (frequentEdgeIter.hasNext()) {
            Edge e = (Edge) frequentEdgeIter.next();
            int startConceptId = e.getStart().getIndex(), endConceptId = e.getEnd().getIndex();
            if (cycleDetector.findSet(endConceptId) != cycleDetector.findSet(startConceptId)) {
                ret.addDependency(AssociationRule.makeFromItemsets(e.getEnd(), e.getStart()));
                cycleDetector.union(endConceptId, startConceptId);
            }
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 1:20:34)
     * @param minConfidence double
     * @param minSupport int
     */
    public abstract void findAssociations(DependencySet depSet, int minSupport, double minConfidence);

    /**
     * Creation date: (19.06.01 23:51:04)
     * @param minConfidence double
     * @param minSupport int
     * @test_public
     */
    public static ArrayList findFrequentEdgesSortedByConfidence(Lattice lat, double minConfidence, int minSupport) {
        ArrayList frequentEdges = new ArrayList();
        EdgeIterator selector = new conexp.core.enumerators.EdgeMinSupportSelector(lat, minSupport);
        while (selector.hasNextEdge()) {
            Edge e = selector.nextEdge();
            if (e.getConfidence() >= minConfidence) {
                frequentEdges.add(e);
            }
        }
        Collections.sort(frequentEdges,
                new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ComparatorUtil.compareDoubles(((Edge) o2).getConfidence(), ((Edge) o1).getConfidence());
                    }
                });

        return frequentEdges;
    }

    public void findAssociationsRuleCover(DependencySet cover, double minConfidence) {
        Assert.isTrue(frequentItemsetLattice != null, "Frequent itemset lattice should be calculated before search for cover");
        (new AssociationCoverCalculator()).findAssociationsRuleCover(cover,
                frequentItemsetLattice,
                minConfidence
        );
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.05.01 10:12:34)
     */
    public abstract Lattice findFrequentItemsetsAndImplications(int minSupport);

    /**
     * Insert the method's description here.
     * Creation date: (02.05.01 14:21:12)
     * @param cxt conexp.core.Context
     */
    public void setContext(Context cxt) {
        this.cxt = cxt;
    }

    public ExtendedContextEditingInterface getDataSet() {
        return cxt;
    }


    public int supportForSet(Set attribs) {
        Assert.isTrue(validImplications != null, "Frequent set and implications should be found before use");
        ModifiableSet closedSet = attribs.makeModifiableSetCopy();
        validImplications.setClosure(closedSet);
        return supportForClosedSet(closedSet);
    }

    public int supportForClosedSet(Set attribs) {
        return frequentItemsetLattice.findLatticeElementFromOne(attribs).getObjCnt();
    }

    public int getTotalObjectCount() {
        return cxt.getObjectCount();
    }

    public int numberOfFrequentClosedItemsets() {
        return frequentItemsetLattice.conceptsCount();
    }

    public int numberOfEdges() {
        return frequentItemsetLattice.edgeCount();
    }
}
