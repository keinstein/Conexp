/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;

import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;

import java.util.Iterator;


public class LatticeElementPredCompareSet extends CompareSetBase{
    private LatticeElementCollection predecessors;

    public LatticeElementPredCompareSet(LatticeElement el) {
        predecessors = el.getPredecessors();
    }

    protected Iterator getCollectionIterator() {
        return predecessors.iterator();
    }

    protected KeyValuePair makeKeyValuePair(Object obj) {
        LatticeElement pred = (LatticeElement)obj;
        return new KeyValuePair(pred.getAttribs(), pred);
    }

    public int getSize() {
        return predecessors.getSize();
    }
}
