/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.Figure;
import junit.framework.TestCase;

import java.awt.Rectangle;


public abstract class FigureTest extends TestCase {
    protected Figure f;

    protected abstract Figure makeFigure();

    protected void setUp() {
        f = makeFigure();
    }

    public void testBoundingBox() {
        Rectangle rect = new Rectangle();
        assertTrue(rect.isEmpty());
        f.boundingBox(rect);
        assertEquals(false, rect.isEmpty());
    }
}
