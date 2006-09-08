/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.ruleview.tests;



import conexp.frontend.ruleview.GenericRuleView;
import conexp.frontend.ruleview.RulePaneMessages;
import conexp.frontend.tests.ResourcesToolbarDefinitionTestHelper;
import junit.framework.TestCase;

public abstract class GenericRuleViewTest extends TestCase {
    GenericRuleView ruleView;

    protected void setUp() {
        ruleView = makeRuleView();
    }


    public void testMakeRulePaneMessages() throws Exception {
        RulePaneMessages rulePaneMessages = ruleView.makeRulePaneMessages();
        assertNotNull("Empty rule pane message should not be null", rulePaneMessages.getEmptyRulesetMessage());
        assertNotNull("RuleSet should be recalculated message should be not null", rulePaneMessages.getRuleSetShouldBeRecalculated());
    }

    protected abstract GenericRuleView makeRuleView();

    public void testResources() {
        ResourcesToolbarDefinitionTestHelper.testToolbarDefinitionInResources(ruleView.getResources(), ruleView.getActionChain());
    }
}
