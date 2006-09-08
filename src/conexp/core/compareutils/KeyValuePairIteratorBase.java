/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.compareutils;

import util.collection.IteratorWrapperBase;

import java.util.Iterator;


public abstract class KeyValuePairIteratorBase extends IteratorWrapperBase implements KeyValuePairIterator {
    protected KeyValuePairIteratorBase(Iterator innerIterator) {
        super(innerIterator);
    }

    public Object next() {
        return nextKeyValuePair();
    }
}
