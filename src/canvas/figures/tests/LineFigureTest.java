package canvas.figures.tests;

import canvas.figures.ConnectionFigure;
import canvas.figures.LineFigure;
import canvas.tests.FigureTest;
import canvas.tests.MockFigure;
import junit.framework.Test;
import junit.framework.TestSuite;

public class LineFigureTest extends FigureTest {

    protected canvas.Figure makeFigure() {
        MockFigure start = new MockFigure(10, 10);
        MockFigure end = new MockFigure(20, 30);
        return new LineFigure(start, end);
    }

    public static Test suite() {
        return new TestSuite(LineFigureTest.class);
    }

    public void testNegativeSizes() {
        ConnectionFigure lf = (ConnectionFigure) f;
        lf.getStartFigure().setCoords(30, 30);
        lf.getEndFigure().setCoords(10, 10);
        testBoundingBox();
    }

    public void testContains(){
        //todo: write test
    }
}