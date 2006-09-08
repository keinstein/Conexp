/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.associations.AssociationRule;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AssociationRuleTest extends TestCase {
    private static final Class THIS = AssociationRuleTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    private AssociationRule ruleApproximate;
    private AssociationRule ruleExact;

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:30:44)
     */
    protected void setUp() {
        ruleApproximate = AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 1}, 2, new int[]{0, 1, 0}, 1);
        ruleExact = AssociationsBuilder.makeAssociationRule(new int[]{0, 1, 0}, 1, new int[]{0, 0, 1}, 1);
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:40:20)
     */
    private static void doTestConfidence(AssociationRule rule, int expPremiseSupport, int expConclusionSupport, double expConfidence, boolean expIsExact) {
        assertEquals(expPremiseSupport, rule.getPremiseSupport());
        assertEquals(expConclusionSupport, rule.getRuleSupport());
        assertEquals(expConfidence, rule.getConfidence(), 0.1e-5);
        assertEquals(expIsExact, rule.isExact());
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:32:10)
     */
    public void testConclusion() {
        assertEquals(SetBuilder.makeSet(new int[]{0, 1, 0}), ruleApproximate.getConclusion());
        assertEquals(SetBuilder.makeSet(new int[]{0, 0, 1}), ruleExact.getConclusion());
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:32:58)
     */
    public void testConfidence() {
        doTestConfidence(ruleApproximate, 2, 1, 0.5, false);
        doTestConfidence(ruleExact, 1, 1, 1, true);
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:33:24)
     */
    public void testIsValid() {
        assertEquals(true, ruleApproximate.isValid());
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 2:31:36)
     */
    public void testPremise() {
        assertEquals(SetBuilder.makeSet(new int[]{0, 0, 1}), ruleApproximate.getPremise());
        assertEquals(SetBuilder.makeSet(new int[]{0, 1, 0}), ruleExact.getPremise());
    }
}
