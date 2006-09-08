/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components;


public interface LatticeSupplierAndCalculator extends LatticeSupplier {
    void calculateAndLayoutLattice();
}
