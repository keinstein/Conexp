/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.DefaultTool;
import canvas.DefaultToolListener;
import canvas.Tool;
import canvas.ToolEvent;
import com.mockobjects.ExpectationList;
import junit.framework.TestCase;

public class DefaultToolTest extends TestCase {

    static class MockToolListener extends DefaultToolListener {
        ExpectationList expList;

        public MockToolListener() {
            reset();
        }

        public void reset() {
            expList = new ExpectationList("Expected events");
        }

        public void toolStateChanged(ToolEvent toolEvent) {
            expList.addActual(toolEvent);
        }

        public void addExpectedEvent(ToolEvent toolEvent) {
            expList.addExpected(toolEvent);
        }

        public void verify() {
            reset();
            expList.verify();
        }
    }

    public static void testFiringOfEventsOnActivate() {
        Tool tool = makeTool();
        MockToolListener mockListener = new MockToolListener();
        tool.addToolListener(mockListener);
        mockListener.addExpectedEvent(ToolEvent.makeToolActivatedEvent(tool));
        tool.activate();
        assertTrue(tool.isActive());
        mockListener.verify();

        mockListener.addExpectedEvent(ToolEvent.makeToolDeactivateEvent(tool));
        tool.deactivate();
        assertFalse(tool.isActive());
        mockListener.verify();
    }

    public static void testFiringOfEventsOnEnabledChange() {
        Tool tool = makeTool();
        MockToolListener mockListener = new MockToolListener();
        tool.addToolListener(mockListener);
        mockListener.addExpectedEvent(ToolEvent.makeToolDisabledEvent(tool));
        assertTrue(tool.isEnabled());
        tool.setEnabled(false);
        assertFalse(tool.isEnabled());
        mockListener.verify();

        mockListener.addExpectedEvent(ToolEvent.makeToolEnabledEvent(tool));
        tool.setEnabled(true);
        assertTrue(tool.isEnabled());
        mockListener.verify();
    }

    private static Tool makeTool() {
        return new DefaultTool();
    }
}
