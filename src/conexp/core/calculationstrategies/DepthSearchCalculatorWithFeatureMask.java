/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.ConceptLatticeCalcStrategyWithFeatureMask;
import conexp.core.ConceptSpaceSearchEngine;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import conexp.core.SearchConstraint;
import conexp.core.Set;
import conexp.core.searchconstraints.NullSearchConstraint;


public class DepthSearchCalculatorWithFeatureMask extends DepthSearchCalculator implements
        ConceptLatticeCalcStrategyWithFeatureMask, ConceptSpaceSearchEngine {

    public DepthSearchCalculatorWithFeatureMask() {
        super();
        removeAllSearchConstraints();
    }

    public void setFeatureMasks(Set attributesMask, Set objectsMask) {
        this.attributesMask = attributesMask;
        this.objectsMask = objectsMask;
    }


    private Set attributesMask;
    private Set objectsMask;

    protected ModifiableSet calcDescAttr(int depth, Set objects, Set attribs) {
        ModifiableSet ret = super.calcDescAttr(depth, objects, attribs);
        ret.and(allAttrSet);
        return ret;
    }

    private ModifiableSet allObjSet;

    protected void initObjectsAndAttribs() {
        super.initObjectsAndAttribs();
        allObjSet = ContextFactoryRegistry.createSet(getRelation().getRowCount());
        allObjSet.fill();
        if (hasFeatureMask()) {
            allAttrSet.and(attributesMask);
        }

        if (objectsMask != null) {
            allObjSet.and(objectsMask);
        }
    }


    protected void calcOne() {
        newIntent.copy(allAttrSet);
        newExtent.fill();
        newExtent.and(allObjSet);
        for (int objId = allObjSet.firstIn(); objId != Set.NOT_IN_SET; objId = allObjSet.nextIn(objId)) {
            newIntent.and(rel.getSet(objId));
        }
    }

    public void tearDown() {
        super.tearDown();
        allObjSet = null;
    }

    public void buildLattice() {
        if (hasFeatureMask()) {
            lattice.setFeatureMasks(attributesMask, objectsMask);
        }
        super.buildLattice();
    }

    private boolean hasFeatureMask() {
        return null != attributesMask || null != objectsMask;
    }

    public void removeAllSearchConstraints() {
        setSearchConstrainter(new NullSearchConstraint());
    }

    private SearchConstraint searchConstrainer;

    public void setSearchConstrainter(SearchConstraint searchConstrainer) {
        this.searchConstrainer = searchConstrainer;
    }

    private boolean hasZero = false;

    protected void performDepthSearchCalculationOfLattice() {
        calcOne();
        if (!searchConstrainer.continueSearch(newIntent, newExtent.elementCount())) {
            return;
        }
        DepthSearchLatticeElement one = makeDepthSearchLatticeElement(newExtent, newIntent);
        lattice.addElement(one);
        lattice.setOne(one);

        hasZero = false;
        calcZero();
        if (one.getObjects().isEquals(newExtent)) {
            lattice.setZero(one);
        } else {

            if (searchConstrainer.continueSearch(newIntent, newExtent.elementCount())) {
                DepthSearchLatticeElement zero = makeDepthSearchLatticeElement(newExtent, outerSet);
                lattice.addElement(zero);
                lattice.setZero(zero);
                hasZero = true;
            }
            calcDescendantsAttr(one, 0);
        }

    }

    protected void depthSearchEnumerateConcepts() {
        //todo: write version, that works with feature mask and search constrainer
        initStackObjects();
        callback.startCalc();
        calcOne();
        if (!searchConstrainer.continueSearch(newIntent, newExtent.elementCount())) {
            return;
        }
        callback.addConcept(newExtent, newIntent);
        if (!newIntent.equals(allAttrSet)) {
            prohibitedSets[0].copy(newIntent);
            descEntities[0].copy(allAttrSet);
            descEntities[0].andNot(prohibitedSets[0]);

            depthSearchEnumConcepts(newIntent, newExtent, 0);
        } // end of if ()

    }

    protected void depthSearchEnumConcepts(Set intent, Set extent, int depth) {
        ModifiableSet prohibitedSet = prohibitedSets[depth];
        ModifiableSet workSet = descEntities[depth];
        ModifiableSet currExtent = currObjects[depth];
        currExtent.copy(extent);
        ModifiableSet currIntent = currAttribs[depth];
        currIntent.copy(intent);

        for (int j = workSet.firstIn(); j != Set.NOT_IN_SET; j = workSet.nextIn(j)) {
            findAttrClosure(j, currExtent);
            workSet.and(outerSet);
            newIntent.andNot(currIntent);
            if (!newIntent.intersects(prohibitedSet)) {
                newIntent.or(currIntent);
                if (searchConstrainer.continueSearch(newIntent, newExtent.elementCount())) {
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
                } else {
                    tempAttrSet.copy(allAttrSet);
                    tempAttrSet.andNot(outerSet);

                    prohibitedSet.or(tempAttrSet);
                }
            }
        }  //for(int j=workSet.length(); --j>=0;){
        //*DBG*/ System.out.println("return from depth["+depth+"]==============================================");
    }


    void calcDescendantsAttr(DepthSearchLatticeElement parentElement, int depth) {
        Set conceptDescendantAttributes = calcDescAttr(depth, parentElement.getObjects(), parentElement.getAttribs());
        if (conceptDescendantAttributes.isEmpty()) {
            if (hasZero) {
                parentElement.addFirstTimePred(lattice.getZero());
            }
            return;
        }
        ModifiableSet conceptDescendantAttributesCopy = descEntitiesCopy[depth];
        conceptDescendantAttributesCopy.copy(conceptDescendantAttributes);

        ModifiableSet prohibitedSet = prohibitedSets[depth];
        for (int j = conceptDescendantAttributesCopy.firstIn(); j != Set.NOT_IN_SET; j = conceptDescendantAttributesCopy.nextIn(j))
        {
            if (isDirectDescendentForAttr(j, conceptDescendantAttributes, parentElement.getObjects())) {
                conceptDescendantAttributesCopy.andNot(newIntent);
                conceptDescendantAttributesCopy.and(outerSet);
                newIntent.or(parentElement.getAttribs());
                if (searchConstrainer.continueSearch(newIntent, newExtent.elementCount())) {
                    if (newIntent.intersects(prohibitedSet)) {
                        setConnectionFromOne(parentElement, newIntent);
                    } else {
                        // this comparison should work only one time
                        // it will be really very good to remove it
                        if (newIntent.isEquals(allAttrSet)) {
                            if (hasZero) {
                                parentElement.addFirstTimePred(lattice.getZero());
                            }
                        } else {
                            DepthSearchLatticeElement childLatticeElement = makeDepthSearchLatticeElement(newExtent, newIntent);
                            lattice.addElement(childLatticeElement);
                            parentElement.addFirstTimePred(childLatticeElement);
                            prohibitedSets[depth + 1].copy(prohibitedSet);
                            calcDescendantsAttr(childLatticeElement, depth + 1);
                        }
                    }
                }
                prohibitedSet.put(j);

            }
            //if(isDirectDescendentAttr(j,conceptDescAttr,parentElement.objects)){
        }
        //for(int j=conceptDescendantAttributesCopy.length();--j>=0; ){
    }

}
