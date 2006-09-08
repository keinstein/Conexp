/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.ConceptIterator;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;


public class ReferenceDepthSearchCalculator extends BasicDepthSearchCalculator {
    //-----------------------------------------------------------------
    public ReferenceDepthSearchCalculator() {
        super();
    }
    //--------------------------------------------------------------------

    /**
     * calculate attributes, that exist in at least one object from _objects and
     * don't belong to _attribs
     *
     * @param depth   Description of Parameter
     * @param objects Description of Parameter
     * @param attribs Description of Parameter
     * @return Description of the Returned Value
     */
    private Set calcDescAttr(int depth, Set objects, Set attribs) {
        ///*DBG*/ System.out.println("calcDescAttr "+depth+" attribs["+attribs+"]");
        ModifiableSet ret = descEntities[depth];
        ret.clearSet();
        for (int j = objects.length(); --j >= 0;) {
            if (objects.in(j)) {
                ret.or(rel.getSet(j));
            }
        }
        ret.andNot(attribs);
        return ret;
    }
    //-------------------------------------------------------------------------------

    /**
     * Description of the Method
     *
     * @param _latEl Description of Parameter
     * @param depth  Description of Parameter
     */
    private void calcDescendantsAttr(LatticeElement _latEl, int depth) {
        Set _conceptDescAttr = calcDescAttr(depth, _latEl.getObjects(), _latEl.getAttribs());
        if (_conceptDescAttr.isEmpty()) {
            lattice.getZero().addSucc(_latEl);
            return;
        }
        ModifiableSet _conceptDescAttrCopy = descEntitiesCopy[depth];
        _conceptDescAttrCopy.copy(_conceptDescAttr);

        ModifiableSet _prohibitedSet = prohibitedSets[depth];
        for (int j = _conceptDescAttrCopy.length(); --j >= 0;) {
            if (_conceptDescAttrCopy.in(j)) {
                if (isDirectDescendentForAttr(j, _conceptDescAttr, _latEl.getObjects())) {
                    _conceptDescAttrCopy.andNot(newIntent);
                    _conceptDescAttrCopy.and(outerSet);
                    newIntent.or(_latEl.getAttribs());
                    if (newIntent.intersects(_prohibitedSet)) {
                        setConnectionFromOne(_latEl, newIntent);
                    } else {
                        // this comparison should work only one time
                        // it will be really very good to remove it
                        if (newIntent.equals(allAttrSet)) {
                            lattice.getZero().addSucc(_latEl);
                        } else {
                            LatticeElement _latNew = makeLatticeElement(newExtent, newIntent);
                            lattice.addElement(_latNew);
                            _latEl.addPred(_latNew);
                            prohibitedSets[depth + 1].copy(_prohibitedSet);
                            calcDescendantsAttr(_latNew, depth + 1);
                            _prohibitedSet.put(j);
                        }
                    }
                }
                //if(isDirectDescendentAttr(j,conceptDescAttr,_latEl.objects)){
            }
            // if(_conceptDescAttrCopy.in(j)){
        }
        //for(int j=_conceptDescAttrCopy.length();--j>=0; ){
    }


    //-----------------------------------------------------------------
    protected void performDepthSearchCalculationOfLattice() {
        calcOne();
        LatticeElement latEl = makeLatticeElement(newExtent, newIntent);
        lattice.addElement(latEl);
        lattice.setOne(latEl);
        calcZero();
        if (latEl.getObjects().equals(newExtent)) {
            lattice.setZero(latEl);
        } else {
            latEl = makeLatticeElement(newExtent, outerSet);
            lattice.addElement(latEl);
            lattice.setZero(latEl);
            calcDescendantsAttr(lattice.getOne(), 0);
        }
    }

    //-----------------------------------------------------------------

    /**
     * method finds a closure by addition of attr j to intent, having extent
     * _concObjects
     *
     * @param j            - attribute to add
     * @param _concObjects - list of objects, in which closure is performed
     * @output newExtent - new conexp extent outerSet - outer
     * set newIntent - new conexp intent
     */
    protected void findAttrClosure(int j, Set _concObjects) {
        newExtent.clearSet();
        outerSet.clearSet();
        newIntent.copy(allAttrSet);
        for (int i = _concObjects.length(); --i >= 0;) {
            if (_concObjects.in(i)) {
                Set tmp = rel.getSet(i);
                if (tmp.in(j)) {
                    newExtent.put(i);
                    newIntent.and(tmp);
                } else {
                    outerSet.or(tmp);
                }
            }
        }
    }
    //----------------------------------------------------------------

    /**
     * this function is strongly connected with used order !!! precodndition :
     * _attribs not equal one attribs
     *
     * @param attribs Description of Parameter
     * @return Description of the Returned Value
     */
    protected LatticeElement findLatticeElementFromOne(Set attribs) {
        LatticeElement curr = lattice.getOne();
        Assert.isTrue(null != curr, "One in findElement can't be null");
        while (true) {
            ConceptIterator iter = curr.getPredecessors().iterator();
            outer :
            {
                while (iter.hasNext()) {
                    LatticeElement pred = iter.nextConcept();
                    //*DBG*/System.out.println("pred="+(BitSet)_pred.attribs);
                    //*DBG*/System.out.println("compare="+_attribs+" "+_pred.attribs+"["+_attribs.compare(_pred.attribs)+"]");
                    switch (attribs.compare(pred.getAttribs())) {
                        case Set.SUPERSET:
                            curr = pred;
                            break outer;
                        case Set.EQUAL:
                            //found = true;
                            return pred;
                        case Set.SUBSET:
                            Assert.isTrue(false, " findLatticeElement attribs [" + attribs + "] pred[" + pred.getAttribs() + ']');
                        default:
                            // continue cycle
                            break;
                    }
                    //switch(_objects.compare(_succ.objects))
                }
                //while(iter.hasMoreElements())
            }
            //outer
        }
    }
}
