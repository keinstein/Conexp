package conexp.core.calculationstrategies;

import conexp.core.ModifiableSet;
import conexp.core.ContextFactoryRegistry;
import conexp.core.Set;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public abstract class NextClosedSetClosureSystemGeneratorBase extends NextClosedSetAlgorithmBase {
    //-----------------------------------------------------
    protected abstract boolean closureAttr(ModifiableSet set, int j, ModifiableSet notJ);

    protected abstract void onFinishCalc();

    protected abstract void onStartCalc();

    //-----------------------------------------------------
    protected void nextClosedSetAttr() {
        final int numAttr = rel.getColCount();
        ModifiableSet b = ContextFactoryRegistry.createSet(numAttr);
        ModifiableSet notJ = ContextFactoryRegistry.createSet(numAttr);
        zeroClosureAttr();
        addZeroElement(attrSet);
        int j = numAttr - 1;
        while (j >= 0) {
            //that is a not equal g
            j = numAttr - 1;
            notJ.clearSet();
            for (; j >= 0; j--) {
                notJ.put(j);
                if (!attrSet.in(j)) {
                    b.copy(attrSet);
                    b.andNot(notJ);
                    if (closureAttr(b, j, notJ)) {
                        attrSet.copy(nextClosure);
                        addNextClosedElement(attrSet);
                        break;
                    }
                }
            }
        }
    }

    protected abstract void addNextClosedElement(Set elementIntent);

    protected abstract void addZeroElement(Set zeroElementIntent);

    //-----------------------------------------------------
    public void calculateConceptSet() {
        startCalc();
        //*DBG*/ rel.printDebugData();
        onStartCalc();
        nextClosedSetAttr();
        onFinishCalc();
    }
}
