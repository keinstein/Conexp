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


public class DepthSearchCalculator extends BasicDepthSearchCalculator {
    static class DepthSearchLatticeElement extends LatticeElement {
        ModifiableSet firstTimePredecessors;

        DepthSearchLatticeElement(Set extent, Set intent) {
            super(extent, intent);
            firstTimePredecessors = ContextFactoryRegistry.createSet(intent.size());
        }

        public void addFirstTimePred(LatticeElement predecessor) {
            firstTimePredecessors.put(getPredCount());
            addPred(predecessor);
        }
    };

    //-----------------------------------------------------------------
    /**
     *  Constructor for the DepthSearchCalculator object
     */
    public DepthSearchCalculator() {
        super();
    }
    //--------------------------------------------------------------------
    /**
     *  calculate attributes, that exist in at least one object from _objects and
     *  don't belong to _attribs
     *
     *@param  depth    Description of Parameter
     *@param  objects  Description of Parameter
     *@param  attribs  Description of Parameter
     *@return           Description of the Returned Value
     */
    protected ModifiableSet calcDescAttr(int depth, Set objects, Set attribs) {
        ///*DBG*/ System.out.println("calcDescAttr "+depth+" attribs["+attribs+"]");
        ModifiableSet ret = descEntities[depth];
        ret.clearSet();
        for (int j = objects.firstIn(); j >= 0; j = objects.nextIn(j)) {
            ret.or(rel.getSet(j));
        }
        ret.andNot(attribs);
        return ret;
    }
    //-----------------------------------------------------------------
    /**
     *  from here starts version for attributes
     */

    protected void performDepthSearchCalculationOfLattice() {
        calcOne();
        DepthSearchLatticeElement one = makeDepthSearchLatticeElement(newExtent, newIntent);
        lattice.addElement(one);
        lattice.setOne(one);
        calcZero();
        if (one.getObjects().isEquals(newExtent)) {
            lattice.setZero(one);
        } else {
            DepthSearchLatticeElement zero = makeDepthSearchLatticeElement(newExtent, outerSet);
            lattice.addElement(zero);
            lattice.setZero(zero);
            calcDescendantsAttr(one, 0);
        }
    }

    //-----------------------------------------------------------------
    /**
     *  method finds a closure by addition of attr j to intent, having extent
     *  _concObjects
     *
     *@param  j             - attribute to add
     *@param  _concObjects  - list of objects, in which closure is performed
     * side_effect            newExtent - new conexp extent
     *                        outerSet - outer set
     *                        newIntent - new conexp intent
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
    //----------------------------------------------------------------
    /**
     *  this function is strongly connected with used order !!! precondition :
     *  _attribs not equal one attribs
     */
    protected LatticeElement findLatticeElementFromOne(Set attribs) {
        DepthSearchLatticeElement curr = (DepthSearchLatticeElement) lattice.getOne();
        for (; ;) {
            Set firstTime = curr.firstTimePredecessors;
            int i = firstTime.firstIn();
            outer : {
                while (i >= 0) {
                    DepthSearchLatticeElement pred = (DepthSearchLatticeElement) curr.getPred(i);
                    if (attribs.isSupersetOf(pred.getAttribs())) {
                        curr = pred;
                        if (curr.getAttribs().isEquals(attribs)) {
                            return curr;
                        }
                        break outer;
                    }
                    i = firstTime.nextIn(i);
                }
            }//outer
        }
    }

    //-------------------------------------------------------------------------------
    /**
     *  Description of the Method
     *
     *@param  parentElement              Description of Parameter
     *@param  depth              Description of Parameter
     */
    void calcDescendantsAttr(DepthSearchLatticeElement parentElement, int depth) {
        Set _conceptDescAttr = calcDescAttr(depth, parentElement.getObjects(), parentElement.getAttribs());
        if (_conceptDescAttr.isEmpty()) {
            parentElement.addFirstTimePred(lattice.getZero());
            return;
        }
        ModifiableSet _conceptDescAttrCopy = descEntitiesCopy[depth];
        _conceptDescAttrCopy.copy(_conceptDescAttr);

        ModifiableSet _prohibitedSet = prohibitedSets[depth];
        for (int j = _conceptDescAttrCopy.length(); --j >= 0;) {
            if (_conceptDescAttrCopy.in(j)) {
                if (isDirectDescendentForAttr(j, _conceptDescAttr, parentElement.getObjects())) {
                    _conceptDescAttrCopy.andNot(newIntent);
                    _conceptDescAttrCopy.and(outerSet);
                    newIntent.or(parentElement.getAttribs());
                    if (newIntent.intersects(_prohibitedSet)) {
                        setConnectionFromOne(parentElement, newIntent);
                    } else {
                        // this comparison should work only one time
                        // it will be really very good to remove it
                        if (newIntent.isEquals(allAttrSet)) {
                            parentElement.addFirstTimePred(lattice.getZero());
                        } else {
                            DepthSearchLatticeElement _latNew = makeDepthSearchLatticeElement(newExtent, newIntent);
                            lattice.addElement(_latNew);
                            parentElement.addFirstTimePred(_latNew);
                            prohibitedSets[depth + 1].copy(_prohibitedSet);
                            calcDescendantsAttr(_latNew, depth + 1);
                            _prohibitedSet.put(j);
                        }
                    }
                }
                //if(isDirectDescendentAttr(j,conceptDescAttr,parentElement.objects)){
            }
            // if(_conceptDescAttrCopy.in(j)){
        }
        //for(int j=_conceptDescAttrCopy.length();--j>=0; ){
    }

    /**
     * Insert the method's description here.
     * Creation date: (05.08.01 19:28:46)
     * @return conexp.core.LatticeElement
     * @param extent conexp.core.Set
     * @param intent conexp.core.Set
     */
    protected DepthSearchLatticeElement makeDepthSearchLatticeElement(Set extent, Set intent) {
        return new DepthSearchLatticeElement((Set) extent.clone(), (Set) intent.clone());
    }

}
