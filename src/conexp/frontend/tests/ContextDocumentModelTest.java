/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.tests;



import conexp.core.Context;
import conexp.core.ContextListener;
import conexp.core.DependencySet;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocumentModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import junit.framework.TestCase;

public class ContextDocumentModelTest extends TestCase {
    public void testClearingLatticesAfterSettingContext() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});
        ContextDocumentModel model = new ContextDocumentModel(cxt);
        assertEquals(0, model.getLatticeComponents().size());

        model.addLatticeComponent();
        assertEquals(1, model.getLatticeComponents().size());
        Context cxt2 = SetBuilder.makeContext(new int[][]{{1}});
        model.setContext(cxt2);
        assertEquals(0, model.getLatticeComponents().size());
    }

    public void testCorrectAdditionAndCleanUpOfContextListeners() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});
        final int listenersCount = cxt.getContextListenersCount();
        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.addLatticeComponent();
        Context cxt2 = SetBuilder.makeContext(new int[][]{{1}});
        model.setContext(cxt2);
        assertEquals(listenersCount, cxt.getContextListenersCount());
    }


    public void testDependentComponentCleanUpWhenChangingContext() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});

        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.setClearDependentRecalcPolicy();
        setupDependentComponentsForClearingRecalcPolicy(model);
        cxt.setRelationAt(0, 0, false);
        checkDependentComponentAreCleared(model);
    }

    public void testDependentComponentCleanUpWhenContextTransposed() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});

        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.setClearDependentRecalcPolicy();
        setupDependentComponentsForClearingRecalcPolicy(model);
        cxt.transpose();
        checkDependentComponentAreCleared(model);
    }

    public void testDependentComponentCleanUpWhenContextStructureChanged() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 1},
                {1, 1}});

        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.setClearDependentRecalcPolicy();
        setupDependentComponentsForClearingRecalcPolicy(model);
        cxt.reduceObjects();
        checkDependentComponentAreCleared(model);
    }


    private void setupDependentComponentsForClearingRecalcPolicy(ContextDocumentModel model) {
        model.addLatticeComponent();
        model.getLatticeComponent(0).calculateLattice();
        assertFalse("lattice expected to be computed",
                model.getLatticeComponent(0).isEmpty());
        model.findAssociations();
        assertTrue("Associations are expected to be computed",
                model.getAssociationMiner().isComputed());
        model.findImplications();
        assertTrue("Implications are expected to be computed",
                model.getImplicationBaseCalculator().isComputed());
    }

    private static void checkDependentComponentAreCleared(ContextDocumentModel model) {
        assertTrue(model.getLatticeComponent(0).isEmpty());
        assertFalse(model.getAssociationMiner().isComputed());
        assertFalse(model.getImplicationBaseCalculator().isComputed());
    }

    public void testSetRecomputeDependentRecalcPolicy() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});
        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.setRecomputeDependentRecalcPolicy();
        StrategyValueItem recalculationPolicy = model.getRecalculationPolicy();
        checkRecalcPolicyWasProperlySet(cxt, recalculationPolicy,
                ContextDocumentModel.RECOMPUTE_DEPENDENT_POLICY_KEY);
        model.setClearDependentRecalcPolicy();
        checkRecalcPolicyWasProperlySet(cxt, recalculationPolicy,
                ContextDocumentModel.CLEAR_DEPENDENT_POLICY_KEY);

    }

    private static void checkRecalcPolicyWasProperlySet(Context cxt,
                                                        StrategyValueItem recalculationPolicy,
                                                        String recalcPolicyKey) {
        assertEquals("Expect policy to change",
                recalcPolicyKey,
                recalculationPolicy.getStrategyKey());
        assertEquals(recalculationPolicy.getValue(),
                recalculationPolicy.findStrategyByKey(
                        recalcPolicyKey));
        assertTrue("The correspondent listener was not set",
                cxt.hasContextListener(
                        (ContextListener) recalculationPolicy.getStrategy()));
    }

    public void testEqualsOnRecalcPolicy() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});

        ContextDocumentModel model = new ContextDocumentModel(cxt);

        ContextDocumentModel.RecomputeDependentRecalcPolicy recomputeDependentRecalcPolicy = model.new RecomputeDependentRecalcPolicy();
        assertEquals(recomputeDependentRecalcPolicy,
                recomputeDependentRecalcPolicy);
        assertEquals(recomputeDependentRecalcPolicy, model.new RecomputeDependentRecalcPolicy());
        assertFalse(recomputeDependentRecalcPolicy.equals(model.new ClearDependentRecalcPolicy()));
    }

    public void testDependentComponentRecalculationWhenChangingContext() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1, 0},
                {0, 1}});

        ContextDocumentModel model = new ContextDocumentModel(cxt);
        model.setRecomputeDependentRecalcPolicy();
        model.getAssociationMiner().setConfidence(0.);

        model.addLatticeComponent();
        model.getLatticeComponent(0).calculateLattice();
        assertFalse("lattice expected to be computed",
                model.getLatticeComponent(0).isEmpty());
        assertEquals(4, model.getLatticeComponent(0).getLattice().conceptsCount());
        model.findAssociations();
        assertTrue("Associations are expected to be computed",
                model.getAssociationMiner().isComputed());
        DependencySet oldAssociationRules = model.getAssociationRules();
        assertEquals(3, oldAssociationRules.getSize());
        assertEquals(0, calculateExactRules(oldAssociationRules));

        model.findImplications();
        assertTrue("Implications are expected to be computed",
                model.getImplicationBaseCalculator().isComputed());
        assertEquals(0, model.getImplications().getSize());


        cxt.setRelationAt(0, 0, false);
        assertEquals(3, model.getLatticeComponent(0).getLattice().conceptsCount());
        assertTrue("Associations are expected to be computed",
                model.getAssociationMiner().isComputed());
        DependencySet associationRules = model.getAssociationRules();
        assertEquals(3, associationRules.getSize());
        assertEquals(1, calculateExactRules(associationRules));

        assertTrue(model.getImplicationBaseCalculator().isComputed());
        assertEquals(1, model.getImplications().getSize());

    }

    private static int calculateExactRules(DependencySet associationRules) {
        int size = associationRules.getSize();
        int countExact = 0;
        for (int i = 0; i < size; i++) {
            if (associationRules.getDependency(i).isExact()) {
                countExact++;
            }
        }
        return countExact;
    }


}
