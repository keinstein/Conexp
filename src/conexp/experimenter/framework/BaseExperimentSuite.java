package conexp.experimenter.framework;

import conexp.experimenter.experiments.ExperimentSuite;
import conexp.experimenter.relationsequences.PercentFilledRelationGenerationStrategy;

import java.io.PrintWriter;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 18/8/2003
 * Time: 16:43:38
 */

public class BaseExperimentSuite {
    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 9:25:41)
     */
    protected static void doRunExperimentSet(ExperimentSet set) {
        ExperimentRunner benchmark = BaseExperimentSuite.setUpExperimentRunner(set, new PrintWriter(System.out, true), new PrintWriter(System.err, true));

        BaseExperimentSuite.initBenchmark(benchmark);

        RelationSequenceSet relSet = ExperimentSuite.makeRelationSequenceSet();
        for (int i = 0; i < relSet.getRelationSequenceCount(); i++) {
            benchmark.runExperiment(relSet.getRelationSequence(i));
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:48:26)
     */
    public static void initBenchmark(ExperimentRunner benchmark) {
        RelationSequence relGen = new PercentFilledRelationGenerationStrategy(1, 1, 20, 20, 1, 0.1);
        benchmark.runExperiment(relGen);
    }

    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 13:37:34)
     */
    protected static ExperimentRunner setUpExperimentRunner(ExperimentSet experimentSet, PrintWriter pw, PrintWriter screenStream) {
        ExperimentRunner benchmark = new ExperimentRunner();
        benchmark.setExperimentSet(experimentSet);
        benchmark.setOutStream(pw);
        benchmark.setScreenStream(screenStream);
        return benchmark;
    }
}
