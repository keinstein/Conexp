package conexp.core.compareutils;

import conexp.core.ConceptsCollection;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class ConceptCollectionComparator extends BaseComparator {

    public ConceptCollectionComparator(ConceptsCollection one, ConceptsCollection two) {
        this(DefaultCompareInfoFactory.getInstance(), one, two);
    }

    protected ConceptCollectionComparator(CompareInfoFactory compareInfoFactory, ConceptsCollection one, ConceptsCollection two) {
        super(compareInfoFactory, new ConceptCollectionCompareSet(one),
                new ConceptCollectionCompareSet(two));
    }
}
