/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

public interface LatticeCalculator {
    final static String LATTICE_DRAWING_CHANGED = "LATTICE_DRAWING_CHANGED";
    final static String LATTICE_CLEARED = "LATTICE_CLEARED";

    public void clearLattice();

    public void calculateLattice();
}
