/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework.tests;

import conexp.core.BinaryRelation;
import conexp.core.ContextFactoryRegistry;
import conexp.core.bitset.BitSetFactory;
import conexp.core.tests.SetBuilder;
import conexp.experimenter.experiments.BasicExperiment;
import conexp.experimenter.framework.ExperimentContextFactory;
import conexp.experimenter.framework.ExperimentRunResults;
import conexp.experimenter.framework.ExperimentRunner;
import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.IExperiment;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.framework.RelationSequence;
import conexp.experimenter.relationsequences.BinaryRelationBasedRelationSequence;
import conexp.experimenter.setdecorator.OperationCodes;
import conexp.experimenter.setdecorator.OperationStatistic;
import junit.framework.TestCase;



public class ExperimentRunnerTest extends TestCase {
    public static void testMeasurementOfOperationCount() {
        ExperimentContextFactory experimentContextFactory = new ExperimentContextFactory();
        ContextFactoryRegistry.setContextFactory(experimentContextFactory);
        ExperimentRunner runner = new ExperimentRunner(experimentContextFactory);

        ExperimentSet experimentSet = new ExperimentSet();
        IExperiment mockExperiment = new MockExperiment();
        experimentSet.addExperiment(mockExperiment);
        runner.setExperimentSet(experimentSet);

        RelationSequence relationSequence = makeRelationSequence();
        runner.runExperiment(relationSequence);
        assertEquals(1, runner.getNumberOfExperimentsRuns());
        ExperimentRunResults runResults = runner.getRunResults(0);
        MeasurementSet measurementSet = runResults.getResultForExperiment(0);
        OperationStatistic measurementValue = (OperationStatistic) measurementSet.getMeasurementValue(ExperimentRunner.OPERATION_COUNT);
        assertNotNull(measurementValue);
        assertEquals(9, measurementValue.getOperationCount(OperationCodes.IN));
        ContextFactoryRegistry.setContextFactory(BitSetFactory.getInstance());
    }

    public static void testNoOperationCount() {
        ExperimentRunner runner = new ExperimentRunner();

        ExperimentSet experimentSet = new ExperimentSet();
        IExperiment mockExperiment = new MockExperiment();
        experimentSet.addExperiment(mockExperiment);
        runner.setExperimentSet(experimentSet);
        RelationSequence relationSequence = makeRelationSequence();
        runner.runExperiment(relationSequence);
        assertEquals(1, runner.getNumberOfExperimentsRuns());
        ExperimentRunResults runResults = runner.getRunResults(0);
        MeasurementSet measurementSet = runResults.getResultForExperiment(0);
        OperationStatistic measurementValue = (OperationStatistic) measurementSet.getMeasurementValue(ExperimentRunner.OPERATION_COUNT);
        assertNull(measurementValue);
    }

    private static BinaryRelationBasedRelationSequence makeRelationSequence() {
        return new BinaryRelationBasedRelationSequence(SetBuilder.makeRelation(new int[][]{
                {1, 1, 1},
                {0, 1, 1},
                {0, 0, 1}
        }));
    }


    private static class MockExperiment extends BasicExperiment {
        protected Object getActionObject() {
            return "MockExperiment";
        }

        BinaryRelation relation;

        public void setUp(BinaryRelation rel) {
            this.relation = rel;
        }

        public void perform() {
            for (int i = 0; i < relation.getRowCount(); i++) {
                for (int j = 0; j < relation.getColCount(); j++) {
                    relation.getSet(i).in(j);
                }
            }
        }

        public void tearDown() {
            relation = null;
        }
    }
}
