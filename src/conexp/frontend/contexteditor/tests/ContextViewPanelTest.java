/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.contexteditor.tests;

import conexp.core.tests.SetBuilder;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ContextViewPanelTest extends TestCase {
    private static final Class THIS = ContextViewPanelTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testResources() {
        ContextViewPanel panel = new ContextViewPanel(SetBuilder.makeContext(new int[][]{{0, 1}}));
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(panel.getResources(), panel.getActionChain());
    }

}
