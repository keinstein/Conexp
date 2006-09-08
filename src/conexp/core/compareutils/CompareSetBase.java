/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.compareutils;

import java.util.Iterator;



public abstract class CompareSetBase implements ICompareSet {
    protected abstract Iterator getCollectionIterator();

    protected abstract KeyValuePair makeKeyValuePair(Object obj);

    public KeyValuePairIterator iterator() {
        KeyValuePairIterator keyValuePairIterator = new KeyValuePairIteratorBase(getCollectionIterator()) {
            public KeyValuePair nextKeyValuePair() {
                return makeKeyValuePair(innerIterator.next());
            }
        };
        return keyValuePairIterator;
    }
}
