package conexp.frontend.ruleview.tests;

/**
 * User: sergey
 * Date: 26/6/2005
 * Time: 2:45:09
 */

import conexp.core.tests.SetBuilder;
import conexp.frontend.ruleview.*;
import junit.framework.TestCase;

public class RulePaneTest extends TestCase {
    public static final String EMPTY_MSG = "EMPTY_MESSAGE";
    public static final String RULE_SET_TO_BE_RECOMPUTED = "Rule Set should be recalculated";

    public void testMessageForNotComputedSet(){
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
                renderer, new RulePaneMessages(){
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