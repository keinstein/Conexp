package conexp.core.layout.chaindecomposition;

/**
 * Insert the type's description here.
 * Creation date: (10.03.01 10:32:18)
 * @author
 */
public abstract class SimplePlacementStrategy implements ConceptPlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:32:19)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    public double calcXCoord(double base, int currChain, int maxChain) {
        return base * (2 * currChain - maxChain) / 2.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:32:18)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    public abstract double calcYCoord(double base, int currChain, int maxChain);
}