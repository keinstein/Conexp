/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.utils;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import util.Assert;

public class PowerSetIterator {

    private ModifiableSet current;
    private ModifiableSet next = null;
    private int sizeOfSet;

    public PowerSetIterator(int size) {
        Assert.isTrue(size >= 0);
        current = ContextFactoryRegistry.createSet(size);
        next = current;
        sizeOfSet = current.size();
    }


    public boolean hasNext() {
        return next != null;
    }

    public ModifiableSet nextSet() {
        Assert.isTrue(hasNext());
        ModifiableSet toReturn = next.makeModifiableSetCopy();
        next = null;
        boolean wasModified = false;
        for (int i = sizeOfSet; --i >= 0;) {
            if (!current.in(i)) {
                current.put(i);
                wasModified = true;
                break;
            }
            current.remove(i);
        }
        if (wasModified) {
            next = current;
        }
        return toReturn;
    }

}
