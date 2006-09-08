/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.bitset;

import conexp.core.ContextFactory;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;
import conexp.core.SetRelation;


public class BitSetFactory implements ContextFactory {
    private BitSetFactory() {
    }

    public ModifiableBinaryRelation createRelation(int rowCount, int colCount) {
        return new SetRelation(rowCount, colCount);
    }

    public ModifiableSet createSet(int size) {
        return new BitSet(size);
    }

    static final ContextFactory ourInstance = new BitSetFactory();

    public static ContextFactory getInstance() {
        return ourInstance;
    }
}
