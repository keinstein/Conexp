package conexp.experimenter.experiments;

import conexp.core.BinaryRelationProcessor;
import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.experimenter.experiments.BaseConceptSetExperiment;
import util.StringUtil;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 13:46:49)
 * @author
 */
public abstract class ConceptSetExperiment extends BaseConceptSetExperiment {
    protected final java.lang.String strategyName;

    protected ConceptSetExperiment(String strategyName) {
        this.strategyName = strategyName;
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 16:31:10)
     */
    protected Object makeConceptsCollection() {
        return new ConceptsCollection();
    }


    public String getDescription() {
        return StringUtil.extractClassName(strategyName);
    }

    public void perform() {
        ((ConceptCalcStrategy)strategy).calculateConceptSet();
    }

    protected int getConceptsCount() {
        return ((ConceptsCollection) coll).conceptsCount();
    }

    public BinaryRelationProcessor makeStrategy() {
        return (ConceptCalcStrategy) createClassByName(strategyName);
    }

}