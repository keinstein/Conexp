/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.tests;

import canvas.figures.RectangularFigure;

import java.awt.Graphics;

public class MockFigure extends RectangularFigure {
    public MockFigure(double x, double y) {
        this(x, y, 10, 10);
    }

    public MockFigure(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public void draw(Graphics g, canvas.CanvasScheme opt) {
    }

}
