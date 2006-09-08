/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.util.tests;

import canvas.DefaultTool;
import canvas.util.ToolAction;
import canvas.util.ToolToggleButton;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ToolToggleButtonTest extends TestCase {

    public static Test suite() {
        return new TestSuite(ToolToggleButtonTest.class);
    }

    public void testButtonStateChangeOnToolActivation() {
        DefaultTool actionTool = new DefaultTool();
        actionTool.activate();
        ToolAction action = new ToolAction(null, "testAction", "Test action", actionTool);

        ToolToggleButton button = new ToolToggleButton(action);
        assertTrue(button.isSelected());

        actionTool.deactivate();
        assertFalse(button.isSelected());

        actionTool.activate();
        assertTrue(button.isSelected());
    }

    public static void testButtonStateChangeOnChangeOfEnabled() {

        DefaultTool actionTool = new DefaultTool();
        actionTool.setEnabled(false);
        ToolAction action = new ToolAction(null, "testAction", "Test action", actionTool);
        ToolToggleButton button = new ToolToggleButton(action);
        assertFalse(button.isEnabled());

        actionTool.setEnabled(true);

        assertTrue(button.isEnabled());
        actionTool.setEnabled(false);
        assertFalse(button.isEnabled());
    }
}
