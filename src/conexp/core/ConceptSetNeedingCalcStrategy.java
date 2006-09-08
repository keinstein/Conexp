/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public class ConceptSetNeedingCalcStrategy extends AbstractConceptCalcStrategy {
    protected ConceptsCollection conceptSet;

    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 18:35:03)
     *
     * @param col conexp.core.ConceptsCollection
     */
    public void setConceptSet(ConceptsCollection col) {
        conceptSet = col;
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:03:37)
     */
    public void tearDown() {
        super.tearDown();
        conceptSet = null;
    }
}
