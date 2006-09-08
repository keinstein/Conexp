package conexp.experimenter.experiments;

import conexp.core.ConceptsCollection;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 13:46:49)
 *
 * @author
 */
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