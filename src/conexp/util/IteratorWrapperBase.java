package conexp.util;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public abstract class IteratorWrapperBase implements Iterator{
    protected Iterator innerIterator;

    public IteratorWrapperBase(Iterator innerIterator) {
        this.innerIterator = innerIterator;
    }

    public boolean hasNext() {
        return innerIterator.hasNext();
    }

    public void remove() {
        innerIterator.remove();
    }
}
