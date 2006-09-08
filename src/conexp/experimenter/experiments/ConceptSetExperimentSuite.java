package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;

public class ConceptSetExperimentSuite extends ExperimentSuite {
    public static void conceptSetExperiment() {
        doRunExperimentSet(createExperimentSet());
    }


    public static ExperimentSet createExperimentSet() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.NextClosedSetCalculator"));
        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        set.addExperiment(new LatticeBuildExperiment("conexp.core.calculationstrategies.NextClosedSetCalculator"));
        set.addExperiment(new LatticeBuildExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        return set;
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:59:10)
     *
     * @param args java.lang.String[]
     */
    public static void main(String[] args) {
        conceptSetExperiment();
    }


}