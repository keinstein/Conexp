/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.compareutils;

import conexp.core.ImplicationSet;


public class ImplicationSetComparator extends BaseComparator {

    public ImplicationSetComparator(ImplicationSet one, ImplicationSet two) {
        super(new DefaultCompareInfoFactory(),
                new ImplicationSetCompareSet(one),
                new ImplicationSetCompareSet(two));
    }
}
