package conexp.core.compareutils;

import conexp.core.Lattice;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 13:38:19)
 * @author
 */
public class LatticeComparator extends BaseComparator{
    public LatticeComparator(Lattice one, Lattice two) {
        super(new LatticeElementCompareInfoFactory(),
                new ConceptCollectionCompareSet(one),
                new ConceptCollectionCompareSet(two));
    }
}