package conexp.experimenter.experiments.tests;

import conexp.experimenter.experiments.ExperimentSuite;
import conexp.experimenter.experiments.ConceptSetExperimentSuite;
import conexp.experimenter.framework.ExperimentSet;
import conexp.experimenter.framework.IExperiment;
import conexp.experimenter.framework.RelationSequence;
import conexp.experimenter.framework.RelationSequenceSet;
import junit.framework.TestCase;

/**
 * JUnit test case for ConceptSetExperimentSuiteTest
 */

public class ExperimentSuiteTest extends TestCase {

    public void doTestCreateExperimentSet(ExperimentSet set) {
        assertNotNull(set);
        conexp.core.BinaryRelation rel = conexp.core.tests.SetBuilder.makeRelation(new int[][]{{0, 0},
                                                                                                       {0, 0}});
        for (int i = 0; i < set.experimentCount(); i++) {
            IExperiment exp = set.experimentAt(i);
            assertNotNull(exp);
            try {
                exp.setUp(rel);
                exp.perform();
                exp.tearDown();
            } catch (Exception ex) {
                fail("experiment " + i + " failed" + ex);
            }
        }
    }


    public void testCreateExperimentSet() {
        ExperimentSet set = ConceptSetExperimentSuite.createExperimentSet();
        doTestCreateExperimentSet(set);
    }

    public void testRelationSequence() {
        RelationSequenceSet set = ExperimentSuite.makeRelationSequenceSet();
        assertNotNull(set);
        for (int i = 0; i < set.getRelationSequenceCount(); i++) {
            RelationSequence seq = set.getRelationSequence(i);
            assertNotNull(seq);
            int j = 0;
            try {
                for (j = 0; j < seq.getRelationCount(); j++) {
                    assertNotNull(seq.getRelation(j));
                }
            } catch (Exception ex) {
                fail("relationSet member " + i + "at position " + j + " failed");
            }
        }
    }
}