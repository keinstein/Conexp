/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.frontend.ruleview.GenericRuleView;
import conexp.frontend.ruleview.ImplicationsView;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ImplicationsViewTest extends GenericRuleViewTest {
    private static final Class THIS = ImplicationsViewTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected GenericRuleView makeRuleView() {
        return new ImplicationsView(new MockDependencySetSupplier(), null);
    }

}
