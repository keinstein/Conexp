/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks;

import conexp.core.ConceptsCollection;
import conexp.core.Set;


public class ConceptSetCallback extends DefaultConceptEnumCallback {
    protected ConceptsCollection conceptSet;

    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 18:07:58)
     *
     * @param collection conexp.core.ConceptsCollection
     */
    public ConceptSetCallback(ConceptsCollection collection) {
        conceptSet = collection;
    }


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
        conceptSet.addElement(conceptSet.makeConceptFromSetsCopies(obj, attr));
    }


    /**
     * **************************************
     * this function is called at the beginning of
     * calculation of conexp set
     * ***************************************
     */
    public void startCalc() {
        conceptSet.clear();
    }
}
