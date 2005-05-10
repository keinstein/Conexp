package conexp.experimenter.experiments;

import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.ContextLoadingRelationGenerationStrategy;
import util.DataFormatException;

import java.io.IOException;

/**
 * User: sergey
 * Date: 10/5/2005
 * Time: 8:00:22
 */
public class ImplicationExperimentSuite extends ExperimentSuite  {
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
