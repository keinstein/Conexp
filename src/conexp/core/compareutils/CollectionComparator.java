/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import java.util.Collection;


public class CollectionComparator extends BaseComparator {

    public CollectionComparator(Collection one, Collection two) {
        super(DefaultCompareInfoFactory.getInstance(),
                new CollectionCompareSet(one),
                new CollectionCompareSet(two));
    }
}
