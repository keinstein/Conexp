/*
 * $Id$
 * Copyright (c) 2005 realtime (http://www.realtime.dk),
 * All Rights Reserved.
 */
package conexp.frontend.ruleview.tests;

/**
 * Test suite for conexp.frontend.ruleview.GenericRuleView.
 *
 * @author Serhiy Yevtushenko sye@realtime.dk
 * @version $Revision $
 * @see conexp.frontend.ruleview.GenericRuleView
 */

import conexp.frontend.ruleview.GenericRuleView;
import conexp.frontend.ruleview.RulePaneMessages;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.TestCase;

public abstract class GenericRuleViewTest extends TestCase {
    GenericRuleView ruleView;

    protected void setUp() {
        ruleView = makeRuleView();
    }


    public void testMakeRulePaneMessages() throws Exception {
        RulePaneMessages rulePaneMessages = ruleView.makeRulePaneMessages();
        assertNotNull("Empty rule pane message should not be null",rulePaneMessages.getEmptyRulesetMessage());
        assertNotNull("RuleSet should be recalculated message should be not null", rulePaneMessages.getRuleSetShouldBeRecalculated());
    }

    protected abstract GenericRuleView makeRuleView();

    public void testResources() {
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(ruleView.getResources(), ruleView.getActionChain());
    }
}