/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.DefaultTool;
import canvas.FigureDrawingCanvas;
import junit.framework.TestCase;
import util.collection.CollectionFactory;
import util.testing.SimpleMockPropertyChangeListener;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

public class FigureDrawingCanvasTest extends TestCase {

    static class MockTool extends DefaultTool {
        List callsList = CollectionFactory.createDefaultList();

        public void clearCalls() {
            callsList.clear();
        }

        public List getCallsList() {
            return callsList;
        }

        public void activate() {
            super.activate();
            callsList.add("activate");
        }

        public void deactivate() {
            super.deactivate();
            callsList.add("deactivate");
        }

    }

    public void testCallsOfEventOnActiveToolChange() {
        FigureDrawingCanvas canvas = new FigureDrawingCanvas();
        MockTool tool = new MockTool();
        tool.clearCalls();

        canvas.setActiveTool(tool);
        canvas.deactivateTool();

        assertEquals(Arrays.asList(new String[]{"activate",
                "deactivate"}), tool.getCallsList());
    }

    public static void testFiringOfSelectionEvent() {
        FigureDrawingCanvas canvas = new FigureDrawingCanvas();
        MockFigure one = new MockFigure(5, 5);
        MockFigure two = new MockFigure(20, 24);
        canvas.getDrawing().addFigure(one);
        canvas.getDrawing().addFigure(two);


        assertFalse(canvas.hasSelection());
        SimpleMockPropertyChangeListener mockListener = new SimpleMockPropertyChangeListener(FigureDrawingCanvas.SELECTION_PROPERTY);
        canvas.addPropertyChangeListener(mockListener);
        mockListener.setExpected(1);
        canvas.selectFigure(one);
        mockListener.verify();
        mockListener.setExpected(1);
        canvas.selectFigure(two);
        mockListener.verify();
    }

    public static void testResizeOfCanvasOnZoomChange() {
        FigureDrawingCanvas canvas = new FigureDrawingCanvas();
        MockFigure one = new MockFigure(5, 5);
        MockFigure two = new MockFigure(95, 45);
        canvas.getDrawing().addFigure(one);
        canvas.getDrawing().addFigure(two);
        assertEquals(new Dimension(100, 50), canvas.getSize());
        canvas.setZoom(2.0);
        assertEquals(new Dimension(200, 100), canvas.getSize());
    }

}
