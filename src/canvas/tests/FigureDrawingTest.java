package canvas.tests;

import canvas.Figure;
import canvas.FigureDrawing;
import canvas.figures.FigureWithCoords;
import com.mockobjects.beans.MockPropertyChangeListener;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Insert the type's description here.
 * Creation date: (13.01.01 2:07:00)
 * @author
 */
public class FigureDrawingTest extends TestCase {
    protected FigureDrawing fd;
    protected FigureWithCoords f1;
    protected FigureWithCoords f2;

    protected void setUp() {
        fd = new canvas.FigureDrawing();
        f1 = new MockFigure(5, 5);
        fd.addFigure(f1);
        f2 = new MockFigure(15, 15);
        fd.addFigure(f2);
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.01.01 2:07:56)
     * @return junit.framework.Test
     */
    public static Test suite() {
        return new TestSuite(FigureDrawingTest.class);
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.01.01 2:32:53)
     */
    public void testAddForegroundFigure() {
        assertEquals(fd.makeDrawingDimension(20, 20), fd.getDimension());
        assertEquals(false, fd.isBoundsRectDirty());
        assertEquals(new Rectangle(0,0, 20, 20), fd.getUserBoundsRect());
        Figure f = new MockFigure(25, 25);
        fd.addForegroundFigure(f);
        assertEquals(fd.makeDrawingDimension(30, 30), fd.getDimension());
        assertEquals(new Rectangle(0, 0, 30, 30), fd.getUserBoundsRect());
    }

    public void testFireBoundsBoxChange() {
        assertEquals(new Rectangle(0,0, 20, 20), fd.getUserBoundsRect());
        MockPropertyChangeListener listener = new MockPropertyChangeListener();
        listener.setExpectedEventCount(1);
        listener.setExpectedPropertyName(FigureDrawing.BOUNDS_BOX_PROPERTY);
        listener.setExpectedOldValue(new Rectangle(0, 0, 20, 20));
        listener.setExpectedNewValue(new Rectangle(0, 0, 30, 30));
        fd.addPropertyChangeListener(listener);



        Figure f = new MockFigure(25, 25);
        fd.addForegroundFigure(f);
        listener.verify();
    }


    public void testFindFigure() {
        canvas.Figure f = fd.findFigureInReverseOrder(6, 6);
        assertSame("should find first figure", f1, f);
        f = fd.findFigureInReverseOrder(11, 11);
        assertSame("should find second figure", f2, f);
        f = fd.findFigureInReverseOrder(11, 5);
        assertNull("shouldn't find any figure", f);

        f1.moveBy(10, 10);
        assertEquals(new Point(15, 15), f1.getCenter());
        assertSame(f2, fd.findFigureInReverseOrder(15,15));
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.01.01 2:16:15)
     */
    public void testGetUserBoundsRect() {
        assertEquals(new java.awt.Rectangle(0, 0, 20, 20), fd.getUserBoundsRect());

        MockFigureDrawingListener mockListener = new MockFigureDrawingListener();
        mockListener.expDim.addExpected(fd.makeDrawingDimension(25, 25));
        mockListener.dimChangedCnt.setExpected(1);
        fd.addDrawingChangedListener(mockListener);

        f1.moveBy(-5, -5);
        assertEquals(new java.awt.Rectangle(-5, -5, 25, 25), fd.getUserBoundsRect());
        mockListener.verify();

        mockListener.reset();
        mockListener.dimChangedCnt.setExpected(1);
        mockListener.expDim.addExpected(fd.makeDrawingDimension(20, 30));

        f2.moveBy(-5, 5);
        assertEquals(new java.awt.Rectangle(-5, -5, 20, 30), fd.getUserBoundsRect());
        mockListener.verify();


        mockListener.reset();
        mockListener.dimChangedCnt.setExpected(1);
        mockListener.expDim.addExpected(fd.makeDrawingDimension(15, 25));

        f2.moveBy(-5, -5);
        assertEquals(new java.awt.Rectangle(-5, -5, 15, 25), fd.getUserBoundsRect());
        mockListener.verify();
    }

    public void testUserBoundRectWithNonZeroFiguresDisplacement() {
        f1.moveBy(40, 40);
        f2.moveBy(40, 40);
        assertEquals(new Rectangle(40, 40, 20, 20), fd.getUserBoundsRect());
    }

    /**
     * Insert the method's description here.
     * Creation date: (20.01.01 0:16:06)
     */
    public void testLayOnBorder() {
        assertEquals(new java.awt.Rectangle(0, 0, 20, 20), fd.getUserBoundsRect());
        assertTrue(fd.layOnBorder(new java.awt.Rectangle(0, 0, 1, 1)));
        assertTrue(fd.layOnBorder(new java.awt.Rectangle(0, 1, 1, 1)));
        assertTrue(fd.layOnBorder(new java.awt.Rectangle(1, 0, 1, 1)));
        assertTrue(fd.layOnBorder(new java.awt.Rectangle(1, 19, 1, 1)));
        assertTrue(fd.layOnBorder(new java.awt.Rectangle(19, 18, 1, 1)));
        assertEquals(false, fd.layOnBorder(new java.awt.Rectangle(5, 5, 5, 5)));
        assertEquals(false, fd.layOnBorder(new java.awt.Rectangle(18, 18, 1, 1)));
    }

    public void testClear(){
        assertEquals(new java.awt.Rectangle(0, 0, 20, 20), fd.getUserBoundsRect());
        assertEquals(fd.makeDrawingDimension(20, 20), fd.getDimension());

        fd.clear();
        assertEquals(new Rectangle(), fd.getUserBoundsRect());

    }
}