/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.ConceptsCollection;


public abstract class ConceptSetExperiment extends BaseConceptCalcExperiment {

    protected ConceptSetExperiment(String strategyName) {
        super(strategyName);
    }


    protected void doLocalSetup() {
        coll = makeConceptsCollection();
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 16:31:10)
     */
    protected Object makeConceptsCollection() {
        return new ConceptsCollection();
    }


    protected int getConceptsCount() {
        return ((ConceptsCollection) coll).conceptsCount();
    }

}
