/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.Implication;
import conexp.core.ImplicationSet;

public class ImplicationSetCompareSet implements ICompareSet {
    final ImplicationSet implicationSet;

    public ImplicationSetCompareSet(ImplicationSet implicationSet) {
        this.implicationSet = implicationSet;
    }

    public KeyValuePair get(int index) {
        Implication impl = implicationSet.getImplication(index);
        return new KeyValuePair(impl, impl);
    }

    public int getSize() {
        return implicationSet.getSize();
    }
}
