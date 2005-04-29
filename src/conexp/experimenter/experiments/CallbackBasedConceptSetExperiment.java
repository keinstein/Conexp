package conexp.experimenter.experiments;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.enumcallbacks.ConceptSetCallback;


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
        super.doLocalSetup();
        ((ConceptCalcStrategy)strategy).setCallback(new ConceptSetCallback((ConceptsCollection) coll));
    }


}