/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks;

import conexp.core.BinaryRelation;
import conexp.core.ConceptEnumCallback;
import conexp.core.Set;


public class DefaultConceptEnumCallback implements ConceptEnumCallback {

    /**
     * **************************************
     * this function is called for adding new
     * conexp to conexp lattice
     *
     * @param obj  - set of objects of conexp
     * @param attr - set of attributes of conexp
     *             ***************************************
     */
    public void addConcept(Set obj, Set attr) {
    }


    /**
     * **************************************
     * this function is called at the end of
     * calculation of conexp set
     * ***************************************
     */
    public void finishCalc() {
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 20:05:23)
     *
     * @param rel conexp.core.BinaryRelation
     */
    public void setRelation(BinaryRelation rel) {
    }


    /**
     * **************************************
     * this function is called at the beginning of
     * calculation of conexp set
     * ***************************************
     */
    public void startCalc() {
    }
}
