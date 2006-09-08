/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.todo;

import conexp.core.Edge;
import conexp.core.LatticeElement;
import conexp.core.layout.GenericForceDirectedLayouter;
import conexp.core.layout.Point3D;

public class ForceDirectedLayouter extends GenericForceDirectedLayouter {

    static class ElementInfo extends ForceDirectConceptInfo {
        Point3D deltaMove = new Point3D();

        void setDeltaMoveToZero() {
            deltaMove.setLocation(0, 0);
            deltaMove.z = 0;
        }

        void adjustForce(double dx, double dy, double dz) {
            deltaMove.setLocation(deltaMove.getX() + dx,
                    deltaMove.getY() + dy);
            deltaMove.z += dz;
        }

        void addDeltaToCurrPosWithFactor(double factor) {
            coords.setLocation(coords.getProjectedX() + deltaMove.getProjectedX() * factor,
                    coords.getProjectedY() + deltaMove.getProjectedY() * factor);

            coords.z += deltaMove.z * factor;
        }
    }

    protected double _time;
    private ForceDirectStrategy strategy;
    private ForceDirectParams fdParams = new ForceDirectParams();
    protected int numIterations = 30;

    /**
     * Insert the method's description here.
     * Creation date: (20.02.01 0:55:19)
     */
    private void calcInitialVerticesPosition() {
        primeGen.reset();
        float PI = (float) Math.PI;
        int size = topSorted.length;
        for (int i = 0; i < size;) {
            ElementInfo inf = getLocalConceptInfo(topSorted[i].getIndex());
            int rank = inf.rank;
            // j will be the number of vertices with the same rank as the ith one.
            int j;
            for (j = 1; j < size - i; j++) {
                if (rank != getConceptInfo(topSorted[i + j]).rank) {
                    break;
                }
            }
            float angle = 2 * PI / j;

            for (int k = 0; k < j; k++) {
                Point3D pt = getConceptInfo(topSorted[i + k]).coords;
                pt.setLocation(drawParams.getGridSizeX() * j
                        * Math.cos(k * angle),
                        drawParams.getGridSizeY() *
                                (getConceptInfo(lattice.getOne()).rank - rank));
            }
            i += j;
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:16:20)
     */
    void calculateAttractiveForces() {
        double[] forceFactors = new double[2];
        //for all edges
        int conceptCount = lattice.conceptsCount();
        for (int i = 0; i < conceptCount; i++) {
            LatticeElement curr = lattice.elementAt(i);
            int predCnt = curr.getPredCount();
            for (int j = 0; j < predCnt; j++) {
                Edge e = curr.getPredEdge(j);

                ElementInfo from = getLocalConceptInfo(e.getStart().getIndex());
                ElementInfo to = getLocalConceptInfo(e.getEnd().getIndex());

                double distance = Point3D.distance(from.coords, to.coords);
                if (distance > 0.00001) {
                    strategy.attractEdgeNodes(e, distance, forceFactors);
                    from.adjustForce((from.coords.getProjectedX() - to.coords.getProjectedX()) * forceFactors[0],
                            (from.coords.getProjectedY() - to.coords.getProjectedY()) * forceFactors[0],
                            0/*(from.coords.z-to.coords.z)*forceFactors[0]*/);
                    to.adjustForce((to.coords.getProjectedX() - from.coords.getProjectedX()) * forceFactors[1],
                            (to.coords.getProjectedY() - from.coords.getProjectedY()) * forceFactors[1],
                            0/*(to.coords.y-from.coords.y)*forceFactors[1]*/);
                }
            }
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:10:26)
     */
    void calculateRepulsiveForces() {
        double k = fdParams.k();
        int nodecnt = lattice.conceptsCount();
        for (int i = 0; i < nodecnt; ++i) {
            ElementInfo u = getLocalConceptInfo(lattice.elementAt(i).getIndex());
            u.setDeltaMoveToZero();
            for (int j = 0; j < nodecnt; ++j) {
                LatticeElement curr = lattice.elementAt(j);
                ElementInfo v = getLocalConceptInfo(curr.getIndex());
                double distance = Point3D.distance(u.coords, v.coords);
                if (distance > 3 * k) {
                    continue;
                }
                if (distance == 0.0) {
                    distance = 0.0001;
                }

                double factor = strategy.repulsiveForce(curr, distance);
                u.adjustForce((u.coords.getProjectedX() - v.coords.getProjectedX()) * factor,
                        (u.coords.getProjectedY() - v.coords.getProjectedY()) * factor,
                        0/*(u.coords.z-v.coords.z)*factor*/);
            }
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:14:11)
     */
    void displaceWithRegardToTemperature() {
        int nodecnt = lattice.conceptsCount();
        double temp = temp(_time) + fdParams.minTemp();
        for (int j = 0; j < nodecnt; ++j) {
            LatticeElement curr = lattice.elementAt(j);
            ElementInfo v = getLocalConceptInfo(curr.getIndex());
            double force = v.deltaMove.size();
            if (force < 0.00001) {
                continue;
            }
            v.addDeltaToCurrPosWithFactor(strategy.deltaMove(curr, force, temp));
        }
    }

    protected synchronized void forceDirectedPlacement() {
        calculateRepulsiveForces();
        calculateAttractiveForces();
        displaceWithRegardToTemperature();
        _time += 1.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.03.01 10:29:19)
     *
     * @param index int
     * @return java.lang.Object
     */
    public ElementInfo getLocalConceptInfo(int index) {
        return (ElementInfo) elementMap[index];
    }

    /**
     * Comments for the improveOnce method.
     */
    public void improveOnce() {
        forceDirectedPlacement();
        projectAndAssignCoords();
    }

    // Implementation of embedder interface, Init and Embed.
    //
    private void init() {
        _time = 1.0;
        fdParams.setLattice(lattice);
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 2:12:37)
     */
    protected void localInit() {
        init();
    }

    /**
     * Comments for the makeConceptInfo method.
     *
     * @return conexp.core.layout.GenericLayouter$LayoutConceptInfo
     */
    protected LayoutConceptInfo makeConceptInfo() {
        return new ElementInfo();
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 20:28:04)
     */
    public void performLayout() {
        // by number of iterations
        init();
        for (int i = 0; i < numIterations; i++) {
            forceDirectedPlacement();
        }
        projectAndAssignCoords();
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.01 1:31:23)
     */
    protected void projectAndAssignCoords() {
        for (int i = lattice.conceptsCount(); --i >= 0;) {
            LatticeElement el = lattice.elementAt(i);
            ElementInfo elInfo = getLocalConceptInfo(el.getIndex());
            elInfo.setX(elInfo.coords.getProjectedX());
            elInfo.setY(elInfo.coords.getProjectedY());
        }
    }

    public void setStrategy(ForceDirectStrategy newStrategy) {
        strategy = newStrategy;
        strategy.setForceDirectParams(fdParams);
    }

    protected double temp(double t) {
        return lattice.conceptsCount() / 2 / (1 + Math.exp(t / 8 - 5));
    }

    /**
     * Insert the method's description here.
     * Creation date: (25.04.01 8:35:55)
     */
    protected void assignCoordsToLattice() {
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 14:16:34)
     *
     * @return boolean
     */
    public boolean isDone() {
        return (int) _time > numIterations;
    }
}
