/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.core.LatticeElement;
import conexp.core.Set;




public abstract class LatticeNeedingCalcStrategy extends DepthSearchConceptCalcStrategy implements LatticeCalcStrategy {
    protected Lattice lattice;

    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------

    /**
     * creates new conexp lattice element with objects obj and attributes attr
     *
     * @param obj  Description of Parameter
     * @param attr Description of Parameter
     * @return Description of the Returned Value
     */
    protected static LatticeElement makeLatticeElement(Set obj, Set attr) {
        return LatticeElement.makeFromSetsCopies(obj, attr);
    }
    //-----------------------------------------------------------------

    /**
     * Sets the Lattice attribute of the DepthSearchCalculator object
     *
     * @param _lat The new Lattice value
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
        super.tearDown();
    }

    public void calculateConceptSet() {
        buildLattice();
    }

    /**
     * calculates Zero element of conexp lattice object set is in newExtent
     * attributes set is in outerSet
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
}
