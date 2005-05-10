package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;

/**
 * User: sergey
 * Date: 10/5/2005
 * Time: 8:00:22
 */
public class ImplicationExperimentRunner extends ConceptSetExperimentSuite {
    public static void implicationSetExperiment(){
        doRunExperimentSet(createImplicationsExperiment());
    }

    public static ExperimentSet createImplicationsExperiment() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new NextClosedSetImplicationCalculatorExperiment());
        //add here other experiments
        return set;
    }

    public static void main(String[] args) {
        implicationSetExperiment();
    }

}
