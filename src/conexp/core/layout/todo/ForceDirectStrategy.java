/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.todo;

import conexp.core.Edge;
import conexp.core.LatticeElement;

public interface ForceDirectStrategy {
    void attractEdgeNodes(Edge e, double distance, double[] forceFactors);

    double deltaMove(LatticeElement v, double force, double Temp);

    double repulsiveForce(LatticeElement v, double distance);

    void setForceDirectParams(ForceDirectParams fdParams);
}
