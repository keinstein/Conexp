/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview.tests;

import conexp.frontend.ruleview.ImplicationsView;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ImplicationsViewTest extends TestCase {
    private static final Class THIS = ImplicationsViewTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public static void testResources() {
        ImplicationsView ruleView = new ImplicationsView(new MockDependencySetSupplier(), null);
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(ruleView.getResources(), ruleView.getActionChain());
    }

}
