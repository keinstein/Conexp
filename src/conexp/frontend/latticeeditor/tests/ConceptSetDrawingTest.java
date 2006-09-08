/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.TestCase;


public abstract class ConceptSetDrawingTest extends TestCase {

    protected abstract ConceptSetDrawing getDrawing();

    public void testGetPainterOptionsType() {
        assertTrue(getDrawing().getPainterOptions() instanceof LatticePainterOptions);
    }

}
