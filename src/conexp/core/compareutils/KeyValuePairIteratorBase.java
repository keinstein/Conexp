package conexp.core.compareutils;

import util.collection.IteratorWrapperBase;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public abstract class KeyValuePairIteratorBase extends IteratorWrapperBase implements KeyValuePairIterator{
    public KeyValuePairIteratorBase(Iterator innerIterator) {
        super(innerIterator);
    }

    public Object next() {
        return nextKeyValuePair();
    }
}
