package conexp.core.calculationstrategies;

import conexp.core.AbstractConceptCalcStrategy;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class NextClosedSetAlgorithmBase extends AbstractConceptCalcStrategy {
    //-----------------------------------------------------
    protected ModifiableSet attrSet;
    protected ModifiableSet closedObjects;
    protected ModifiableSet nextClosure;
    protected ModifiableSet nextElementInLecticalOrder;
    protected ModifiableSet allAttrSet;

    /**
     *  Description of the Method
     */
    protected void startCalc() {
        int col = getAttributeCount();
        closedObjects = ContextFactoryRegistry.createSet(getObjectCount());
        attrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet.fill();
        nextClosure = ContextFactoryRegistry.createSet(col);
        nextElementInLecticalOrder = ContextFactoryRegistry.createSet(col);
    }

    private int getObjectCount() {
        return rel.getRowCount();
    }

    private int getAttributeCount() {
        return rel.getColCount();
    }

    public void tearDown() {
        super.tearDown();
        attrSet = null;
        closedObjects = null;
        nextClosure = null;
        nextElementInLecticalOrder = null;
        allAttrSet = null;
    }

    //-----------------------------------------------------
    protected void zeroClosureAttr() {
        int numObj = getObjectCount();
        closedObjects.fill();
        attrSet.copy(allAttrSet);
        for (int j = numObj; --j >= 0;) {
            attrSet.and(rel.getSet(j));
        }
    }
}
