package conexp.core.enumerators;

/**
 * Insert the type's description here.
 * Creation date: (04.05.01 21:16:53)
 * @author Serhiy Yevtushenko
 */

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