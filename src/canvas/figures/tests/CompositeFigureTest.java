package canvas.figures.tests;

import canvas.figures.CompositeFigure;
import canvas.tests.MockFigure;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CompositeFigureTest extends TestCase {
    private static final Class THIS = CompositeFigureTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testFindFigureInside(){
        CompositeFigure figure = new CompositeFigure();
        MockFigure firstInner = new MockFigure(10, 10);
        figure.addFigure(firstInner);

        MockFigure secondInner = new MockFigure(10, 10);
        figure.addFigure(secondInner);

        assertTrue(figure.contains(10, 10));
        assertSame(secondInner, figure.findFigureInside(10, 10));
    }
}