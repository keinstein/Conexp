package conexp.core;

import java.util.Comparator;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public interface LatticeElementCollection{
    int getSize();
    LatticeElement get(int index);
    ConceptIterator iterator();
    void sort(Comparator latticeElementComparator);
}
