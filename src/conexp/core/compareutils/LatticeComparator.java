/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.compareutils;

import conexp.core.Lattice;


public class LatticeComparator extends BaseComparator {
    public LatticeComparator(Lattice one, Lattice two) {
        super(new LatticeElementCompareInfoFactory(),
                new ConceptCollectionCompareSet(one),
                new ConceptCollectionCompareSet(two));
    }
}
