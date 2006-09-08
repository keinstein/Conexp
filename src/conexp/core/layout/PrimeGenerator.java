/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;


public class PrimeGenerator {
    // I didn't have 3 and 5 before.
    private static final int[] primes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    private int primePointer = -1;
    // methods

    public int nextPrime() {
        primePointer++;
        if (primePointer == primes.length) {
            primePointer = 0;
        }
        return primes[primePointer];
    }

    /**
     * Insert the method's description here.
     * Creation date: (05.03.01 2:21:53)
     */
    public void reset() {
        primePointer = -1;
    }
}
