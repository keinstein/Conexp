package conexp.core.calculationstrategies;

import conexp.core.*;
import conexp.core.searchconstraints.NullSearchConstraint;

/**
 *  Description of the Class
 *
 *@author     Sergey
 *@created    24 Èþëü 2000 ã.
 */
public class DepthSearchCalculatorWithFeatureMask extends DepthSearchCalculator implements
        ConceptLatticeCalcStrategyWithFeatureMask, ConceptSpaceSearchEngine {

    public DepthSearchCalculatorWithFeatureMask() {
        super();
        removeAllSearchConstraints();
    }

    public void setFeatureMask(Set featureMask) {
        this.featureMask = featureMask;
    }

    Set featureMask;

    protected ModifiableSet calcDescAttr(int depth, Set objects, Set attribs) {
        ModifiableSet ret = super.calcDescAttr(depth, objects, attribs);
        ret.and(allAttrSet);
        return ret;
    }

    protected void initObjectsAndAttribs() {
        super.initObjectsAndAttribs();
        if (hasFeatureMask()) {
            allAttrSet.and(featureMask);
        }
    }

    public void buildLattice() {
        if (hasFeatureMask()) {
            lattice.setFeatureMask(featureMask);
        }
        super.buildLattice();
    }

    private boolean hasFeatureMask() {
        return null != featureMask;
    }

    public void removeAllSearchConstraints(){
        setSearchConstrainter(new NullSearchConstraint());
    }

    SearchConstraint searchConstrainer;

    public void setSearchConstrainter(SearchConstraint searchConstrainer) {
        this.searchConstrainer = searchConstrainer;
    }

    boolean hasZero = false;

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
        startCalc();
        initObjectsAndAttribs();
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

        for (int j = workSet.firstIn(); j!=Set.NOT_IN_SET; j = workSet.nextIn(j)) {
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
                    }else{
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
            if(hasZero){
                parentElement.addFirstTimePred(lattice.getZero());
            }
            return;
        }
        ModifiableSet conceptDescendantAttributesCopy = descEntitiesCopy[depth];
        conceptDescendantAttributesCopy.copy(conceptDescendantAttributes);

        ModifiableSet prohibitedSet = prohibitedSets[depth];
        for (int j = conceptDescendantAttributesCopy.firstIn(); j!=Set.NOT_IN_SET; j = conceptDescendantAttributesCopy.nextIn(j)) {
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
                                if(hasZero){
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