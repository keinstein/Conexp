/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.calculationstrategies;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import conexp.core.Set;



public abstract class NextClosedSetClosureSystemGeneratorBase extends NextClosedSetAlgorithmBase {

    protected abstract boolean closureAttr(ModifiableSet set, int j, ModifiableSet notJ);

    protected abstract void onFinishCalc();

    protected abstract void onStartCalc();

    //-----------------------------------------------------
    private void nextClosedSetAttr() {
        final int numAttr = getAttributeCount();
        ModifiableSet notJ = ContextFactoryRegistry.createSet(numAttr);
        zeroClosureAttr();
        addZeroElement(attrSet);
        int j = numAttr - 1;
        ModifiableSet nextPrefix = ContextFactoryRegistry.createSet(numAttr);
        while (j >= 0) {
            //that is a not equal g
            j = numAttr - 1;
            notJ.clearSet();
            for (; j >= 0; j--) {
                notJ.put(j);
                if (!attrSet.in(j)) {
                    nextPrefix.copy(attrSet);
                    nextPrefix.andNot(notJ);
                    if (closureAttr(nextPrefix, j, notJ)) {
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
