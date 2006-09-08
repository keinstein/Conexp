/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.bitset;

import conexp.core.ModifiableSet;
import conexp.core.Set;

public abstract class BasicBitSet implements ModifiableSet, Cloneable {
    protected int size;

    protected BasicBitSet(int nbits) {
        if (nbits < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(nbits));
        }
        this.size = nbits;
    }


    public int nextOut(int prev) {
        if (prev < 0) {
            return -1;
        }
        for (int i = prev + 1; i < size; i++) {
            if (!in(i)) {
                return i;
            }
        }
        return -1;
    }

    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().isInstance(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return isEquals((Set) obj);
    }

    public Object clone() {
        return makeModifiableSetCopy();
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(3 * size() + 2);
        String separator = "";
        buffer.append('{');
        for (int i = 0; i < size(); i++) {
            buffer.append(separator);
            buffer.append(in(i) ? '1' : '0');
            separator = ", ";
        }
        buffer.append('}');
        return buffer.toString();
    }


    public boolean isSupersetOf(Set other) {
        return other.isSubsetOf(this);
    }


}
