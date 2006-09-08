/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import junit.framework.TestCase;

public class LatticePainterDrawParamsTest extends TestCase {
    public static void testMakeCopy() throws Exception {
        LatticePainterDrawParams one = new LatticePainterDrawParams();
        one.setGridSizeX(20);
        one.setGridSizeY(30);
        one.setMaxEdgeStroke(5.0f);
        one.setNodeMaxRadius(15);
        one.setShowCollisions(false);
        one.setDrawConceptNo(true);
        DrawParameters two = one.makeCopy();
        assertEquals(one, two);
    }
}
