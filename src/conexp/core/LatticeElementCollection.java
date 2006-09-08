/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core;

import java.util.Comparator;


public interface LatticeElementCollection {
    int getSize();

    boolean isEmpty();

    LatticeElement get(int index);

    ConceptIterator iterator();

    void sort(Comparator latticeElementComparator);

}
