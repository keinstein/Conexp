package conexp.core.layout;

import conexp.core.*;
import util.Assert;
import util.comparators.ComparatorUtil;

import java.util.*;

public class MinIntersectionLayout extends NonIncrementalLayouter {
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
        int topSortNumber;


        public void savePosition() {
            savedPosInRank = posInRank;
        }

        public void restorePosition() {
            posInRank = savedPosInRank;
        }
    };
    //------------------------------------------
    class SuccRankComparator implements java.util.Comparator {
        public int compare(Object obj, Object obj1) {
            double i1 = getElementInfo(((Edge) obj).getEnd()).posInRank;
            double i2 = getElementInfo(((Edge) obj1).getEnd()).posInRank;
            return ComparatorUtil.compareDoubles(i1, i2);
        }

        private SuccRankComparator() {
            super();
        }
    }

//------------------------------------------
    //------------------------------------------
    class MedComparator implements java.util.Comparator {
        private MedComparator() {
            super();
        }

        public int compare(Object obj, Object obj1) {
            double i1 = getElementInfo((LatticeElement) obj).median;
            double i2 = getElementInfo((LatticeElement) obj1).median;
            if (-1. == i1 || -1. == i2) {
                return 0;
            }
            return ComparatorUtil.compareDoubles(i1, i2);
        }
    }


    class PosComparator implements java.util.Comparator {
        public int compare(Object obj, Object obj1) {
            double i1 = getElementInfo(((LatticeElement) obj)).posInRank;
            double i2 = getElementInfo(((LatticeElement) obj1)).posInRank;
            return ComparatorUtil.compareDoubles(i1, i2);
        }

        private PosComparator() {
            super();
        }
    }

    //------------------------------------------
    class PredRankComparator implements java.util.Comparator {
        private PredRankComparator() {
        }

        public int compare(Object obj, Object obj1) {
            double i1 = getElementInfo(((Edge) obj).getStart()).posInRank;
            double i2 = getElementInfo(((Edge) obj1).getStart()).posInRank;
            return ComparatorUtil.compareDoubles(i1, i2);
        }
    }

    private java.util.ArrayList virtMap = new ArrayList();


    //------------------------------------------
    void assignRanks() {
        Comparator posComparator = new PosComparator();
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

    boolean transposeRank[];
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

        int currRankPos[] = new int[height + 1];
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
        lattice.doTopSort(new Lattice.DefaultTopSortBlock() {
            public void elementAction(LatticeElement curr, LatticeElement lastPred) {
                getElementInfo(curr).rank = curr.getHeight();
            }

            public void assignTopSortNumberToElement(LatticeElement el, int topSortNumber) {
                getElementInfo(el).topSortNumber = topSortNumber;
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

            int delta = (bestCrossing - calcCrossings());
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
        Iterator succ = curr.successors();

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
     this methods calculates crossing between predessors edges
     of two vertices with one rank
     list of predessors should be ordered due
     posInRank of edges end vertices
     first pos should be less then second pos
     */
    int crossingPred(LatticeElement first, LatticeElement second) {
        Assert.isTrue(isPredessorsOrdered(first), "Predessors should be ordered ");
        Assert.isTrue(isPredessorsOrdered(second), "Predessors should be ordered ");
        return calculateEdgeIntersectionsBetweenTwoElements(first.predecessorElements(), second.predecessorElements());
    }

//------------------------------------------
    /**
     this methods calculates crossing between successors edges
     of two vertices with one rank
     list of successors should be ordered due
     posInRank of edges end vertices
     first pos should be less then second pos
     */
    int crossingSucc(LatticeElement first, LatticeElement second) {
        Assert.isTrue(isSuccessorsOrdered(first), " Successors should be ordered ");
        Assert.isTrue(isSuccessorsOrdered(second), " Successors should be ordered ");
        return calculateEdgeIntersectionsBetweenTwoElements(first.successorElements(), second.successorElements());
    }

    private int calculateEdgeIntersectionsBetweenTwoElements(ConceptIterator firstEdges, ConceptIterator secondEdges) {
        if (!(firstEdges.hasNext() && secondEdges.hasNext())) {
            return 0;
        }
        int ret = 0;
        outer:{
            LatticeElement firstElement = firstEdges.nextConcept();
            LatticeElement secondElement = secondEdges.nextConcept();
            boolean secEdgeIsLast = false;
            for (; ;) {
                //processing first edges, that doesn't intersects
                int prevEdgeCross = ret;
                while (getElementInfo(firstElement).posInRank <= getElementInfo(secondElement).posInRank || secEdgeIsLast) {
                    if (!firstEdges.hasNext()) {
                        break outer;
                    }
                    firstElement = firstEdges.nextConcept();
                    ret += prevEdgeCross;         //next edge has all crossings, that a previous one
                }
                util.Assert.isTrue(getElementInfo(firstElement).posInRank > getElementInfo(secondElement).posInRank, "Position should be greater");

                int edgeOwnCrossing = 0;
                noMoreSecEdges:{
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

        ConceptIterator succ = elm.successorElements();

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
            Collections.sort(elm.successors, new SuccRankComparator());
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
        util.Assert.isTrue(getElementInfo(elm1).posInRank < getElementInfo(elm2).posInRank);
        Iterator pred1 = elm1.predessors();
        Iterator pred2 = elm2.predessors();
        Edge e1 = (Edge) pred1.next();
        Edge e2 = (Edge) pred2.next();
        outer:{
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
        util.Assert.isTrue(getElementInfo(elm1).posInRank < getElementInfo(elm2).posInRank);
        //*DBG*/ elm1.printSuccPos();
        //*DBG*/ elm2.printSuccPos();
        Iterator succ1 = elm1.successors();
        Iterator succ2 = elm2.successors();
        Edge e1 = (Edge) succ1.next();
        Edge e2 = (Edge) succ2.next();
        outer:{
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
    /** returns median value for the list of predessors
     @param elm - node of lattice for which to return median value
     */
    double medianValuePred(LatticeElement elm) {
        int cnt = elm.getPredCount();
        int m = cnt / 2;
        if (0 == cnt) {
            return -1;
        }
        if (1 == cnt % 2) {
            return getElementInfo(elm.getPred(m)).posInRank;

        }
        if (2 == cnt) {
            return (getElementInfo(elm.getPred(0)).posInRank +
                    getElementInfo(elm.getPred(1)).posInRank) / 2.;
        }

        double leftMid = getElementInfo(elm.getPred(m - 1)).posInRank;
        double left = leftMid - getElementInfo(elm.getPred(0)).posInRank;

        double rightMid = getElementInfo(elm.getPred(m)).posInRank;
        double right = getElementInfo(elm.getPred(cnt - 1)).posInRank - rightMid;

        return (leftMid * right + rightMid * left) / (left + right);
    }
//------------------------------------------
    /** returns median value for the list of successors
     @param elm - node of lattice for which to return median value
     */
    double medianValueSucc(LatticeElement elm) {
        int cnt = elm.getSuccCount();
        if (0 == cnt) {
            return -1.;
        }
        int m = cnt / 2;
        if (1 == cnt % 2) {
            return getElementInfo(elm.getSucc(m)).posInRank;
        }
        if (2 == cnt) {
            return (getElementInfo(elm.getSucc(0)).posInRank +
                    getElementInfo(elm.getSucc(1)).posInRank) / 2.;
        }
        double leftMid = getElementInfo(elm.getSucc(m - 1)).posInRank;
        double left = leftMid - getElementInfo(elm.getSucc(0)).posInRank;
        double rightMid = getElementInfo(elm.getSucc(m)).posInRank;
        double right = getElementInfo(elm.getSucc(cnt - 1)).posInRank - rightMid;
        return (leftMid * right + rightMid * left) / (left + right);
    }

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
        Comparator predRankComparator = new PredRankComparator();
        Comparator succRankComparator = new SuccRankComparator();
        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                if (!isPredessorsOrdered(ranks[i][j])) {
                    Collections.sort(ranks[i][j].predessors, predRankComparator);
                }
                if (!isSuccessorsOrdered(ranks[i][j])) {
                    Collections.sort(ranks[i][j].successors, succRankComparator);
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
        assignRanks();
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
        Arrays.sort(currNodes, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                ElementInfo el1 = getElementInfo((LatticeElement) obj1);
                ElementInfo el2 = getElementInfo((LatticeElement) obj2);
                if (el1.x < el2.x) {
                    return -1;
                }
                if (el1.x > el2.x) {
                    return 1;
                }
                return 0;
            }
        });
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
            if (1 == (iter % 2)) {
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
        Comparator predRankComparator = new PredRankComparator();
        if (0 == iter % 2) {
// warning : number of ranks decreased due lattice properties
            int bound = ranks.length - 1;
            // this cycle will be working, only if number of ranks is bigger/equal to  4//otherwise it will not be working
            for (int i = 2; i < bound; i++) {
                LatticeElement[] currentRank = ranks[i];
                for (int j = currentRank.length; --j >= 0;) {
                    getElementInfo(currentRank[j]).median = medianValueSucc(currentRank[j]);
                }
                reorderCurrentRankByHeuristicValue(currentRank);
            }
// restoring order of in edges
        } else {
            // this cycle will be working, if number of ranks is greater
            for (int i = ranks.length - 1; --i > 1;) {
                LatticeElement[] currentRank = ranks[i];
                for (int j = currentRank.length; --j >= 0;) {
                    if (!isPredessorsOrdered(currentRank[j])) {
                        Collections.sort(currentRank[j].predessors, predRankComparator);
                    }
                    getElementInfo(currentRank[j]).median = medianValuePred(currentRank[j]);
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


    private void assignCoordsForRank(int rank) {
        final int deltaX = drawParams.getGridSizeX();
        for (int j = ranks[rank].length; --j >= 0;) {
            LatticeElement curr = ranks[rank][j];
            if (curr.isVirtual()) {
                continue;
            }
            LatticeElement parent = curr.getPred(0);
            int numParents = curr.getPredCount();

            ElementInfo currInfo = getElementInfo(curr);
            ElementInfo parentInfo = getElementInfo(parent);
            if (1 == numParents) {
                int numChildsOfParent = parent.getSuccCount();
                if (1 == numChildsOfParent) {
                    currInfo.x = parentInfo.x;
                } else {
                    int pos = -1;
                    for (int k = numChildsOfParent; --k >= 0;) {
                        if (parent.getSucc(k) == curr) {
                            pos = k;
                            break;
                        }
                    }
                    util.Assert.isTrue(pos != -1);
                    currInfo.x = parentInfo.x - ((numChildsOfParent - 1) * deltaX / 2) + pos * deltaX;
                }
            } else {
                currInfo.x = (getElementInfo(curr.getPred(0)).x + getElementInfo(curr.getPred(numParents - 1)).x) / 2;
            }
            /*getElementInfo(curr).y = drawParams.getGridSizeY() * (lattice.getHeight() - curr.getHeight()); */
            currInfo.y = drawParams.getGridSizeY() * (lattice.getHeight() - curr.getHeight());

        }
    }


    private void assignCoordsForRankReverse(int rank) {
        final int deltaX = drawParams.getGridSizeX();
        for (int j = ranks[rank].length; --j >= 0;) {
            LatticeElement curr = ranks[rank][j];
            if (curr.isVirtual()) {
                continue;
            }
            LatticeElement parent = curr.getSucc(0);
            int numParents = curr.getSuccCount();

            ElementInfo currInfo = getElementInfo(curr);
            ElementInfo parentInfo = getElementInfo(parent);

            if (1 == numParents) {
                int numChildsOfParent = parent.getPredCount();
                if (1 == numChildsOfParent) {
                    currInfo.x = parentInfo.x;
                } else {
                    int pos = -1;
                    for (int k = numChildsOfParent; --k >= 0;) {
                        if (parent.getPred(k) == curr) {
                            pos = k;
                            break;
                        }
                    }
                    util.Assert.isTrue(pos != -1);
                    currInfo.x = parentInfo.x - ((numChildsOfParent - 1) * deltaX / 2) + pos * deltaX;
                }
            } else {
                currInfo.x = (getElementInfo(curr.getSucc(0)).x + getElementInfo(curr.getSucc(numParents - 1)).x) / 2;
            }
            getElementInfo(curr).y = drawParams.getGridSizeY() * (lattice.getHeight() - curr.getHeight());
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

    /**
     * Insert the method's description here.
     * Creation date: (17.02.01 2:46:21)
     * @param index int
     * @param obj java.lang.Object
     */
    private ElementInfo getElementInfo(LatticeElement el) {
        return getElementInfo(el.getIndex());
    }

    /**
     @deprecated
     */
    //----------------------------------------------
    private boolean isPredessorsOrdered(LatticeElement el) {
        return checkIsElementsOfIteratorOrderedByPosition(el.predecessorElements());
    }


    /**
     @deprecated
     */
    //----------------------------------------------
    private boolean isSuccessorsOrdered(LatticeElement el) {
        return checkIsElementsOfIteratorOrderedByPosition(el.successorElements());
    }

    //--------------------------------------------
    private void printPredPos(LatticeElement el) {
        printElementsFromIteratorWithPositions("Predessors: ", el.predecessorElements());
    }

    private void printSuccPos(LatticeElement el) {
        printElementsFromIteratorWithPositions("Successors: ", el.successorElements());
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

    private void printElementsFromIteratorWithPositions(String name, ConceptIterator enum) {
        System.out.print(name);
        System.out.print("{ ");
        while (enum.hasNext()) {
            LatticeElement elm = enum.nextConcept();
            System.out.print("[" + elm.getIndex() + ";" + getElementInfo(elm).rank + ";" + getElementInfo(elm).posInRank + "]");
        }
        System.out.println(" } ");
    }

    //--------------------------------------------

    /**
     @deprecated
     */


    interface ElementInfoProcessor{
        void processElementInfo(ElementInfo elementInfo);
    }

    protected void assignCoordsToLattice() {

        int latticeWidth = calcLatticeWidth();
        int height = lattice.getHeight();

//	calcUnitElementPosition
        LatticeElement zero = lattice.getZero();
        ElementInfo zeroInfo = getElementInfo(zero);
        zeroInfo.x = drawParams.getGridSizeX() * latticeWidth / 2;
        zeroInfo.y = (height - zero.getHeight()) * drawParams.getGridSizeY();

        for (int i = 1; i < ranks.length; i++) {
            assignCoordsForRank(i);
            int straitenIterCount = 10;
            while (straightenLayoutForRank(i) && straitenIterCount > 0) {
                straitenIterCount--;
            }
        }
        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                LatticeElement curr = ranks[i][j];
                if (curr.isVirtual()) {
                    continue;
                }
                ElementInfo currInfo = getElementInfo(curr);
                currInfo.xPosDirect = currInfo.x;
            }
        }
        for (int i = ranks.length - 1; --i >= 0;) {
            assignCoordsForRankReverse(i);
            int straitenIterCount = 10;
            while (straightenLayoutForRank(i) && straitenIterCount > 0) {
                straitenIterCount--;
            }
        }

        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                LatticeElement curr = ranks[i][j];
                if (curr.isVirtual()) {
                    continue;
                }
                ElementInfo currInfo = getElementInfo(curr);
                currInfo.xPosReverse = currInfo.x;
            }
        }
        for (int i = ranks.length; --i >= 0;) {
            for (int j = ranks[i].length; --j >= 0;) {
                LatticeElement curr = ranks[i][j];
                if (curr.isVirtual()) {
                    continue;
                }
                ElementInfo currInfo = getElementInfo(curr);
                currInfo.x = (currInfo.xPosReverse + currInfo.xPosDirect) / 2.;
            }

            int straitenIterCount = 10;
            while (straightenLayoutForRank(i) && straitenIterCount > 0) {
                straitenIterCount--;
            }
        }

        fireLayoutChanged();
    }

    protected static final int edgeSlack(Edge currEdge) {
        return currEdge.getLength() - 1;
    }


    //------------------------------------------
    static void exchangeEdges(List edges, Edge toSwap) {
        int index = edges.indexOf(toSwap);
        util.Assert.isTrue(-1 != index);
        edges.set(index, edges.get(index + 1));
        edges.set(index + 1, toSwap);
    }

}