/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.ContextLoadingRelationGenerationStrategy;
import util.DataFormatException;

import java.io.IOException;


public class SpectExperimentSuite extends ExperimentSuite {
    public static void conceptSetExperiment() throws IOException, DataFormatException {
        doRunExperimentWithCountingOfSetOperations(createExperimentSet(), createRelationSequence());
    }

    private static RelationSequenceSet createRelationSequence() throws DataFormatException, IOException {
        RelationSequenceSet relationSequenceSet = new RelationSequenceSet();
        relationSequenceSet.addRelationSequence(new ContextLoadingRelationGenerationStrategy("SPECT_ALL.cxt"));
        return relationSequenceSet;
    }


    public static ExperimentSet createExperimentSet() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.NextClosedSetCalculator"));
        set.addExperiment(new CallbackBasedConceptSetExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        //set.addExperiment(new CallbackBasedConceptSetExperiment("research.conexp.core.calculationstrategies.DepthSearchCalculator2"));
        set.addExperiment(new CallbackBasedConceptSetExperiment("research.conexp.core.calculationstrategies.SimpleDepthSearchCalculator"));
        set.addExperiment(new CallbackBasedConceptSetExperiment("research.conexp.core.calculationstrategies.SimpleDepthSearchCalculator2"));
        //      set.addExperiment(new LatticeBuildExperiment("conexp.core.calculationstrategies.NextClosedSetCalculator"));
        //      set.addExperiment(new LatticeBuildExperiment("conexp.core.calculationstrategies.DepthSearchCalculator"));
        return set;
    }

    public static void main(String[] args) {
        try {
            conceptSetExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
