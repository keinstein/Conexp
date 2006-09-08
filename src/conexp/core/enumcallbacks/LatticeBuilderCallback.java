/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.Set;

public class LatticeBuilderCallback extends DefaultConceptEnumCallback {

    private Lattice lat;
    private LatticeElement last;

    public LatticeBuilderCallback(Lattice _lat) {
        super();
        lat = _lat;
    }

    private void addToLatElem(Set obj, Set attr) {
        last = LatticeElement.makeFromSetsCopies(obj, attr);
        lat.addElementSetLinks(last);
    }

    /**
     *
     */
    public void finishCalc() {
        if (null != last) {
            lat.setOne(last);
        }
        //*DBG*/lat.printDebugData();
    }

    /**
     * @param extent <description>
     * @param intent <description>
     */
    public void setZeroElement(Set extent, Set intent) {
        last = LatticeElement.makeFromSetsCopies(extent, intent);
        //*DBG*/ System.out.println(last);
        //*DBG*/ System.out.println(lat);
        lat.addElement(last);
        lat.setZero(last);
    }

    /**
     * @param extent <description>
     * @param intent <description>
     */
    public void addConcept(Set extent, Set intent) {
        addToLatElem(extent, intent);
    }
}
