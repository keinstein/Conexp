/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.ConceptSetNeedingCalcStrategy;
import conexp.core.ConceptsCollection;


public class ConceptSetNeedingConceptSetExperiment extends ConceptSetExperiment {

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 15:37:35)
     *
     * @param strategyName java.lang.String
     */
    public ConceptSetNeedingConceptSetExperiment(String strategyName) {
        super(strategyName);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 15:33:31)
     */
    protected void doLocalSetup() {
        super.doLocalSetup();
        ((ConceptSetNeedingCalcStrategy) strategy).setConceptSet((ConceptsCollection) coll);
    }

}
