package conexp.core.compareutils;

import conexp.core.Lattice;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 13:38:19)
 * @author
 */
public class LatticeComparator {
    public final DiffMap map;
    public final boolean equal;

    /**
     * LatticeComparator constructor comment.
     */
    public LatticeComparator(Lattice one, Lattice two) {
        super();
        map = new DiffMap(new LatticeElementCompareInfoFactory());
        equal = map.compareSets(new ConceptCollectionCompareSet(one), new ConceptCollectionCompareSet(two));
    }
}