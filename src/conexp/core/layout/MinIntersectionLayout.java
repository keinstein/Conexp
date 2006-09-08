/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.ConceptFactory;
import conexp.core.ConceptIterator;
import conexp.core.Edge;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;
import util.Assert;
import util.collection.CollectionFactory;
import util.comparators.ComparatorUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MinIntersectionLayout extends NonIncrementalLayouter {
    //todo:possibly - modify min intersection in order to perfrom swapping, taking into account consideration about strait lines

    //------------------------------------------
    //for debug only
    int maxElm;
    boolean showVirt = false;
    //------------------------------------------
    private Comparator medianComparator = new MedComparator();

    //--------------------------------------------
    static class ElementInfo extends GenericLayouter.LayoutConceptInfo {
        double xPosDirect;
        double xPosReverse;
        double median;
        double savedPosInRank;
        double posInRank;
        int rank;


        public void savePosition() {
            savedPosInRank = posInRank;
        }

        public void restorePosition() {
            posInRank = savedPosInRank;
        }
    }
//------------------------------------------

    class MedComparator implements Comparator {
        public int compare(Object obj, Object obj1) {
            double i1 = getElementInfo((LatticeElement) obj).median;
            double i2 = getElementInfo((LatticeElement) obj1).median;
            if (-1. == i1 || -1. == i2) {
                return 0;
            }
            return ComparatorUtil.compareDoubles(i1, i2);
        }
    }

    //------------------------------------------
    class PositionInRankComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            double i1 = getElementInfo((LatticeElement) o1).posInRank;
            double i2 = getElementInfo((LatticeElement) o2).posInRank;
            return ComparatorUtil.compareDoubles(i1, i2);
        }
    }

    ILayerAssignmentFunction layerAssignmentFunction;

    public ILayerAssignmentFunction getLayerAssignmentFunction() {
        return layerAssignmentFunction;
    }

    public void setLayerAssignmentFunction(ILayerAssignmentFunction layerAssignmentFunction) {
        this.layerAssignmentFunction = layerAssignmentFunction;
    }

    private List virtMap = CollectionFactory.createFastIndexAccessList();

    public MinIntersectionLayout() {
        setLayerAssignmentFunction(HeightInLatticeLayerAssignmentFunction.getInstance());
    }

    //------------------------------------------
    void assignPositionsInRanks() {
        Comparator posComparator = new PositionInRankComparator();
        for (int i = ranks.length; --i > 0;) {
            Arrays.sort(ranks[i], posComparator);
            for (int j = ranks[i].length; --j >= 0;) {
                getElementInfo(ranks[i][j]).posInRank = j;
            }
        }
    }
//------------------------------------------

    static class BreakEdgeInfo {
        Edge replaced;
        Edge start;
        Edge end;

        BreakEdgeInfo(Edge replaced, Edge start, Edge end) {
            super();
            this.replaced = replaced;
            this.start = start;
            this.end = end;
        }

    }

    LinkedList replacedEdges = new LinkedList();

    //------------------------------------------
    void breakEdgeAndUpdateRankSizes(Edge edge, int[] ranksSize) {
        LatticeElement start = edge.getStart();
        LatticeElement prev = start;
        Edge startEdge = null;
        for (int i = edgeSlack(edge); --i >= 0;) {
            LatticeElement virt = ConceptFactory.makeVirtual();
            if (showVirt) {
                /*DBG*/lattice.addElement(virt);
                /*DBG*/virt.setHeight(prev.getHeight() + 1);
            }
            addVirtualConceptToMap(virt);
            getElementInfo(virt).rank = getElementInfo(prev).rank + 1;

            //updating rank size for virtual nodes
            ranksSize[getElementInfo(virt).rank]++;

            Edge newEdge = new Edge(prev, virt);
            if (null == startEdge) {
                startEdge = newEdge;
            } else {
                prev.successors.add(newEdge);
            }
            virt.predessors.add(newEdge);
            prev = virt;
        }

        LatticeElement end = edge.getEnd();
        Edge endEdge = new Edge(prev, end);
        prev.successors.add(endEdge);
        start.replaceSucc(edge, startEdge);
        end.replacePred(edge, endEdge);
        replacedEdges.add(new BreakEdgeInfo(edge, startEdge, endEdge));
    }

    void restoreBreakedEdges() {
        while (replacedEdges.size() > 0) {
            BreakEdgeInfo curr = (BreakEdgeInfo) replacedEdges.removeFirst();
            Edge work = curr.replaced;
            work.getStart().replaceSucc(curr.start, work);
            work.getEnd().replacePred(curr.end, work);
        }
    }

    //---------------------------------------------------------------
    void calcCoordinates() {
        if (!lattice.isEmpty()) {
            initSearch();
            performSearch();

            if (!showVirt) {
                restoreBreakedEdges();
                packPosInRanks();
            }
        }
        assignCoordsToLattice();
    }

    boolean[] transposeRank;
    LatticeElement[][] ranks; //arrays of elements of lattice

    private void initSearch() {
        assignRanksToLatticeElements();

        int height = lattice.getHeight();

        final int[] ranksSize = new int[height + 1];
        calculateRanksSizes(ranksSize);

        ranks = new LatticeElement[height + 1][];
        for (int i = ranks.length; --i >= 0;) {
            ranks[i] = new LatticeElement[ranksSize[i]];
        }

        int[] currRankPos = new int[height + 1];
        Arrays.fill(currRankPos, 0);
        calculateInitialNodeOrderByDepthSearch(lattice.getZero(), currRankPos);

        transposeRank = new boolean[height + 1];
    }

    private void calculateRanksSizes(final int[] ranksSize) {
        Arrays.fill(ranksSize, 0);
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement curr) {
                ranksSize[getElementInfo(curr).rank]++;
                getElementInfo(curr).posInRank = -1;
                breakLongOutgoingEdges(curr, ranksSize); // rank size is updated here also
            }
        });
    }

    private void assignRanksToLatticeElements() {
        getLayerAssignmentFunction().calculateLayersForLattice(lattice,
                new ILayerAssignmentFunction.ILayerAssignmentFunctionCallback() {
                    public void layerForLatticeElement(LatticeElement latticeElement, int layer) {
                        getElementInfo(latticeElement).rank = layer;
                    }
                });
    }

    //------------------------------------------
    int calcCrossings() {
        int ret = 0;
        for (int i = ranks.length; --i >= 0;) {
            int rankCrossing = 0;
            for (int j = ranks[i].length; --j >= 0;) {
                LatticeElement outer = ranks[i][j];
                for (int k = j; --k >= 0;) {
                    rankCrossing += crossingSucc(ranks[i][k], outer);
                }
            }
            ret += rankCrossing;
        }
        return ret;
    }

    public void calcInitialPlacement() {
        //for a while non iterative
        calcCoordinates();
    }

    private void performSearch() {
        int bestCrossing = calcCrossings();

        int iterNo = 0;
        boolean lastIterBest = false;

        int iterationsWithoutImprovement = 0;

        while (iterationsWithoutImprovement < 2) {

            lastIterBest = false;

            wmedian(iterNo);
            transpose(iterNo);

            int delta = bestCrossing - calcCrossings();
            if (delta > 0) {
                bestCrossing -= delta;

                saveOrder();

                lastIterBest = true;
                iterationsWithoutImprovement = 0;
            } else {
                iterationsWithoutImprovement++;
            }

            iterNo++;
        }
        if (!lastIterBest) {
            restoreOrder();
        }
    }


    private void breakLongOutgoingEdges(LatticeElement curr, int[] ranksSize) {
        Iterator succ = curr.successorsEdges();

        while (succ.hasNext()) {
            Edge currEdge = (Edge) succ.next();
            if (edgeSlack(currEdge) > 0) {
                breakEdgeAndUpdateRankSizes(currEdge, ranksSize);
            }
        }
    }

    //------------------------------------------
    int crossing(LatticeElement elm1, LatticeElement elm2) {
        return crossingSucc(elm1, elm2) + crossingPred(elm1, elm2);
    }
//------------------------------------------

    /**
     * this methods calculates crossing between predessors edges
     * of two vertices with one rank
     * list of predessors should be ordered due
     * posInRank of edges end vertices
     * first pos should be less then second pos
     */
    int crossingPred(LatticeElement first, LatticeElement second) {
        Assert.isTrue(isPredessorsOrdered(first), "Predessors should be ordered ");
        Assert.isTrue(isPredessorsOrdered(second), "Predessors should be ordered ");
        return calculateEdgeIntersectionsBetweenTwoElements(first.getPredecessors().iterator(), second.getPredecessors().iterator());
    }

//------------------------------------------

    /**
     * this methods calculates crossing between successors edges
     * of two vertices with one rank
     * list of successors should be ordered due
     * posInRank of edges end vertices
     * first pos should be less then second pos
     */
    int crossingSucc(LatticeElement first, LatticeElement second) {
        Assert.isTrue(isSuccessorsOrdered(first), " Successors should be ordered ");
        Assert.isTrue(isSuccessorsOrdered(second), " Successors should be ordered ");
        return calculateEdgeIntersectionsBetweenTwoElements(first.getSuccessors().iterator(), second.getSuccessors().iterator());
    }

    private int calculateEdgeIntersectionsBetweenTwoElements(ConceptIterator firstEdges, ConceptIterator secondEdges) {
        if (!(firstEdges.hasNext() && secondEdges.hasNext())) {
            return 0;
        }
        int ret = 0;
        outer:
        {
            LatticeElement firstElement = firstEdges.nextConcept();
            LatticeElement secondElement = secondEdges.nextConcept();
            boolean secEdgeIsLast = false;
            for (; ;) {
                //processing first edges, that doesn't intersects
                int prevEdgeCross = ret;
                while (getElementInfo(firstElement).posInRank <= getElementInfo(secondElement).posInRank || secEdgeIsLast)
                {
                    if (!firstEdges.hasNext()) {
                        break outer;
                    }
                    firstElement = firstEdges.nextConcept();
                    ret += prevEdgeCross;         //next edge has all crossings, that a previous one
                }
                Assert.isTrue(getElementInfo(firstElement).posInRank > getElementInfo(secondElement).posInRank, "Position should be greater");

                int edgeOwnCrossing = 0;
                noMoreSecEdges:
                {
                    while (getElementInfo(firstElement).posInRank > getElementInfo(secondElement).posInRank) {
                        ++edgeOwnCrossing;
                        if (!secondEdges.hasNext()) {
                            secEdgeIsLast = true;
                            break noMoreSecEdges;
                        }
                        secondElement = secondEdges.nextConcept();
                    }
                }//noMoreSecEdges
                ret += edgeOwnCrossing;
                if (!firstEdges.hasNext()) {
                    break outer;
                }
            }//for(;;)
        }//outer
        return ret;
    }

    //------------------------------------------
    void calculateInitialNodeOrderByDepthSearch(LatticeElement elm, int[] currRankPos) {
        getElementInfo(elm).posInRank = currRankPos[getElementInfo(elm).rank]++;
        getElementInfo(elm).savePosition();
        ranks[getElementInfo(elm).rank][(int) getElementInfo(elm).posInRank] = elm;

        ConceptIterator succ = elm.getSuccessors().iterator();

        boolean sort = false;

        double prev = -1;
        while (succ.hasNext()) {
            LatticeElement curr = succ.nextConcept();
            //*DBG*/System.out.println("curr.posInRank=["+curr.index+";"+curr.posInRank+"]");
            if (-1 == getElementInfo(curr).posInRank) {
                calculateInitialNodeOrderByDepthSearch(curr, currRankPos);
            } else if (getElementInfo(curr).posInRank < prev) {
                sort = true;
            }
            prev = getElementInfo(curr).posInRank;
        }
        if (sort) {
            elm.getSuccessors().sort(new PositionInRankComparator());
        }
    }

    //------------------------------------------
    void exchange(LatticeElement elm1, LatticeElement elm2) {
        improveOrderingSucc(elm1, elm2);
        improveOrderingPred(elm1, elm2);
        getElementInfo(elm1).posInRank++;
        getElementInfo(elm2).posInRank--;
        ranks[getElementInfo(elm1).rank][(int) getElementInfo(elm1).posInRank] = elm1;
        ranks[getElementInfo(elm2).rank][(int) getElementInfo(elm2).posInRank] = elm2;

        /*DBG*/Assert.isTrue(isSuccessorsOrdered(elm1));
        /*DBG*/Assert.isTrue(isPredessorsOrdered(elm1));
        /*DBG*/Assert.isTrue(isSuccessorsOrdered(elm2));
        /*DBG*/Assert.isTrue(isPredessorsOrdered(elm2));
    }

    private ElementInfo getElementInfo(int index) {
        if (index < maxElm) {
            return (ElementInfo) elementMap[index];
        } else {
            return (ElementInfo) virtMap.get(index - maxElm);
        }
    }

    //------------------------------------------
    void improveOrderingPred(LatticeElement elm1, LatticeElement elm2) {
        Assert.isTrue(getElementInfo(elm1).posInRank < getElementInfo(elm2).posInRank);
        Iterator pred1 = elm1.predessorsEdges();
        Iterator pred2 = elm2.predessorsEdges();
        Edge e1 = (Edge) pred1.next();
        Edge e2 = (Edge) pred2.next();
        outer:
        {
            for (; ;) {
                while (getElementInfo(e1.getStart()).posInRank < getElementInfo(e2.getStart()).posInRank) {
                    if (!pred1.hasNext()) {
                        break outer;
                    }
                    e1 = (Edge) pred1.next();
                }
                while (getElementInfo(e1.getStart()).posInRank > getElementInfo(e2.getStart()).posInRank) {
                    if (!pred2.hasNext()) {
                        break outer;
                    }
                    e2 = (Edge) pred2.next();
                }
                if (getElementInfo(e1.getStart()).posInRank == getElementInfo(e2.getStart()).posInRank) {
                    exchangeEdges(e1.getStart().successors, e1);

                    if (!pred1.hasNext()) {
                        break outer;
                    }
                    e1 = (Edge) pred1.next();
                    if (!pred2.hasNext()) {
                        break outer;
                    }
                    e2 = (Edge) pred2.next();
                }
            }//for(;;)
        }//outer
    }

    //------------------------------------------
    void improveOrderingSucc(LatticeElement elm1, LatticeElement elm2) {
        Assert.isTrue(getElementInfo(elm1).posInRank < getElementInfo(elm2).posInRank);
        //*DBG*/ elm1.printSuccPos();
        //*DBG*/ elm2.printSuccPos();
        Iterator succ1 = elm1.successorsEdges();
        Iterator succ2 = elm2.successorsEdges();
        Edge e1 = (Edge) succ1.next();
        Edge e2 = (Edge) succ2.next();
        outer:
        {
            for (; ;) {
                while (getElementInfo(e1.getEnd()).posInRank < getElementInfo(e2.getEnd()).posInRank) {
                    if (!succ1.hasNext()) {
                        break outer;
                    }
                    e1 = (Edge) succ1.next();
                }
                while (getElementInfo(e1.getEnd()).posInRank > getElementInfo(e2.getEnd()).posInRank) {
                    if (!succ2.hasNext()) {
                        break outer;
                    }
                    e2 = (Edge) succ2.next();
                }
                if (getElementInfo(e1.getEnd()).posInRank == getElementInfo(e2.getEnd()).posInRank) {
                    exchangeEdges(e1.getEnd().predessors, e1);
                    if (!succ1.hasNext()) {
                        break outer;
                    }
                    e1 = (Edge) succ1.next();
                    if (!succ2.hasNext()) {
                        break outer;
                    }
                    e2 = (Edge) succ2.next();
                }
            }//for(;;)
        }//outer
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 10:23:38)
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new ElementInfo();
    }
//------------------------------------------

    private double medianValue(LatticeElementCollection connected) {
        int cnt = connected.getSize();
        if (0 == cnt) {
            return -1;
        }
        int m = cnt / 2;
        if (1 == cnt % 2) {
            return getElementInfo(connected.get(m)).posInRank;

        }
        if (2 == cnt) {
            return (getElementInfo(connected.get(0)).posInRank +
                    getElementInfo(connected.get(1)).posInRank) / 2.;
        }

        double leftMid = getElementInfo(connected.get(m - 1)).posInRank;
        double left = leftMid - getElementInfo(connected.get(0)).posInRank;

        double rightMid = getElementInfo(connected.get(m)).posInRank;
        double right = getElementInfo(connected.get(cnt - 1)).posInRank - rightMid;

        return (leftMid * right + rightMid * left) / (left + right);
    }
//------------------------------------------

    //------------------------------------------
    void packPosInRanks() {
        //*DBG*/debugPrintElements();
        for (int i = ranks.length; --i >= 0;) {
            double pos = 0.;
            for (int j = 0; j < ranks[i].length; j++) {
                if (!ranks[i][j].isVirtual()) {
                    getElementInfo(ranks[i][j]).posInRank = pos;
                    pos += 1.;
                } else {
                    pos += 0.3;
                }
            }
        }
        //*DBG*/debugPrintElements();
    }

    public void performLayout() {
        calcCoordinates();
    }

    void reorderEdges() {
        Comparator rankComparator = new PositionInRankComparator();
        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                if (!isPredessorsOrdered(ranks[i][j])) {
                    ranks[i][j].getPredecessors().sort(rankComparator);
                }
                if (!isSuccessorsOrdered(ranks[i][j])) {
                    ranks[i][j].getSuccessors().sort(rankComparator);
                }
            }
        }
    }

    //------------------------------------------
    void restoreOrder() {
        for (int i = ranks.length - 1; --i > 0;) {
            //restoring order for ranks from 1 to maxRank-1
            for (int j = ranks[i].length; --j >= 0;) {
                getElementInfo(ranks[i][j]).restorePosition();
            }
        }
        assignPositionsInRanks();
    }

    //------------------------------------------
    void saveOrder() {
        for (int i = ranks.length - 1; --i > 0;) {
            //saving order for ranks from 1 to maxRank-1
            for (int j = ranks[i].length; --j >= 0;) {
                getElementInfo(ranks[i][j]).savePosition();
            }
        }
    }

    private boolean straightenLayoutForRank(int rank) {
        LatticeElement[] currRank = ranks[rank];
        int nodeCnt = 0;
        for (int i = currRank.length; --i >= 0;) {
            if (!currRank[i].isVirtual()) {
                nodeCnt++;
            }
        }
        if (nodeCnt <= 0) {
            return false;
        }
        LatticeElement[] currNodes = new LatticeElement[nodeCnt];
        for (int i = currRank.length; --i >= 0;) {
            if (!currRank[i].isVirtual()) {
                currNodes[--nodeCnt] = currRank[i];
            }
        }
        Arrays.sort(currNodes, new XPositionComparator());
        boolean straighten = false;
        int xmarginSize = drawParams.getGridSizeX() / 10;
        for (int i = currNodes.length; --i > 0;) {
            ElementInfo curr = getElementInfo(currNodes[i]);
            ElementInfo prev = getElementInfo(currNodes[i - 1]);
            double prevright = prev.x + drawParams.getMaxNodeRadius() / 2 + xmarginSize;
            double thisleft = curr.x - drawParams.getMaxNodeRadius() / 2 - xmarginSize;
            double overlap = prevright - thisleft;
            if (overlap > 0) {
                prev.x -= overlap / 2;
                curr.x += overlap / 2;
                straighten = true;
            }
        }
        return straighten;
    }

    //------------------------------------------
    void transpose(int iter) {
        Arrays.fill(transposeRank, true);
        boolean improved;
        int iterationsWithoutImprovement = 0;
        int crossingBefore = calcCrossings();
        do {
            improved = false;
            int maxBound = ranks.length;
            if (1 == iter % 2) {
                for (int i = maxBound - 1; --i > 1;) {
                    if (transposeOneRank(i)) {
                        improved = true;
                    }
                }
            } else {
                for (int i = 0; i < maxBound; i++) {
                    if (transposeOneRank(i)) {
                        improved = true;
                    }
                }
            }

            int delta = crossingBefore - calcCrossings();
            if (delta <= 0) {
                iterationsWithoutImprovement++;
            } else {
                iterationsWithoutImprovement = 0;
                crossingBefore -= delta;
            }

        } while (improved && iterationsWithoutImprovement < 3);
    }

    //------------------------------------------

    private boolean transposeOneRank(int i) {
        boolean improved = false;
        if (transposeRank[i]) {
            int upBound = Math.min(i + 2, ranks.length);
            int lowBound = Math.max(i - 1, 1);
            transposeRank[i] = false;
            for (int j = ranks[i].length - 1; --j >= 0;) {

                int crs1 = crossing(ranks[i][j], ranks[i][j + 1]);
                int crs2 = crossing(ranks[i][j + 1], ranks[i][j]);
                if (crs1 >= crs2) {
                    exchange(ranks[i][j], ranks[i][j + 1]);
                    Arrays.fill(transposeRank, lowBound, upBound, true);
                    if (crs1 > crs2) {
                        improved = true;
                        //*DBG*/System.out.println("Exchange ["+ranks[i][j].index+":"+ranks[i][j].posInRank+"] [" +ranks[i][j+1].index+":"+ranks[i][j+1].posInRank+"]" +(crs1-crs2));
                    }
                }
            }
        }
        return improved;
    }

    //------------------------------------------

    void wmedian(int iter) {
        Comparator predRankComparator = new PositionInRankComparator();
        if (0 == iter % 2) {
// warning : number of ranks decreased due lattice properties
            int bound = ranks.length - 1;
            // this cycle will be working, only if number of ranks is bigger/equal to  4//otherwise it will not be working
            for (int i = 2; i < bound; i++) {
                LatticeElement[] currentRank = ranks[i];
                for (int j = currentRank.length; --j >= 0;) {
                    LatticeElement elm = currentRank[j];
                    getElementInfo(elm).median = medianValue(elm.getSuccessors());
                }
                reorderCurrentRankByHeuristicValue(currentRank);
            }
// restoring order of in edges
        } else {
            // this cycle will be working, if number of ranks is greater
            for (int i = ranks.length - 1; --i > 1;) {
                LatticeElement[] currentRank = ranks[i];
                for (int j = currentRank.length; --j >= 0;) {
                    LatticeElement elm = currentRank[j];
                    if (!isPredessorsOrdered(currentRank[j])) {
                        currentRank[j].getPredecessors().sort(predRankComparator);
                    }
                    getElementInfo(elm).median = medianValue(elm.getPredecessors());
                }
                reorderCurrentRankByHeuristicValue(currentRank);
            }
        }
        reorderEdges();
    }

    private void reorderCurrentRankByHeuristicValue(LatticeElement[] currentRank) {
        Arrays.sort(currentRank, medianComparator);
        for (int j = currentRank.length; --j >= 0;) {
            getElementInfo(currentRank[j]).posInRank = j;
        }
    }


    private void addVirtualConceptToMap(LatticeElement el) {
        el.setIndex(maxElm + virtMap.size());
        ElementInfo currInfo = (ElementInfo) makeConceptInfo();
        currInfo.posInRank = -1;
        virtMap.add(currInfo);
    }

    protected void afterInitLayout() {
        maxElm = lattice.conceptsCount();
        virtMap.clear();
    }

    interface ConnectedCollectionsSupplier {
        LatticeElementCollection getConnected(LatticeElement current);

        LatticeElementCollection getOtherConnected(LatticeElement current);
    }

    private void doAssignsCoordsForRank(int rank, ConnectedCollectionsSupplier supplier) {
        final int deltaX = drawParams.getGridSizeX();
        for (int j = ranks[rank].length; --j >= 0;) {
            LatticeElement curr = ranks[rank][j];
            if (curr.isVirtual()) {
                continue;
            }
            LatticeElementCollection connected = supplier.getConnected(curr);
            LatticeElement parent = connected.get(0);
            int numParents = connected.getSize();

            ElementInfo currInfo = getElementInfo(curr);
            ElementInfo parentInfo = getElementInfo(parent);
            if (1 == numParents) {
                LatticeElementCollection otherConnected = supplier.getOtherConnected(parent);
                int numChildsOfParent = otherConnected.getSize();
                if (1 == numChildsOfParent) {
                    currInfo.x = parentInfo.x;
                } else {
                    int pos = -1;
                    for (int k = numChildsOfParent; --k >= 0;) {
                        if (otherConnected.get(k) == curr) {
                            pos = k;
                            break;
                        }
                    }
                    Assert.isTrue(pos != -1);
                    currInfo.x = parentInfo.x - (numChildsOfParent - 1) * deltaX / 2 + pos * deltaX;
                }
            } else {
                currInfo.x = (getElementInfo(connected.get(0)).x + getElementInfo(connected.get(numParents - 1)).x) / 2;
            }
            currInfo.y = drawParams.getGridSizeY() * (lattice.getHeight() - curr.getHeight());
        }
    }

    //---------------------------------------------------------------
    int calcLatticeWidth() {
        //        lattice.calcHeight();
        int height_ = lattice.getHeight();

        int[] coordArr = new int[height_ + 1];
        for (int i = coordArr.length; --i >= 0;) {
            coordArr[i] = 0;
        }

        Iterator iter = lattice.elements();
        while (iter.hasNext()) {
            LatticeElement tmp = (LatticeElement) iter.next();
            coordArr[tmp.getHeight()]++;
        }

        int width = 0;
        for (int i = coordArr.length; --i >= 0;) {
            width = Math.max(coordArr[i], width);
        }
        return width;
    }

    private ElementInfo getElementInfo(LatticeElement el) {
        return getElementInfo(el.getIndex());
    }

    /**
     * @deprecated
     */
    //----------------------------------------------
    private boolean isPredessorsOrdered(LatticeElement el) {
        return checkIsElementsOfIteratorOrderedByPosition(el.getPredecessors().iterator());
    }

    /**
     * @deprecated
     */
    //----------------------------------------------
    private boolean isSuccessorsOrdered(LatticeElement el) {
        return checkIsElementsOfIteratorOrderedByPosition(el.getSuccessors().iterator());
    }

    private boolean checkIsElementsOfIteratorOrderedByPosition(ConceptIterator iter) {
        double prevPos = -1;
        while (iter.hasNext()) {
            LatticeElement elm = iter.nextConcept();
            if (getElementInfo(elm).posInRank < prevPos) {
                return false;
            }
            prevPos = getElementInfo(elm).posInRank;
        }
        return true;
    }

/*
    //--------------------------------------------
    private void printPredPos(LatticeElement el) {
        printElementsFromIteratorWithPositions("Predessors: ", el.getPredecessors().iterator());
    }

    private void printSuccPos(LatticeElement el) {
        printElementsFromIteratorWithPositions("Successors: ", el.getSuccessors().iterator());
    }



    private void printElementsFromIteratorWithPositions(String name, ConceptIterator iter) {
        System.out.print(name);
        System.out.print("{ ");
        while (iter.hasNext()) {
            LatticeElement elm = iter.nextConcept();
            System.out.print("[" + elm.getIndex() + ';' + getElementInfo(elm).rank + ';' + getElementInfo(elm).posInRank + ']');
        }
        System.out.println(" } ");
    }

*/
    //--------------------------------------------

    interface ElementInfoProcessor {
        void process(ElementInfo elementInfo);
    }

    protected void assignCoordsToLattice() {
        int latticeWidth = calcLatticeWidth();
        int height = lattice.getHeight();
        calcZeroElementPosition(latticeWidth, height);

        ConnectedCollectionsSupplier supplier = new DirectConnectedCollectionsSupplier();

        for (int rank = 1; rank < ranks.length; rank++) {
            doAssignsCoordsForRank(rank, supplier);
            doStraytenLayoutForRank(rank);
        }

        applyElementInfoProcessor(new ElementInfoProcessor() {
            public void process(ElementInfo elementInfo) {
                elementInfo.xPosDirect = elementInfo.x;
            }
        });
        ConnectedCollectionsSupplier reverseSupplier = new ReverseConnectedCollectionsSupplier();

        for (int rank = ranks.length - 1; --rank >= 0;) {
            doAssignsCoordsForRank(rank, reverseSupplier);
            doStraytenLayoutForRank(rank);
        }
        applyElementInfoProcessor(new ElementInfoProcessor() {
            public void process(ElementInfo elementInfo) {
                elementInfo.xPosReverse = elementInfo.x;
            }
        });
        applyElementInfoProcessor(new ElementInfoProcessor() {
            public void process(ElementInfo elementInfo) {
                elementInfo.x = (elementInfo.xPosReverse + elementInfo.xPosDirect) / 2.;
            }
        });
        for (int rank = ranks.length - 1; --rank >= 0;) {
            doStraytenLayoutForRank(rank);
        }
        fireLayoutChanged();
    }

    private void applyElementInfoProcessor(ElementInfoProcessor processor) {
        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                LatticeElement curr = ranks[i][j];
                if (curr.isVirtual()) {
                    continue;
                }
                processor.process(getElementInfo(curr));
            }
        }
    }

    private void calcZeroElementPosition(int latticeWidth, int height) {
        LatticeElement zero = lattice.getZero();
        ElementInfo zeroInfo = getElementInfo(zero);
        zeroInfo.x = drawParams.getGridSizeX() * latticeWidth / 2;
        zeroInfo.y = (height - zero.getHeight()) * drawParams.getGridSizeY();
    }

    private void doStraytenLayoutForRank(int i) {
        int straitenIterCount = 10;
        while (straightenLayoutForRank(i) && straitenIterCount > 0) {
            straitenIterCount--;
        }
    }

    protected static int edgeSlack(Edge currEdge) {
        return currEdge.getLength() - 1;
    }

    //------------------------------------------
    static void exchangeEdges(List edges, Edge toSwap) {
        int index = edges.indexOf(toSwap);
        Assert.isTrue(-1 != index);
        edges.set(index, edges.get(index + 1));
        edges.set(index + 1, toSwap);
    }

    private static class DirectConnectedCollectionsSupplier implements ConnectedCollectionsSupplier {
        public LatticeElementCollection getConnected(LatticeElement element) {
            return element.getPredecessors();
        }

        public LatticeElementCollection getOtherConnected(LatticeElement element) {
            return element.getSuccessors();
        }
    }

    private static class ReverseConnectedCollectionsSupplier implements ConnectedCollectionsSupplier {
        public LatticeElementCollection getConnected(LatticeElement element) {
            return element.getSuccessors();
        }

        public LatticeElementCollection getOtherConnected(LatticeElement element) {
            return element.getPredecessors();
        }
    }

    private class XPositionComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            ElementInfo el1 = getElementInfo((LatticeElement) obj1);
            ElementInfo el2 = getElementInfo((LatticeElement) obj2);
            return ComparatorUtil.compareDoubles(el1.x, el2.x);
        }
    }
}
