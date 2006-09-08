/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators;


import conexp.core.Lattice;
import conexp.core.LatticeElement;

public class EdgeMinSupportSelector extends SimpleEdgeIterator {
    protected final int minSupport;

    /**
     * EdgeSelector constructor comment.
     */
    public EdgeMinSupportSelector(Lattice lat, int minSupport) {
        super(lat);
        this.minSupport = minSupport;
    }

    public boolean acceptElement(LatticeElement itemset) {
        return itemset.getObjCnt() >= minSupport;
    }

}
