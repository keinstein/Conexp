package conexp.experimenter.experiments;

import conexp.core.ConceptCalcStrategy;
import conexp.experimenter.experiments.ConceptSetExperiment;


/**
 * Insert the type's description here.
 * Creation date: (21.07.01 15:33:31)
 * @author
 */
public class CallbackBasedConceptSetExperiment extends ConceptSetExperiment {


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 15:37:35)
     * @param strategyName java.lang.String
     */
    public CallbackBasedConceptSetExperiment(String strategyName) {
        super(strategyName);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 15:33:31)
     */
    protected void doLocalSetup() {
        ((ConceptCalcStrategy)strategy).setCallback(new conexp.core.enumcallbacks.ConceptSetCallback((conexp.core.ConceptsCollection) coll));
    }


}