/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequenceSet;
import util.DataFormatException;

import java.io.IOException;


public class ImplicationExperimentSuite extends ExperimentSuite {
    public static void implicationSetExperiment() throws DataFormatException, IOException {
        ExperimentSet set = createImplicationsExperiment();
        doRunTimeMeasurementExperiment(set, createRelationSequence());
    }

    public static void implicationSetExperimentWithOperationCount() throws DataFormatException, IOException {
        ExperimentSet set = createImplicationsExperiment();
        doRunExperimentWithCountingOfSetOperations(set, createRelationSequence());
    }

    public static RelationSequenceSet createRelationSequence() throws DataFormatException, IOException {
        RelationSequenceSet relSet = new RelationSequenceSet();
        ExperimentSuite.addExponentialSequence(relSet);
        ExperimentSuite.addTypicalSequenceAndTransposed(relSet);
        /*    if you want to add relations from files,
              uncomment the following line and provide full path to the context file
        */
        //relSet.addRelationSequence(new ContextLoadingRelationGenerationStrategy("SPECT_ALL.cxt"));
        return relSet;
    }

    public static ExperimentSet createImplicationsExperiment() {
        ExperimentSet set = new ExperimentSet();
        set.addExperiment(new NextClosedSetImplicationCalculatorExperiment());
        //add here other experiments
        return set;
    }

    public static void main(String[] args) {
        //just comment out the unneccesary one
        try {
            implicationSetExperiment();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //implicationSetExperimentWithOperationCount();
    }

}
