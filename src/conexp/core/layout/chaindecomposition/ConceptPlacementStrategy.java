package conexp.core.layout.chaindecomposition;

import conexp.util.GenericStrategy;

public interface ConceptPlacementStrategy extends GenericStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:07:28)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    double calcXCoord(double base, int currChain, int maxChain);

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:16:45)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    double calcYCoord(double base, int currChain, int maxChain);
}