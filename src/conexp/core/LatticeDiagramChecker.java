/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.collection.disjointset.DisjointSetsSystem;

public class LatticeDiagramChecker {

    private int nodeCount;
    private ModifiableBinaryRelation lessThanCoverRelation;
    private ModifiableBinaryRelation lessThanRelation;
    private Context context;
    private Lattice lattice;
    private boolean notDiagramMode = false;

    private boolean isNotDiagramMode() {
        return notDiagramMode;
    }

    public void setNotDiagramMode(boolean notDiagramMode) {
        this.notDiagramMode = notDiagramMode;
    }


    public void setNodeCount(int size) {
        this.nodeCount = size;
        lessThanCoverRelation = ContextFactoryRegistry.createRelation(nodeCount, nodeCount);
        clear();
    }

    private void clear() {
        lessThanRelation = null;
        context = null;
        lattice = null;
    }

    public void setLessThan(int nodeLesser, int nodeBigger) {
        lessThanCoverRelation.setRelationAt(nodeLesser, nodeBigger, true);
        clear();
    }

    public boolean isDiagramOfLattice() {
        if (!checkHasseDiagram()) {
            return false;
        }
        if (!(getLattice().conceptsCount() == nodeCount)) {
            return false;
        }
        return true;
    }

    private boolean checkHasseDiagram() {
        if (!isDiagramConnected()) {
            //DBG*/ System.out.println("Not connected");
            return false;
        }
        if (!isNotDiagramMode()) {
            if (!isOrderRelationAcyclic()) {
                //DBG*/ System.out.println("Not acyclic");
                return false;
            }
            if (orderRelationContainsRedundantEdges()) {
                //DBG*/ System.out.println("has redundant edges");
                return false;
            }
        }
        return true;
    }

    public boolean isDiagramOfSemilattice() {
        if (!checkHasseDiagram()) {
            return false;
        }
        int expectedNodeCount = hasMinimalElement() ? nodeCount : nodeCount + 1;
        //DBG*/ System.out.println("Node count: "+nodeCount);
        //DBG*/ System.out.println("Real node count: "+getConcepts().conceptsCount());
        return getLattice().conceptsCount() == expectedNodeCount;
    }

    private boolean hasMinimalElement() {
        BinaryRelation lessThan = getLessThanRelation();
        int minElementCount = 0;
        for (int i = 0; i < nodeCount; i++) {
            boolean minimum = true;
            for (int j = 0; j < nodeCount; j++) {
                if (i == j) {
                    continue;
                }
                if (!lessThan.getRelationAt(i, j)) {
                    minimum = false;
                    break;
                }
            }
            if (minimum) {
                minElementCount++;
            }
        }
        return minElementCount == 1;
    }

    private Lattice getLattice() {
        if (null == lattice) {
            buildContextOfRelation();
            lattice = FCAEngineRegistry.buildLattice(context);
        }
        return lattice;
    }

    private void buildContextOfRelation() {
        ModifiableBinaryRelation lessThanEqualRelation = getLessThanRelation().makeModifiableCopy();
        for (int i = 0; i < nodeCount; i++) {
            lessThanEqualRelation.setRelationAt(i, i, true);
        }
        //*DBG*/ BinaryRelationUtils.logRelation(lessThanCoverRelation, new PrintWriter(System.out, true));
        context = FCAEngineRegistry.makeContext(lessThanEqualRelation);
    }


    public ExtendedContextEditingInterface getContext() {
        if (null == context) {
            buildContextOfRelation();
        }
        return context;
    }

    private boolean orderRelationContainsRedundantEdges() {
        ModifiableBinaryRelation reachableByPathOfLengthBiggerThanOne = ContextFactoryRegistry.createRelation(nodeCount, nodeCount);
        ModifiableBinaryRelation prevReachabilityRelation = lessThanCoverRelation;

        for (int i = 0; i < nodeCount; i++) {  //can be optimized, if needed
            calcReachability(reachableByPathOfLengthBiggerThanOne, prevReachabilityRelation, lessThanCoverRelation);
            if (hasIntersectionsWithLessThanRelation(reachableByPathOfLengthBiggerThanOne)) {
                return true;
            }
            prevReachabilityRelation = reachableByPathOfLengthBiggerThanOne;
        }

        return false;
    }

    private boolean hasIntersectionsWithLessThanRelation(ModifiableBinaryRelation reachabilityRelation) {
        for (int i = 0; i < nodeCount; i++) {
            if (reachabilityRelation.getSet(i).intersects(lessThanCoverRelation.getSet(i))) {
                return true;
            }
        }
        return false;
    }

    private void calcReachability(ModifiableBinaryRelation twoReachable, final ModifiableBinaryRelation firstReachabilityRelation, final ModifiableBinaryRelation basicReachabilityRelation) {
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (firstReachabilityRelation.getRelationAt(i, j)) {
                    twoReachable.getModifiableSet(i).or(basicReachabilityRelation.getSet(j));
                }
            }
        }
    }

    private boolean isOrderRelationAcyclic() {
        return BinaryRelationUtils.haveNoBidirectionalEdges(getLessThanRelation());
    }

    private BinaryRelation getLessThanRelation() {
        if (null == lessThanRelation) {
            lessThanRelation = lessThanCoverRelation.makeModifiableCopy();
            BinaryRelationUtils.transitiveClosure(lessThanRelation);
        }
        return lessThanRelation;
    }

    private boolean isDiagramConnected() {
        if (nodeCount == 0) {
            return true;
        }
        DisjointSetsSystem disjointSystem = new DisjointSetsSystem(nodeCount);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (lessThanCoverRelation.getRelationAt(i, j)) {
                    disjointSystem.union(i, j);
                }
            }
        }

        Assert.isTrue(nodeCount > 0);
        int startNode = disjointSystem.findSet(0);
        for (int i = 1; i < disjointSystem.size(); i++) {
            if (startNode != disjointSystem.findSet(i)) {
                return false;
            }
        }

        return true;
    }
}
