/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.bitset;

import conexp.core.ContextFactory;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;
import conexp.core.SetRelation;


public class BitSetFactory implements ContextFactory {

    public ModifiableBinaryRelation createRelation(int rowCount, int colCount) {
        return new SetRelation(rowCount, colCount);
    }

    public ModifiableSet createSet(int size) {
        return new BitSet(size);
    }
}
