/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies;

import conexp.core.*;
import util.Assert;


public class NextClosedSetCalculator extends NextClosedSetClosureSystemGeneratorBase implements LatticeCalcStrategy {
    //-----------------------------------------------------
    public NextClosedSetCalculator() {
        super();
    }

    //-----------------------------------------------------
    protected boolean closureAttr(ModifiableSet set, int j, ModifiableSet notJ) {
        nextClosure.copy(allAttrSet);
        set.put(j);
        closedObjects.clearSet();
        for (int i = rel.getRowCount(); --i >= 0;) {
            Set tmp = rel.getSet(i);
            if (set.isSubsetOf(tmp)) {
                nextClosure.and(tmp);
                closedObjects.put(i);
            }
        }
        nextElementInLecticalOrder.copy(nextClosure);
        nextElementInLecticalOrder.andNot(notJ);
        set.remove(j);
        return set.equals(nextElementInLecticalOrder);
    }


    protected void onFinishCalc() {
        callback.finishCalc();
    }

    protected void onStartCalc() {
        callback.startCalc();
    }

    protected void addNextClosedElement(Set elementIntent) {
        callback.addConcept(closedObjects, nextClosure);
    }

    protected void addZeroElement(Set zeroElementIntent) {
        callback.addConcept(closedObjects, zeroElementIntent);
    }

    //-----------------------------------------------------
    public void buildLattice() {
        Assert.isTrue(callback instanceof conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback);
        calculateConceptSet();
    }
}
