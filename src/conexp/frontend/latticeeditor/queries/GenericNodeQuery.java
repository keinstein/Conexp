/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.queries;

import conexp.core.ContextFunctions;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;
import util.collection.NullIterator;

import java.util.Iterator;
import java.util.List;

public class GenericNodeQuery extends QueryBase {
    Set queryIntent;
    List attributeContingent;
    boolean innermost;

    public GenericNodeQuery(ExtendedContextEditingInterface cxt, Set queryIntent, java.util.List attributeContingent, boolean isInnermost, Set attributeMask) {
        super(cxt, attributeMask);
        this.queryIntent = queryIntent;
        this.attributeContingent = attributeContingent;
        this.innermost = isInnermost;
    }

    public boolean isInnermost() {
        return innermost;
    }

    public Set getQueryIntent() {
        return queryIntent;
    }

    public boolean hasOwnAttribs() {
        return null != attributeContingent && attributeContingent.size() > 0;
    }

    public int getOwnAttribsCount() {
        if (null == attributeContingent) {
            return 0;
        }
        return attributeContingent.size();
    }

    public Iterator ownAttribsIterator() {
        if (null == attributeContingent) {
            return NullIterator.makeNull();
        }
        return attributeContingent.iterator();
    }

    public Iterator ownObjectsIterator() {
        return NullIterator.makeNull();
    }

    boolean extentCalculated = false;
    int cachedExtentSize = -1;

    public int getExtentSize() {
        if (!extentCalculated) {
            cachedExtentSize = ContextFunctions.idealSize(getQueryIntent(), cxt, getAttributeMask());
            extentCalculated = true;
        }
        return cachedExtentSize;
    }

    boolean contingentSizeCalculated = false;
    int cachedContingentSize = -1;

    public int getOwnObjectsCount() {
        if (!contingentSizeCalculated) {
            cachedContingentSize = ContextFunctions.contingentSize(queryIntent, cxt, getAttributeMask());
            contingentSizeCalculated = true;
        }
        return cachedContingentSize;
    }

}
