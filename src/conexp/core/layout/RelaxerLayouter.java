/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import conexp.core.LatticeElement;
import util.Assert;

public class RelaxerLayouter extends GenericLayouter {
    static class RelaxerLayoutConceptInfo extends LayoutConceptInfo {
        double dx;
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.2001 15:58:34)
     *
     * @param index int
     * @return java.lang.Object
     */
    public RelaxerLayoutConceptInfo getConceptInfo(int index) {
        return (RelaxerLayoutConceptInfo) elementMap[index];
    }

    public RelaxerLayoutConceptInfo getConceptInfo(LatticeElement el) {
        return (RelaxerLayoutConceptInfo) elementMap[el.getIndex()];
    }

    /**
     * improveOnce method comment.
     */
    public synchronized void improveOnce() {
        processEdgesForces();
        processVertexVertextForces();
        assignCoordsToLattice();
        currIter++;
    }

    protected LayoutConceptInfo makeConceptInfo() {
        return new RelaxerLayoutConceptInfo();
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.2001 15:39:29)
     */
    public void performLayout() {
    }

    private int currIter;
    private double repel = 1;

    /**
     * Insert the method's description here.
     * Creation date: (14.03.01 0:16:02)
     */
    public void processEdgesForces() {
        if (0 == currIter % 2) {
            for (int i = lattice.conceptsCount(); --i >= 0;) {
                LatticeElement curr = lattice.elementAt(i);
                RelaxerLayoutConceptInfo currInfo = getConceptInfo(curr);

                double idealLenUnit = 0;
                for (int j = curr.getPredCount(); --j >= 0;) {
                    LatticeElement other = curr.getPred(j);
                    double len = distanceBetween(curr, other);
                    int deltaHeight = curr.getHeight() - other.getHeight();
                    Assert.isTrue(deltaHeight > 0);
                    idealLenUnit += len / deltaHeight;
                }
                idealLenUnit /= curr.getPredCount();
                for (int j = curr.getPredCount(); --j >= 0;) {
                    LatticeElement other = curr.getPred(j);
                    RelaxerLayoutConceptInfo otherInfo = getConceptInfo(other);
                    double vx = currInfo.x - otherInfo.x;
                    double len = distanceBetween(curr, other);
                    double dx;
                    if (0 == len) {
                        dx = Math.random();
                    } else {
                        int deltaHeight = curr.getHeight() - other.getHeight();
                        Assert.isTrue(deltaHeight > 0);
                        double idealEdgeLen = idealLenUnit * deltaHeight;
                        double force = (idealEdgeLen - len) / (3 * len);
                        dx = force * vx;
                    }
                    currInfo.dx += dx;
                }
            }
        } else {
            for (int i = lattice.conceptsCount(); --i >= 0;) {
                LatticeElement curr = lattice.elementAt(i);
                RelaxerLayoutConceptInfo currInfo = getConceptInfo(curr);

                double idealLenUnit = 0;
                for (int j = curr.getSuccCount(); --j >= 0;) {
                    LatticeElement other = curr.getSucc(j);
                    double len = distanceBetween(curr, other);
                    int deltaHeight = other.getHeight() - curr.getHeight();
                    Assert.isTrue(deltaHeight > 0);
                    idealLenUnit += len / deltaHeight;
                }
                idealLenUnit /= curr.getSuccCount();
                for (int j = curr.getSuccCount(); --j >= 0;) {
                    LatticeElement other = curr.getSucc(j);
                    RelaxerLayoutConceptInfo otherInfo = getConceptInfo(other);
                    double vx = currInfo.x - otherInfo.x;
                    double len = distanceBetween(curr, other);
                    double dx;
                    if (0 == len) {
                        dx = Math.random();
                    } else {
                        int deltaHeight = other.getHeight() - curr.getHeight();
                        Assert.isTrue(deltaHeight > 0);
                        double idealEdgeLen = idealLenUnit * deltaHeight;
                        double force = (idealEdgeLen - len) / (3 * len);
                        dx = force * vx;
                    }
                    currInfo.dx += dx;
                }
            }

        }
    }

    private double distanceBetween(LatticeElement curr, LatticeElement other) {
        RelaxerLayoutConceptInfo currInfo = getConceptInfo(curr), otherInfo = getConceptInfo(other);

        double vx = currInfo.x - otherInfo.x;
        double vy = currInfo.y - otherInfo.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.01 23:47:31)
     */
    private void processVertexVertextForces() {
        int gridXSquare = drawParams.getGridSizeX() * drawParams.getGridSizeX();
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            LatticeElement curr = lattice.elementAt(i);
            RelaxerLayoutConceptInfo currInfo = getConceptInfo(curr);
            double dx = 0, dy = 0;

            for (int j = lattice.conceptsCount(); --j >= 0;) {
                if (i == j) {
                    continue;
                }
                LatticeElement other = lattice.elementAt(j);
                RelaxerLayoutConceptInfo otherInfo = getConceptInfo(other);
                double vy = currInfo.y - otherInfo.y;
                if (0 != vy) {
                    continue;
                }
                double vx = currInfo.x - otherInfo.x;
                double lenSqr = vx * vx + vy * vy;
                if (0 == lenSqr) {
                    dx += Math.random();
                    dy += Math.random();
                } else {
                    if (lenSqr < 9 * gridXSquare) {
                        dx += vx / lenSqr;
                        dy += vx / lenSqr;
                    }
                }
            }
            double dlen = dx * dx + dy * dy;
            if (dlen != 0) {
                dlen = Math.sqrt(dlen);
                currInfo.dx += dx / dlen * repel;
            } //no attraction due to absence of grid points
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.01 23:51:33)
     */
    protected void assignCoordsToLattice() {
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            LatticeElement curr = lattice.elementAt(i);
            RelaxerLayoutConceptInfo currInfo = getConceptInfo(curr);
            int dx = (int) currInfo.dx;
            if (dx < -5) {
                dx = -5;
            }
            if (dx > 5) {
                dx = 5;
            }
            currInfo.x += dx;
            currInfo.dx /= 2;
        }
        fireLayoutChanged();
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 14:13:25)
     *
     * @return boolean
     */
    public boolean isDone() {
        // for relaxation layout we don't have stop criteria, it should be stopped by setting other layuot as active
        return false;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 13:06:22)
     *
     * @return boolean
     */
    public boolean isIncremental() {
        return true;
    }
}
