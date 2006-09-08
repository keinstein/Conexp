/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.ImplicationSet;

import java.util.Iterator;

public class ImplicationSetCompareSet extends CompareSetBase {
    final ImplicationSet implicationSet;

    public ImplicationSetCompareSet(ImplicationSet implicationSet) {
        this.implicationSet = implicationSet;
    }

    protected Iterator getCollectionIterator() {
        return implicationSet.iterator();
    }

    protected KeyValuePair makeKeyValuePair(Object obj) {
        return new KeyValuePair(obj, obj);
    }

    public int getSize() {
        return implicationSet.getSize();
    }
}
