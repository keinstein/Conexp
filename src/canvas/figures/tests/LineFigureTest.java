/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures.tests;

import canvas.Figure;
import canvas.figures.ConnectionFigure;
import canvas.figures.LineFigure;
import canvas.tests.FigureTest;
import canvas.tests.MockFigure;

public class LineFigureTest extends FigureTest {

    protected Figure makeFigure() {
        MockFigure start = new MockFigure(10, 10);
        MockFigure end = new MockFigure(20, 30);
        return new LineFigure(start, end);
    }

    public void testNegativeSizes() {
        ConnectionFigure lf = (ConnectionFigure) f;
        lf.getStartFigure().setCoords(30, 30);
        lf.getEndFigure().setCoords(10, 10);
        testBoundingBox();
    }

    public void testContains() {
        LineFigure lineFigure = (LineFigure) f;
        assertFalse(lineFigure.isSelectable());
        assertFalse(lineFigure.contains(15, 20));

        lineFigure.setSelectable(true);
        assertTrue(lineFigure.contains(15, 20));
    }
}
