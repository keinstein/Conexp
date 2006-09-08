/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.ContextFactoryRegistry;
import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;
import util.Assert;


public class NextClosedSetCalculator extends NextClosedSetClosureSystemGeneratorBase implements LatticeCalcStrategy {
    //-----------------------------------------------------
    public NextClosedSetCalculator() {
        super();
    }

    private ModifiableSet closedObjects;


    public void setLattice(Lattice lattice) {
        setCallback(new NextClosedSetLatticeBuilderCallback(lattice));
    }

    protected int getAttributeCount() {
        return rel.getColCount();
    }

    //-----------------------------------------------------
    protected void startCalc() {
        super.startCalc();
        closedObjects = ContextFactoryRegistry.createSet(getObjectCount());
    }

    private int getObjectCount() {
        return rel.getRowCount();
    }

    public void tearDown() {
        super.tearDown();
        closedObjects = null;
    }


    //-----------------------------------------------------
    protected void zeroClosureAttr() {
        int numObj = getObjectCount();
        attrSet.copy(allAttrSet);
        for (int j = numObj; --j >= 0;) {
            attrSet.and(rel.getSet(j));
        }
        closedObjects.fill();
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
        Assert.isTrue(callback instanceof NextClosedSetLatticeBuilderCallback);
        calculateConceptSet();
    }
}
