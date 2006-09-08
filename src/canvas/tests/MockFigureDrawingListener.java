/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.FigureDrawingListener;
import com.mockobjects.ExpectationCounter;
import com.mockobjects.ExpectationList;
import com.mockobjects.Verifiable;

import java.awt.Dimension;


public class MockFigureDrawingListener implements Verifiable, FigureDrawingListener {
    public ExpectationCounter dimChangedCnt = new ExpectationCounter("Dimension changed");
    public ExpectationList expDim = new ExpectationList("Expected dimensions");

    /**
     * contextStructureChanged method comment.
     */
    public void dimensionChanged(Dimension newDim) {
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
