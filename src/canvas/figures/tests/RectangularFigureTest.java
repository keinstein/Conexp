/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.figures.tests;

import canvas.figures.BorderCalculatingFigure;
import canvas.figures.RectangularFigure;
import canvas.tests.FigureTest;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.*;
import java.awt.geom.Point2D;

public class RectangularFigureTest extends FigureTest {

    public static Test suite() {
        return new TestSuite(RectangularFigureTest.class);
    }

    private static final double centerX = 0;
    private static final double centerY = 0;
    private static final double width = 40;
    private static final double height = 20;

    protected canvas.Figure makeFigure() {
        return new RectangularFigure(centerX, centerY, width, height) {
            public void draw(Graphics g, canvas.CanvasScheme opt) {
            }
        };
    }

    public void testBorderAt() {

        Point2D outPoint = new Point2D.Double(centerX, centerY - height);
        Point2D result = new Point2D.Double();
        BorderCalculatingFigure bf = (BorderCalculatingFigure) f;

        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX, centerY - height / 2), result);

        outPoint.setLocation(centerX - height, centerY - height);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX - height / 2, centerY - height / 2), result);

        outPoint.setLocation(centerX + height, centerY + height);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX + height / 2, centerY + height / 2), result);

        outPoint.setLocation(centerX - height, centerY + height);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX - height / 2, centerY + height / 2), result);

        outPoint.setLocation(centerX - width, centerY);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX - width / 2, centerY), result);

        outPoint.setLocation(centerX - width, centerY - height / 4);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX - width / 2, centerY - height / 8), result);

        outPoint.setLocation(centerX + width, centerY);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX + width / 2, centerY), result);

        outPoint.setLocation(centerX + width, centerY - height / 4);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX + width / 2, centerY - height / 8), result);

        outPoint.setLocation(centerX + width, centerY + height / 4);
        bf.borderAt(outPoint, result);
        assertEquals(new Point2D.Double(centerX + width / 2, centerY + height / 8), result);

    }

}
