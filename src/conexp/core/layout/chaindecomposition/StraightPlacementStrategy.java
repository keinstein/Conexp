/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;


public class StraightPlacementStrategy extends SimplePlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:47:32)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public double calcYCoord(double base, int currChain, int maxChain) {
        return base;
    }
}
