/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.Figure;
import canvas.FigureDrawing;
import canvas.figures.FigureWithCoords;
import com.mockobjects.beans.MockPropertyChangeListener;
import junit.framework.TestCase;
import util.testing.TestUtil;

import java.awt.Point;
import java.awt.Rectangle;


public class FigureDrawingTest extends TestCase {
    protected FigureDrawing figureDrawing;
    protected FigureWithCoords f1;
    protected FigureWithCoords f2;

    protected void setUp() {
        figureDrawing = new FigureDrawing();
        f1 = new MockFigure(5, 5);
        figureDrawing.addFigure(f1);
        f2 = new MockFigure(15, 15);
        figureDrawing.addFigure(f2);
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.01.01 2:32:53)
     */
    public void testAddForegroundFigure() {
        assertEquals(figureDrawing.makeDrawingDimension(20, 20), figureDrawing.getDimension());
        assertEquals(false, figureDrawing.isBoundsRectDirty());
        assertEquals(new Rectangle(0, 0, 20, 20), figureDrawing.getUserBoundsRect());
        Figure f = new MockFigure(25, 25);
        figureDrawing.addForegroundFigure(f);
        assertEquals(figureDrawing.makeDrawingDimension(30, 30), figureDrawing.getDimension());
        assertEquals(new Rectangle(0, 0, 30, 30), figureDrawing.getUserBoundsRect());
    }

    public void testFireBoundsBoxChange() {
        assertEquals(new Rectangle(0, 0, 20, 20), figureDrawing.getUserBoundsRect());
        MockPropertyChangeListener listener = new MockPropertyChangeListener();
        listener.setExpectedEventCount(1);
        listener.setExpectedPropertyName(FigureDrawing.BOUNDS_BOX_PROPERTY);
        listener.setExpectedOldValue(new Rectangle(0, 0, 20, 20));
        listener.setExpectedNewValue(new Rectangle(0, 0, 30, 30));
        figureDrawing.addPropertyChangeListener(listener);


        Figure f = new MockFigure(25, 25);
        figureDrawing.addForegroundFigure(f);
        listener.verify();
    }


    public void testFindFigure() {
        Figure f = figureDrawing.findFigureInReverseOrder(6, 6);
        assertSame("should find first figure", f1, f);
        f = figureDrawing.findFigureInReverseOrder(11, 11);
        assertSame("should find second figure", f2, f);
        f = figureDrawing.findFigureInReverseOrder(11, 5);
        assertNull("shouldn't find any figure", f);

        f1.moveBy(10, 10);
        assertEquals(new Point(15, 15), f1.getCenter());
        assertSame(f2, figureDrawing.findFigureInReverseOrder(15, 15));
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.01.01 2:16:15)
     */
    public void testGetUserBoundsRect() {
        assertEquals(new Rectangle(0, 0, 20, 20), figureDrawing.getUserBoundsRect());

        MockFigureDrawingListener mockListener = new MockFigureDrawingListener();
        mockListener.expDim.addExpected(figureDrawing.makeDrawingDimension(25, 25));
        mockListener.dimChangedCnt.setExpected(1);
        figureDrawing.addDrawingChangedListener(mockListener);

        f1.moveBy(-5, -5);
        assertEquals(new Rectangle(-5, -5, 25, 25), figureDrawing.getUserBoundsRect());
        mockListener.verify();

        mockListener.reset();
        mockListener.dimChangedCnt.setExpected(1);
        mockListener.expDim.addExpected(figureDrawing.makeDrawingDimension(20, 30));

        f2.moveBy(-5, 5);
        assertEquals(new Rectangle(-5, -5, 20, 30), figureDrawing.getUserBoundsRect());
        mockListener.verify();


        mockListener.reset();
        mockListener.dimChangedCnt.setExpected(1);
        mockListener.expDim.addExpected(figureDrawing.makeDrawingDimension(15, 25));

        f2.moveBy(-5, -5);
        assertEquals(new Rectangle(-5, -5, 15, 25), figureDrawing.getUserBoundsRect());
        mockListener.verify();
    }

    public void testUserBoundRectWithNonZeroFiguresDisplacement() {
        f1.moveBy(40, 40);
        f2.moveBy(40, 40);
        assertEquals(new Rectangle(40, 40, 20, 20), figureDrawing.getUserBoundsRect());
    }

    /**
     * Insert the method's description here.
     * Creation date: (20.01.01 0:16:06)
     */
    public void testLayOnBorder() {
        assertEquals(new Rectangle(0, 0, 20, 20), figureDrawing.getUserBoundsRect());
        assertTrue(figureDrawing.layOnBorder(new Rectangle(0, 0, 1, 1)));
        assertTrue(figureDrawing.layOnBorder(new Rectangle(0, 1, 1, 1)));
        assertTrue(figureDrawing.layOnBorder(new Rectangle(1, 0, 1, 1)));
        assertTrue(figureDrawing.layOnBorder(new Rectangle(1, 19, 1, 1)));
        assertTrue(figureDrawing.layOnBorder(new Rectangle(19, 18, 1, 1)));
        assertEquals(false, figureDrawing.layOnBorder(new Rectangle(5, 5, 5, 5)));
        assertEquals(false, figureDrawing.layOnBorder(new Rectangle(18, 18, 1, 1)));
    }

    public void testClear() {
        assertEquals(new Rectangle(0, 0, 20, 20), figureDrawing.getUserBoundsRect());
        assertEquals(figureDrawing.makeDrawingDimension(20, 20), figureDrawing.getDimension());

        figureDrawing.clear();
        assertEquals(new Rectangle(), figureDrawing.getUserBoundsRect());
    }

    public void testEquals() {
        FigureDrawing fd1 = new FigureDrawing();
        TestUtil.testNotEquals(fd1, new Object());
        TestUtil.testNotEquals(fd1, null);
        assertEquals(fd1, fd1);
        FigureDrawing fd2 = new FigureDrawing();
        TestUtil.testEqualsAndHashCode(fd1, fd2);
        fd1.addFigure(f1);
        TestUtil.testNotEquals(fd1, fd2);
        fd2.addFigure(f1);
        TestUtil.testEqualsAndHashCode(fd1, fd2);
        fd1.addForegroundFigure(f2);
        TestUtil.testNotEquals(fd1, fd2);
        fd2.addForegroundFigure(f2);
        TestUtil.testEqualsAndHashCode(fd1, fd2);
        fd1.getDimension();

    }
}
