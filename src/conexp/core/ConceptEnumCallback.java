/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ConceptEnumCallback {

//------------------------------------------

    /**
     * **************************************
     * this function is called for adding new
     * conexp to conexp lattice
     *
     * @param obj  - set of objects of conexp
     * @param attr - set of attributes of conexp
     *             ***************************************
     */
    void addConcept(Set obj, Set attr);

//------------------------------------------

    /**
     * **************************************
     * this function is called at the end of
     * calculation of conexp set
     * ***************************************
     */
    void finishCalc();

//-----------------------------------------

    /**
     * **************************************
     * this function is called at the beginning of
     * calculation of conexp set
     * ***************************************
     */
    void startCalc();

    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 20:05:08)
     *
     * @param rel conexp.core.BinaryRelation
     */
    void setRelation(BinaryRelation rel);
}
