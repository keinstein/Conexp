/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.todo;

import conexp.core.Edge;
import conexp.core.LatticeElement;


public class AnnealerForceDirectStrategy implements ForceDirectStrategy {
    protected ForceDirectParams fdParams;

    /**
     * AnnealerForceDirectStrategy constructor comment.
     */
    public AnnealerForceDirectStrategy() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:49:29)
     *
     * @param e graphlib.Edge
     */
    public void attractEdgeNodes(Edge e, double distance, double[] forceFactors) {
        LatticeElement from = e.getStart();
        LatticeElement to = e.getEnd();
        double k = fdParams.k();
        double pullto = distance / to.degree() * (distance / to.degree()) / k;
        double pullfrom = distance / from.degree() * (distance / from.degree()) / k;
        forceFactors[0] = -Math.max(pullfrom, 1.0) / distance * fa(distance); //from
        forceFactors[1] = -Math.max(pullto, 1.0) / distance * fa(distance); //to
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
    public double deltaMove(LatticeElement v, double force, double temp) {
        return v.degree() / force * Math.min(force, temp);
    }

    protected final double fa(double x) {
        return x * x / fdParams.k();
    }

    protected final double fr(double x) {
        double k = fdParams.k();
        return k * k / x;
    }

    /**
     * Insert the method's description here.
     * Creation date: (16.11.00 21:22:40)
     *
     * @param v graphlib.Node
     * @return double
     */
    public double repulsiveForce(LatticeElement v, double distance) {
        return v.degree() / distance * fr(distance);
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 2:20:00)
     */
    public void setForceDirectParams(ForceDirectParams fdParams) {
        this.fdParams = fdParams;
    }
}
