package conexp.core.calculationstrategies;

import conexp.core.*;


/**
 * Insert the type's description here.
 * Creation date: (23.02.01 22:42:43)
 * @author
 */
public abstract class LatticeNeedingCalcStrategy extends DepthSearchBinaryRelationAlgorithm implements ConceptCalcStrategy, LatticeCalcStrategy {
    protected Lattice lattice;
    protected ConceptEnumCallback callback;

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

    //-----------------------------------------------------------------------
    /**
     *  calculates One element of conexp lattice object set is in newExtent
     *  attributes set is in outerSet
     */
    protected void calcOne() {
        int numObj = rel.getRowCount();
        newIntent.copy(allAttrSet);
        newExtent.fillByOne(numObj);
        for (int i = numObj; --i >= 0;) {
            newIntent.and(rel.getSet(i));
        }
    }

    //-----------------------------------------------------------------------
    /**
     *  calculates Zero element of conexp lattice object set is in newExtent
     *  attributes set is in outerSet
     */
    protected void calcZero() {
        int numObj = rel.getRowCount();
        newExtent.clearSet();
        outerSet.copy(allAttrSet);
        for (int i = numObj; --i >= 0;) {
            if (outerSet.isSubsetOf(rel.getSet(i))) {
                newExtent.put(i);
            }
        }
    }

    //-----------------------------------------------------------------
    /**
     *  creates new conexp lattice element with objects obj and attributes attr
     *
     *@param  obj   Description of Parameter
     *@param  attr  Description of Parameter
     *@return       Description of the Returned Value
     */
    protected static LatticeElement makeLatticeElement(Set obj, Set attr) {
        return LatticeElement.makeFromSetsCopies(obj, attr);
    }
    //-----------------------------------------------------------------
    /**
     *  Sets the Lattice attribute of the DepthSearchCalculator object
     *
     *@param  _lat  The new Lattice value
     */
    public void setLattice(Lattice _lat) {
        lattice = _lat;
        lattice.clear();
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:03:37)
     */

    public void tearDown() {
        lattice = null;
        callback = null;
        super.tearDown();
    }
}