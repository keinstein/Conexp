/*
 * User: Serhiy Yevtushenko
 * Date: Aug 19, 2002
 * Time: 4:34:23 PM
 */
package conexp.core;

import util.Assert;
import util.collection.disjointset.DisjointSetsSystem;

public class LatticeDiagramChecker {

    int nodeCount;
    ModifiableBinaryRelation lessThanCoverRelation;
    protected ModifiableBinaryRelation lessThanRelation;
    protected Context context;
    protected Lattice lattice;
    boolean notDiagramMode = false;

    public boolean isNotDiagramMode() {
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
        //DBG*/ System.out.println("Real node count: "+getLattice().conceptsCount());
        return getLattice().conceptsCount() == expectedNodeCount;
    }

    public boolean hasMinimalElement() {
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
            ModifiableBinaryRelation lessThanEqualRelation = getLessThanRelation().makeModifiableCopy();
            for (int i = 0; i < nodeCount; i++) {
                lessThanEqualRelation.setRelationAt(i, i, true);
            }
            //*DBG*/ BinaryRelationUtils.logRelation(lessThanCoverRelation, new PrintWriter(System.out, true));
            context = FCAEngineRegistry.makeContext(lessThanEqualRelation);
            lattice = FCAEngineRegistry.buildLattice(context);
        }
        return lattice;
    }


    public ExtendedContextEditingInterface getContext() {
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
        BinaryRelation lessThanRelation = getLessThanRelation();

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (lessThanRelation.getRelationAt(i, j) && lessThanRelation.getRelationAt(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public BinaryRelation getLessThanRelation() {
        if (null == lessThanRelation) {
            lessThanRelation = lessThanCoverRelation.makeModifiableCopy();
            BinaryRelationUtils.transitiveClosure(lessThanRelation);
        }
        return lessThanRelation;
    }

    boolean isDiagramConnected() {
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
