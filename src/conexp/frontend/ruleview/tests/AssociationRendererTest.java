/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.core.associations.AssociationRule;
import conexp.core.associations.tests.AssociationsBuilder;
import conexp.frontend.ruleview.AssociationRuleSupportRenderer;


public class AssociationRendererTest extends ImplicationRendererTest {
    protected void addRepresentableDependencies() {
        super.addRepresentableDependencies();

        AssociationRule rule = AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 1}, 2, new int[]{0, 1, 0}, 1);
        assertTrue(rule.isValid());
        depSet.addDependency(rule);
    }

    /**
     * Creation date: (07.05.01 1:57:45)
     */
    protected conexp.frontend.ruleview.RuleRenderer makeRuleRenderer() {
        return new AssociationRuleSupportRenderer();
    }

}
