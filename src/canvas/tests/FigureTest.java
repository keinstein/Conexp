package canvas.tests;

import canvas.Figure;
import junit.framework.TestCase;

/**
 * Insert the type's description here.
 * Creation date: (23.12.00 16:14:24)
 * @author
 */
public abstract class FigureTest extends TestCase {
    protected Figure f;

    protected abstract Figure makeFigure();

    protected void setUp() {
        f = makeFigure();
    }

    public void testBoundingBox() {
        java.awt.Rectangle rect = new java.awt.Rectangle();
        assertTrue(rect.isEmpty());
        f.boundingBox(rect);
        assertEquals(false, rect.isEmpty());
    }
}