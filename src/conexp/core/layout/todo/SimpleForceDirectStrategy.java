/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.todo;

import conexp.core.Edge;
import conexp.core.LatticeElement;

public class SimpleForceDirectStrategy implements ForceDirectStrategy {
    ForceDirectParams fdParams;

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 23:03:11)
     *
     * @param graph graphlib.GraphModel
     */
    public SimpleForceDirectStrategy() {
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:49:29)
     *
     * @param e graphlib.Edge
     */
    public void attractEdgeNodes(Edge e, double distance, double[] forceFactors) {
        forceFactors[0] = forceFactors[1] = -1.0 / distance * fa(distance);
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:28:43)
     *
     * @param v     graphlib.Node
     * @param force double
     * @param Temp  double
     * @return double
     */
    public double deltaMove(LatticeElement v, double force, double Temp) {
        return 1.0 / force * Math.min(force, Temp);
    }

    protected final double fa(double x) {
        double k = fdParams.k();
        double ac = fdParams.ac();
        double ae = fdParams.ae();
        return ac * Math.pow(x, ae) / k;
    }

    protected final double fr(double x) {
        double k = fdParams.k();
        double rc = fdParams.rc();
        double re = fdParams.re();
        return rc * (k * k) / Math.pow(x, re);
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:22:40)
     *
     * @param v graphlib.Node
     * @return double
     */
    public double repulsiveForce(LatticeElement v, double distance) {
        return 1.0 / distance * fr(distance);
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 2:20:00)
     */
    public void setForceDirectParams(ForceDirectParams fdParams) {
        this.fdParams = fdParams;
    }
}
