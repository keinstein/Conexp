/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;


public abstract class SimplePlacementStrategy implements ConceptPlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:32:19)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public double calcXCoord(double base, int currChain, int maxChain) {
        return base * (2 * currChain - maxChain) / 2.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:32:18)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public abstract double calcYCoord(double base, int currChain, int maxChain);
}
