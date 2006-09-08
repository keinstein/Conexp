/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

public interface LatticeCalculator {
    String LATTICE_DRAWING_CHANGED = "LATTICE_DRAWING_CHANGED";
    String LATTICE_CLEARED = "LATTICE_CLEARED";

    void clearLattice();

    void calculateLattice();
}
