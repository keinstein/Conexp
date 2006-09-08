/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionCompareSet extends CompareSetBase {
    final Collection collection;

    public CollectionCompareSet(Collection collection) {
        this.collection = collection;
    }

    protected Iterator getCollectionIterator() {
        return collection.iterator();
    }

    protected KeyValuePair makeKeyValuePair(Object obj) {
        return new KeyValuePair(obj, obj);
    }

    public int getSize() {
        return collection.size();
    }
}
