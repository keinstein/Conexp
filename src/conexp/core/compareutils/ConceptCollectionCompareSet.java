/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.ConceptsCollection;
import conexp.core.ItemSet;

import java.util.Iterator;


public class ConceptCollectionCompareSet extends CompareSetBase {
    protected final ConceptsCollection coll;

    public ConceptCollectionCompareSet(ConceptsCollection coll) {
        this.coll = coll;
    }

    protected Iterator getCollectionIterator() {
        return coll.elements();
    }

    public int getSize() {
        return coll.conceptsCount();
    }

    protected KeyValuePair makeKeyValuePair(Object obj) {
        ItemSet con = (ItemSet) obj;
        return new KeyValuePair(con.getAttribs(), con);
    }

}
