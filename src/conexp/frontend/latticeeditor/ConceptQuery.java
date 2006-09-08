/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.Set;

import java.util.Iterator;

public interface ConceptQuery {
    Set getQueryIntent();

    Set getAttributeMask();

    int getExtentSize();

    int getOwnObjectsCount();

    boolean hasOwnObjects();

    int getStability();

    boolean isInnermost();

    ConceptQuery makeCombinedQuery(ConceptQuery other, boolean isTop, boolean isInnermost);


    boolean hasOwnAttribs();

    int getOwnAttribsCount();

    Iterator ownAttribsIterator();

    Iterator extentIterator();

    Iterator intentIterator();

    Iterator ownObjectsIterator();
}
