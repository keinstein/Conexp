/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.tests;

import canvas.DefaultTool;
import canvas.Tool;
import canvas.ToolEvent;
import com.mockobjects.ExpectationList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DefaultToolTest extends TestCase {

    public static Test suite() {
        return new TestSuite(DefaultToolTest.class);
    }

    static class MockToolListener extends canvas.DefaultToolListener {
        ExpectationList expList;

        public MockToolListener() {
            reset();
        }

        public void reset() {
            expList = new ExpectationList("Expected events");
        }

        public void toolStateChanged(canvas.ToolEvent toolEvent) {
            expList.addActual(toolEvent);
        }

        public void addExpectedEvent(canvas.ToolEvent toolEvent) {
            expList.addExpected(toolEvent);
        }

        public void verify() {
            reset();
            expList.verify();
        }
    }

    public void testFiringOfEventsOnActivate() {
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

    public void testFiringOfEventsOnEnabledChange() {
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

    private Tool makeTool() {
        return new DefaultTool();
    }
}
