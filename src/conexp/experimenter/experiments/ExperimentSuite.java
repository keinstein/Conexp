/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.experimenter.framework.BaseExperimentSuite;
import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.RelationSequence;
import conexp.experimenter.framework.RelationSequenceSet;
import conexp.experimenter.relationsequences.ExponentialRelationGenerationStrategy;
import conexp.experimenter.relationsequences.FixedIntentSizeRelationGenerationStrategy;
import conexp.experimenter.relationsequences.PercentFilledRelationGenerationStrategy;
import conexp.experimenter.relationsequences.TransposeRelationSequenceDecorator;



public class ExperimentSuite extends BaseExperimentSuite {

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:47:57)
     */
    public static RelationSequenceSet createRelationSequenceSet() {
        RelationSequenceSet relSet = new RelationSequenceSet();
        //addExponentialSequence(relSet);

        addSparseSequence(relSet);
/*        addTypicalSequenceAndTransposed(relSet);
*/
        return relSet;
    }

    protected static void addExponentialSequence(RelationSequenceSet relSet) {
        relSet.addRelationSequence(new ExponentialRelationGenerationStrategy(5, 19, 19 - 5 + 1));
    }

    protected static void addTypicalSequenceAndTransposed(RelationSequenceSet relSet) {

        //while having problems with memory usage, limit k to 7, later lift to 9
        final int lowFillBound = 1;
        final int upperFillBound = 7;


        RelationSequence[] toTranspose = new RelationSequence[upperFillBound];
        for (int k = lowFillBound; k <= upperFillBound; k++) {
            toTranspose[k - 1] = new PercentFilledRelationGenerationStrategy(1, 30, 5, 5, 30/*count*/, (double) k / 10);
            // toTranspose[k - 1] = new PercentFilledRelationGenerationStrategy(10, 100, 20, 20, 10/*count*/, (double) k / 10);
            relSet.addRelationSequence(toTranspose[k - 1]);
        }

        for (int k = lowFillBound; k <= upperFillBound; k++) {
            relSet.addRelationSequence(new TransposeRelationSequenceDecorator(toTranspose[k - 1]));
        }
    }

    protected static void addSparseSequence(RelationSequenceSet relSet) {
//        RelationSequence sparse = new conexp.experimenter.relationsequences.FixedIntentSizeRelationGenerationStrategy(100, 500, 100, 100, 5, 4);
        //RelationSequence sparse = new conexp.experimenter.relationsequences.FixedIntentSizeRelationGenerationStrategy(100, 900, 100, 100, 9, 3);
        RelationSequence sparse = new FixedIntentSizeRelationGenerationStrategy(1000, 9000, 100, 100, 9, 3);
        relSet.addRelationSequence(sparse);
        relSet.addRelationSequence(new TransposeRelationSequenceDecorator(sparse));
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 9:25:41)
     */
    protected static void doRunExperimentSet(ExperimentSet set) {
        doRunTimeMeasurementExperiment(set, ExperimentSuite.createRelationSequenceSet());
    }


    protected static void doRunExperimentSetWithOperationCount(ExperimentSet set) {
        doRunExperimentWithCountingOfSetOperations(set, ExperimentSuite.createRelationSequenceSet());
    }

}
