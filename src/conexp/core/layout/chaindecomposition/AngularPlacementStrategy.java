/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;


public class AngularPlacementStrategy extends SimplePlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:49:44)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public double calcYCoord(double base, int currChain, int maxChain) {
        return base * (Math.abs(2 * currChain - maxChain) / 2.0 + 1);
    }
}
