/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 7:58:16 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework;

import util.Assert;

public class ExperimentRunResults {
    MeasurementSet relationParams;
    MeasurementSet[] resultsOnExperimentSet;

    public ExperimentRunResults(MeasurementSet relationDescription, int experimentsInSet) {
        relationParams = relationDescription;
        resultsOnExperimentSet = new MeasurementSet[experimentsInSet];
    }

    public void storeResultsForExperiment(int expNo, MeasurementSet results) {
        Assert.isTrue(null == resultsOnExperimentSet[expNo]);
        resultsOnExperimentSet[expNo] = results;
    }

    public MeasurementSet getResultForExperiment(int expNo) {
        return resultsOnExperimentSet[expNo];
    }
}
