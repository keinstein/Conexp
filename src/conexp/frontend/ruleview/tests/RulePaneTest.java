/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ruleview.tests;



import conexp.core.tests.SetBuilder;
import conexp.frontend.ruleview.ImplicationBaseCalculator;
import conexp.frontend.ruleview.ImplicationRenderer;
import conexp.frontend.ruleview.NextClosedSetImplicationCalculatorFactory;
import conexp.frontend.ruleview.RulePane;
import conexp.frontend.ruleview.RulePaneMessages;
import conexp.frontend.ruleview.RuleRenderer;
import junit.framework.TestCase;

public class RulePaneTest extends TestCase {
    public static final String EMPTY_MSG = "EMPTY_MESSAGE";
    public static final String RULE_SET_TO_BE_RECOMPUTED = "Rule Set should be recalculated";

    public void testMessageForNotComputedSet() {
        RuleRenderer renderer = new ImplicationRenderer();
        ImplicationBaseCalculator implicationBaseCalculator =
                (new ImplicationBaseCalculator(
                        SetBuilder.makeContext(new int[][]{
                                {1, 1}
                        }),
                        NextClosedSetImplicationCalculatorFactory.getInstance()
                ));

        assertFalse(implicationBaseCalculator.isComputed());

        RulePane rulePane = new RulePane(implicationBaseCalculator,
                renderer, new RulePaneMessages() {
            public String getEmptyRulesetMessage() {
                return EMPTY_MSG;
            }

            public String getRuleSetShouldBeRecalculated() {
                return RULE_SET_TO_BE_RECOMPUTED;
            }
        });
        String text = rulePane.getText().trim();
        assertFalse(EMPTY_MSG.equals(text));

    }

}
