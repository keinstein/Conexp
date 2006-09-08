/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;

import java.util.Iterator;


public class LatticeElementCollectionCompareSet extends CompareSetBase {
    private LatticeElementCollection collection;

    public LatticeElementCollectionCompareSet(LatticeElementCollection predecessors) {
        this.collection = predecessors;
    }

    protected Iterator getCollectionIterator() {
        return collection.iterator();
    }

    protected KeyValuePair makeKeyValuePair(Object obj) {
        LatticeElement pred = (LatticeElement) obj;
        return new KeyValuePair(pred.getAttribs(), pred);
    }

    public int getSize() {
        return collection.getSize();
    }
}
