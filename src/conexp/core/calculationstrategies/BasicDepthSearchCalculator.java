/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies;

import conexp.core.ContextFactoryRegistry;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;

public abstract class BasicDepthSearchCalculator extends LatticeNeedingCalcStrategy {
    protected ModifiableSet[] descEntities;
    protected ModifiableSet[] descEntitiesCopy;
    protected ModifiableSet[] prohibitedSets;

    //-----------------------------------------------------------------
    void startCalc() {
        int cols = rel.getColCount();
        int rows = rel.getRowCount();
        int maxDepth = Math.min(cols, rows) + 1;

        descEntities = new ModifiableSet[maxDepth];
        descEntitiesCopy = new ModifiableSet[maxDepth];
        prohibitedSets = new ModifiableSet[maxDepth];

        int numEntries = cols;

        for (int i = maxDepth; --i >= 0;) {
            descEntities[i] = ContextFactoryRegistry.createSet(numEntries);
            descEntitiesCopy[i] = ContextFactoryRegistry.createSet(numEntries);
            prohibitedSets[i] = ContextFactoryRegistry.createSet(numEntries);
            //                outerAttribs[i]=asp.createSet(rows);
        }

    }


    public void tearDown() {
        super.tearDown();

        descEntities = null;
        descEntitiesCopy = null;
        prohibitedSets = null;
    }

    //----------------------------------------------------------------------------
    protected abstract LatticeElement findLatticeElementFromOne(Set attribs);

    protected void setConnectionFromOne(LatticeElement _child, Set _childAttrs) {
        LatticeElement _latEl = findLatticeElementFromOne(_childAttrs);
        Assert.isTrue(null != _latEl, "Lattice Element should be found");
        //*DBG*/System.out.println("_curr=" + _latEl.attribs);
        Assert.isTrue(_childAttrs.equals(_latEl.getAttribs()), "Lattice Element should be equal");
        _child.addPred(_latEl);
    }


    protected abstract void findAttrClosure(int j, Set _concObjects);
    //-----------------------------------------------------------------

    protected boolean isDirectDescendentForAttr(int j, Set _concDescAttr, Set _concObjects) {
        findAttrClosure(j, _concObjects);
        outerSet.and(_concDescAttr);
        newIntent.and(_concDescAttr);
        return !newIntent.intersects(outerSet);
    }

    //-----------------------------------------------------------------
    /**
     *  Description of the Method
     */
    public void buildLattice() {
        Assert.isTrue(null != lattice, "Lattice should be set before calculation");
        initStackObjects();
        performDepthSearchCalculationOfLattice();
    }

    protected void initStackObjects() {
        initObjectsAndAttribs();
        startCalc();
    }

    abstract protected void performDepthSearchCalculationOfLattice();

    //-----------------------------------------------------------------

    protected void depthSearchEnumConcepts(Set intent, Set extent, int depth) {
        ModifiableSet prohibitedSet = prohibitedSets[depth];
        ModifiableSet workSet = descEntities[depth];
        ModifiableSet currExtent = currObjects[depth];
        currExtent.copy(extent);
        ModifiableSet currIntent = currAttribs[depth];
        currIntent.copy(intent);

        for (int j = workSet.length(); --j >= 0;) {
            if (workSet.in(j)) {
                findAttrClosure(j, currExtent);
                workSet.and(outerSet);
                newIntent.andNot(currIntent);
                if (!newIntent.intersects(prohibitedSet)) {
                    newIntent.or(currIntent);
                    callback.addConcept(newExtent, newIntent);

                    ModifiableSet nextProhibitedSet = prohibitedSets[depth + 1];
                    nextProhibitedSet.copy(prohibitedSet);
                    nextProhibitedSet.or(newIntent);

                    tempAttrSet.copy(allAttrSet);
                    tempAttrSet.andNot(outerSet);

                    prohibitedSet.or(tempAttrSet);

                    if (!newIntent.equals(allAttrSet)) {
                        descEntities[depth + 1].copy(allAttrSet);
                        descEntities[depth + 1].andNot(nextProhibitedSet);
                        depthSearchEnumConcepts(newIntent, newExtent, depth + 1);
                    } // end of if ()
                }
            } //if(workSet.in(j)){
        }  //for(int j=workSet.length(); --j>=0;){
        //*DBG*/ System.out.println("return from depth["+depth+"]==============================================");
    }
    //-----------------------------------------------------------------
    /**
     *  this is an optimized version of conexp enumeration algorithm
     */
    protected void depthSearchEnumerateConcepts() {
        //        Assert.isTrue(null!=lat,"Lattice should be set before calculation");
        startCalc();
        initObjectsAndAttribs();
        callback.startCalc();
        calcOne();
        callback.addConcept(newExtent, newIntent);
        if (!newIntent.equals(allAttrSet)) {
            prohibitedSets[0].copy(newIntent);
            descEntities[0].copy(allAttrSet);
            descEntities[0].andNot(prohibitedSets[0]);

            depthSearchEnumConcepts(newIntent, newExtent, 0);
        } // end of if ()
    }
    //-----------------------------------------------------------------
    /**
     *  Description of the Method
     */
    public void calculateConceptSet() {
        depthSearchEnumerateConcepts();
    }
}
