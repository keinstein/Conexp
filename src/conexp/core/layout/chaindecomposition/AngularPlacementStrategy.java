/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout.chaindecomposition;


public class AngularPlacementStrategy extends SimplePlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:49:44)
     * @return double
     * @param base double
     * @param currChain int
     * @param maxChain int
     */
    public double calcYCoord(double base, int currChain, int maxChain) {
        return base * (Math.abs(2 * currChain - maxChain) / 2.0 + 1);
    }
}
