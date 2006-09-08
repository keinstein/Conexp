/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.calculationstrategies;

import conexp.core.BinaryRelation;
import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptEnumCallback;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import conexp.core.Set;



public class DepthSearchConceptCalcStrategy extends DepthSearchBinaryRelationAlgorithm implements ConceptCalcStrategy {
    protected ConceptEnumCallback callback;
    protected ModifiableSet[] descEntities;
    protected ModifiableSet[] descEntitiesCopy;
    protected ModifiableSet[] prohibitedSets;

    public void setCallback(ConceptEnumCallback _callback) {
        this.callback = _callback;
        this.callback.setRelation(rel);
    }

    public void setRelation(BinaryRelation rel) {
        super.setRelation(rel);
        //SMELL to refactor
        if (null != callback) {
            callback.setRelation(rel);
        }
    }

    /**
     * calculates One element of conexp lattice object set is in newExtent
     * attributes set is in outerSet
     */
    protected void calcOne() {
        int numObj = rel.getRowCount();
        newIntent.copy(allAttrSet);
        newExtent.fill();
        for (int i = numObj; --i >= 0;) {
            newIntent.and(rel.getSet(i));
        }
    }

    public void tearDown() {
        super.tearDown();
        callback = null;

        descEntities = null;
        descEntitiesCopy = null;
        prohibitedSets = null;
    }

    //-----------------------------------------------------------------
    private void startCalc() {
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

    /**
     * method finds a closure by addition of attr j to intent, having extent
     * _concObjects
     *
     * @param j            - attribute to add
     * @param _concObjects - list of objects, in which closure is performed
     *                     side_effect            newExtent - new conexp extent
     *                     outerSet - outer set
     *                     newIntent - new conexp intent
     */
    protected void findAttrClosure(int j, Set _concObjects) {
        newExtent.clearSet();
        outerSet.clearSet();
        newIntent.copy(allAttrSet);
        for (int i = _concObjects.firstIn(); i >= 0; i = _concObjects.nextIn(i)) {
            Set tmp = rel.getSet(i);
            if (tmp.in(j)) {
                newExtent.put(i);
                newIntent.and(tmp);
            } else {
                outerSet.or(tmp);
            }
        }
    }

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

    /**
     * this is an optimized version of conexp enumeration algorithm
     */
    protected void depthSearchEnumerateConcepts() {
        //        Assert.isTrue(null!=lat,"Lattice should be set before calculation");
        initStackObjects();
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


    public void calculateConceptSet() {
        depthSearchEnumerateConcepts();
    }

    protected void initStackObjects() {
        initObjectsAndAttribs();
        startCalc();
    }
}
