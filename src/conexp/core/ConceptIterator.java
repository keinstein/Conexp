/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

import java.util.Iterator;

//todo: rename into lattice element iterator

public interface ConceptIterator extends Iterator {
    LatticeElement nextConcept();
}
