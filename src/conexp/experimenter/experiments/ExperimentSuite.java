package conexp.experimenter.experiments;

import conexp.experimenter.framework.BaseExperimentSuite;
import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequence;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.PercentFilledRelationGenerationStrategy;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 18/8/2003
 * Time: 16:34:46
 */

public class ExperimentSuite extends BaseExperimentSuite {

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:47:57)
     */
    public static RelationSequenceSet makeRelationSequenceSet() {
        RelationSequenceSet relSet = new RelationSequenceSet();
        addExponentialSequence(relSet);
        addSparseSequence(relSet);
        addTypicalSequenceAndTransposed(relSet);
        return relSet;
    }

    private static void addExponentialSequence(RelationSequenceSet relSet) {
        relSet.addRelationSequence(new conexp.experimenter.relationsequences.ExponentialRelationGenerationStrategy(5, 9, 9-5+1));
    }

    private static void addTypicalSequenceAndTransposed(RelationSequenceSet relSet) {

        //while having problems with memory usage, limit k to 7, later lift to 9
        final int lowFillBound = 1;
        final int upperFillBound = 7;


        RelationSequence[] toTranspose = new RelationSequence[upperFillBound];
        for (int k = lowFillBound; k <= upperFillBound; k++) {
//            toTranspose[k - 1] = new PercentFilledRelationGenerationStrategy(1, 30, 5, 5, 30/*count*/, (double) k / 10);
            toTranspose[k - 1] = new PercentFilledRelationGenerationStrategy(10, 100, 20, 20, 10/*count*/, (double) k / 10);
            relSet.addRelationSequence(toTranspose[k - 1]);
        }

        for (int k = lowFillBound; k <= upperFillBound; k++) {
            relSet.addRelationSequence(new conexp.experimenter.relationsequences.TransposeRelationSequenceDecorator(toTranspose[k - 1]));
        }
    }

    private static void addSparseSequence(RelationSequenceSet relSet) {
//        RelationSequence sparse = new conexp.experimenter.relationsequences.FixedIntentSizeRelationGenerationStrategy(100, 500, 100, 100, 5, 4);
        RelationSequence sparse = new conexp.experimenter.relationsequences.FixedIntentSizeRelationGenerationStrategy(100, 900, 50, 50, 9, 3);
        relSet.addRelationSequence(sparse);
        relSet.addRelationSequence(new conexp.experimenter.relationsequences.TransposeRelationSequenceDecorator(sparse));
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 9:25:41)
     */
    protected static void doRunExperimentSet(ExperimentSet set) {
        final RelationSequenceSet relSet = ExperimentSuite.makeRelationSequenceSet();
        doRunTimeMeasurementExperiment(set, relSet);
    }

}
