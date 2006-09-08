/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.util.Iterator;


public interface ConceptIterator extends Iterator {
    LatticeElement nextConcept();
}
