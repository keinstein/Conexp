/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class LatticePainterOptionsTest extends TestCase {
    private static final Class THIS = LatticePainterOptionsTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testSmallGridSize() {
        (new conexp.frontend.latticeeditor.LatticePainterOptions()).getSmallGridSize();
    }
}
