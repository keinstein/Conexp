/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.Lattice;


public class LatticeComparator extends ConceptCollectionComparator {
    public LatticeComparator(Lattice one, Lattice two) {
        super(new LatticeElementCompareInfoFactory(),
                one,
                two);
    }
}
