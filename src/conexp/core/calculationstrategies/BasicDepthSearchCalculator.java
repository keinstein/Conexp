/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.LatticeElement;
import conexp.core.Set;
import util.Assert;

public abstract class BasicDepthSearchCalculator extends LatticeNeedingCalcStrategy {

    protected abstract LatticeElement findLatticeElementFromOne(Set attribs);

    protected void setConnectionFromOne(LatticeElement _child, Set _childAttrs) {
        LatticeElement _latEl = findLatticeElementFromOne(_childAttrs);
        Assert.isTrue(null != _latEl, "Lattice Element should be found");
        //*DBG*/System.out.println("_curr=" + _latEl.attribs);
        Assert.isTrue(_childAttrs.equals(_latEl.getAttribs()), "Lattice Element should be equal");
        _child.addPred(_latEl);
    }

    protected boolean isDirectDescendentForAttr(int j, Set _concDescAttr, Set _concObjects) {
        findAttrClosure(j, _concObjects);
        outerSet.and(_concDescAttr);
        newIntent.and(_concDescAttr);
        return !newIntent.intersects(outerSet);
    }

    public void buildLattice() {
        Assert.isTrue(null != lattice, "Lattice should be set before calculation");
        initStackObjects();
        performDepthSearchCalculationOfLattice();
    }

    public void calculateConceptSet() {
        depthSearchEnumerateConcepts();
    }

    abstract protected void performDepthSearchCalculationOfLattice();

}
