/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.TestCase;


public class LatticePainterOptionsTest extends TestCase {


    public void testSmallGridSize() {
        (new LatticePainterOptions(new LatticePainterDrawParams())).getSmallGridSize();
    }
}
