package conexp.core.layout.todo;

import conexp.core.Edge;
import conexp.core.LatticeElement;

public interface ForceDirectStrategy {
    public void attractEdgeNodes(Edge e, double distance, double[] forceFactors);

    public double deltaMove(LatticeElement v, double force, double Temp);

    double repulsiveForce(LatticeElement v, double distance);

    public void setForceDirectParams(ForceDirectParams fdParams);
}