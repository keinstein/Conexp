package conexp.core.compareutils;

import conexp.core.ImplicationSet;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 13:38:19)
 * @author
 */
public class ImplicationSetComparator extends BaseComparator {

    public ImplicationSetComparator(ImplicationSet one, ImplicationSet two) {
        super(new DefaultCompareInfoFactory(),
                new ImplicationSetCompareSet(one),
                new ImplicationSetCompareSet(two));
    }
}