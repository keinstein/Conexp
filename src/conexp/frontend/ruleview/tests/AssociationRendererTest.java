/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview.tests;

import conexp.core.associations.AssociationRule;
import conexp.core.associations.tests.ObjectMother;
import conexp.frontend.ruleview.AssociationRuleSupportRenderer;
import junit.framework.Test;
import junit.framework.TestSuite;


public class AssociationRendererTest extends ImplicationRendererTest {
    private static final Class THIS = AssociationRendererTest.class;

    protected void addRepresentableDependencies() {
        super.addRepresentableDependencies();

        AssociationRule rule = ObjectMother.makeAssociationRule(new int[]{0, 0, 1}, 2, new int[]{0, 1, 0}, 1);
        assertTrue(rule.isValid());
        depSet.addDependency(rule);
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:57:45)
     */
    protected conexp.frontend.ruleview.RuleRenderer makeRuleRenderer() {
        return new AssociationRuleSupportRenderer();
    }


    public static Test suite() {
        return new TestSuite(THIS);
    }
}
