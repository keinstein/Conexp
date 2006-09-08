/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor.tests;

import conexp.core.tests.SetBuilder;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTestHelper;
import junit.framework.TestCase;

public class ContextViewPanelTest extends TestCase {
    public static void testResources() {
        ContextViewPanel panel = new ContextViewPanel(SetBuilder.makeContext(new int[][]{{0, 1}}));
        ResourcesToolbarDefinitionTestHelper.testToolbarDefinitionInResources(panel.getResources(), panel.getActionChain());
    }

}
