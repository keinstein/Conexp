/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import conexp.core.ContextFactoryRegistry;
import conexp.core.bitset.BitSetFactory;
import conexp.experimenter.relationsequences.PercentFilledRelationGenerationStrategy;

import java.io.PrintWriter;



public class BaseExperimentSuite {

    public static void doRunTimeMeasurementExperiment(ExperimentSet set, RelationSequenceSet relSet) {
        doRunExperiment(set, relSet, null);
    }

    protected static void doRunExperiment(ExperimentSet set, RelationSequenceSet relSet, ExperimentContextFactory factory) {
        ExperimentRunner benchmark = BaseExperimentSuite.setUpExperimentRunner(set, new PrintWriter(System.out, true), new PrintWriter(System.err, true), factory);
        BaseExperimentSuite.initBenchmark(benchmark);
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
    protected static ExperimentRunner setUpExperimentRunner(ExperimentSet experimentSet, PrintWriter pw, PrintWriter screenStream, ExperimentContextFactory factory) {
        ExperimentRunner benchmark = new ExperimentRunner(factory);
        benchmark.setExperimentSet(experimentSet);
        benchmark.setOutStream(pw);
        benchmark.setScreenStream(screenStream);
        return benchmark;
    }

    public static void doRunExperimentWithCountingOfSetOperations(final ExperimentSet experimentSet, final RelationSequenceSet relationSequence) {
        ExperimentContextFactory factory = new ExperimentContextFactory();
        ContextFactoryRegistry.setContextFactory(factory);
        doRunExperiment(experimentSet, relationSequence, factory);
        ContextFactoryRegistry.setContextFactory(BitSetFactory.getInstance());
    }
}
