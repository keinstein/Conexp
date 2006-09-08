/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.calculationstrategies;

import conexp.core.AbstractConceptCalcStrategy;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;



public abstract class NextClosedSetAlgorithmBase extends AbstractConceptCalcStrategy {
    //-----------------------------------------------------
    protected ModifiableSet attrSet;
    protected ModifiableSet nextClosure;
    protected ModifiableSet nextElementInLecticalOrder;
    protected ModifiableSet allAttrSet;

    /**
     * Description of the Method
     */
    protected void startCalc() {
        int col = getAttributeCount();
        attrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet.fill();
        nextClosure = ContextFactoryRegistry.createSet(col);
        nextElementInLecticalOrder = ContextFactoryRegistry.createSet(col);
    }

    protected abstract int getAttributeCount();

    public void tearDown() {
        super.tearDown();
        attrSet = null;
        nextClosure = null;
        nextElementInLecticalOrder = null;
        allAttrSet = null;
    }

    //-----------------------------------------------------
    protected abstract void zeroClosureAttr();

}
