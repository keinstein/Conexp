package canvas.tests;

import com.mockobjects.ExpectationCounter;
import com.mockobjects.ExpectationList;
import com.mockobjects.Verifiable;

/**
 * Insert the type's description here.
 * Creation date: (15.01.01 23:24:13)
 * @author
 */
public class MockFigureDrawingListener implements Verifiable, canvas.FigureDrawingListener {
    public ExpectationCounter dimChangedCnt = new ExpectationCounter("Dimension changed");
    public ExpectationList expDim = new ExpectationList("Expected dimensions");

    /**
     * contextStructureChanged method comment.
     */
    public void dimensionChanged(java.awt.Dimension newDim) {
        expDim.addActual(newDim);
        dimChangedCnt.inc();
    }

    /**
     * needUpdate method comment.
     */
    public void needUpdate() {
    }

    /**
     * Insert the method's description here.
     * Creation date: (15.01.01 23:53:27)
     */
    public void reset() {
        expDim = new ExpectationList("Expected dimensions");
    }

    /**
     * Insert the method's description here.
     * Creation date: (15.01.01 23:30:27)
     */
    public void verify() {
        expDim.verify();
        dimChangedCnt.verify();
    }
}