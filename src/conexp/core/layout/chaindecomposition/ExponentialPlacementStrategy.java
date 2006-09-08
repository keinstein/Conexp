/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;


public class ExponentialPlacementStrategy implements ConceptPlacementStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:20:32)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public double calcXCoord(double base, int currChain, int maxChain) {
        int med = maxChain / 2;
        double factor = base / (ldexp(1.0, med) + ldexp(1.0, maxChain - med));
        double x = ldexp(factor, currChain);
        double y = ldexp(factor, maxChain - currChain);
        return x - y;
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:20:32)
     *
     * @param base      double
     * @param currChain int
     * @param maxChain  int
     * @return double
     */
    public double calcYCoord(double base, int currChain, int maxChain) {
        int med = maxChain / 2;

        // Der Faktor fuer die Skalierung wird so berechnet, dass der Betrag der
        // kleinsten y-Koordinate gerade base ist. Der Faktor wird nur fuer
        // DM_EXPONENTIAL benutzt.

        double factor = base / (ldexp(1.0, med) + ldexp(1.0, maxChain - med));
        double x = ldexp(factor, currChain);
        double y = ldexp(factor, maxChain - currChain);
        return x + y;
    }

    /**
     * Insert the method's description here.
     * Creation date: (05.03.01 3:11:41)
     *
     * @param base double
     * @param exp  double
     * @return double
     */
    private static double ldexp(double base, double exp) {
        return base * Math.pow(2.0, exp);
    }
}
