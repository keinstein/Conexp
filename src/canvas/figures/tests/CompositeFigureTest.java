/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures.tests;

import canvas.figures.CompositeFigure;
import canvas.tests.MockFigure;
import junit.framework.TestCase;

public class CompositeFigureTest extends TestCase {

    public void testFindFigureInside() {
        CompositeFigure figure = new CompositeFigure();
        MockFigure firstInner = new MockFigure(10, 10);
        figure.addFigure(firstInner);

        MockFigure secondInner = new MockFigure(10, 10);
        figure.addFigure(secondInner);

        assertTrue(figure.contains(10, 10));
        assertSame(secondInner, figure.findFigureInside(10, 10));
    }
}
