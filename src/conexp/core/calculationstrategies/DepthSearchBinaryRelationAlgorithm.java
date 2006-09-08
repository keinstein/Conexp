/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.ContextFactoryRegistry;
import conexp.core.DefaultBinaryRelationProcessor;
import conexp.core.ModifiableSet;

public class DepthSearchBinaryRelationAlgorithm extends DefaultBinaryRelationProcessor {
    protected ModifiableSet newExtent;
    protected ModifiableSet outerSet;
    protected ModifiableSet newIntent;

    protected ModifiableSet tempAttrSet;

    protected ModifiableSet allAttrSet;
    protected ModifiableSet[] currObjects;
    protected ModifiableSet[] currAttribs;

    protected void initObjectsAndAttribs() {
        int cols = rel.getColCount();
        int rows = rel.getRowCount();
        int maxDepth = Math.min(cols, rows) + 1;

        tempAttrSet = ContextFactoryRegistry.createSet(cols);

        outerSet = ContextFactoryRegistry.createSet(cols);
        newIntent = ContextFactoryRegistry.createSet(cols);
        newExtent = ContextFactoryRegistry.createSet(rows);

        allAttrSet = ContextFactoryRegistry.createSet(cols);
        allAttrSet.fill();

        currObjects = new ModifiableSet[maxDepth];
        currAttribs = new ModifiableSet[maxDepth];

        for (int i = 0; i < maxDepth; i++) {
            currObjects[i] = ContextFactoryRegistry.createSet(rows);
            currAttribs[i] = ContextFactoryRegistry.createSet(cols);
        }
    }

    public void tearDown() {
        super.tearDown();
        newExtent = null;
        outerSet = null;
        newIntent = null;

        tempAttrSet = null;
        allAttrSet = null;

        currObjects = null;
        currAttribs = null;
    }
}
