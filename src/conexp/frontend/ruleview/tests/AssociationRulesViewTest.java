/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview.tests;

import conexp.frontend.ruleview.AssociationRulesView;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.TestCase;

public class AssociationRulesViewTest extends TestCase {
    public static void testResources() {
        AssociationRulesView ruleView = new AssociationRulesView(new MockDependencySetSupplier(), null);
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(ruleView.getResources(), ruleView.getActionChain());
    }

}
