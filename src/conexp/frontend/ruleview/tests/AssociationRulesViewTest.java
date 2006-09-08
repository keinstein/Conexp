/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.frontend.ruleview.AssociationRulesView;
import conexp.frontend.ruleview.GenericRuleView;

public class AssociationRulesViewTest extends GenericRuleViewTest {

    protected GenericRuleView makeRuleView() {
        return new AssociationRulesView(new MockDependencySetSupplier(), null);
    }

}
