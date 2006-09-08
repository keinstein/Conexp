/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

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
