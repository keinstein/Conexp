package conexp.core.layout.chaindecomposition;

/**
 * Insert the type's description here.
 * Creation date: (10.03.01 10:47:32)
 * @author
 */
public class StraightPlacementStrategy extends SimplePlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:47:32)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    public double calcYCoord(double base, int currChain, int maxChain) {
        return base;
    }
}