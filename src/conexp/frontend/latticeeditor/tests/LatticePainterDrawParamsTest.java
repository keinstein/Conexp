package conexp.frontend.latticeeditor.tests;

/**
 * User: sergey
 * Date: 16/4/2005
 * Time: 1:51:54
 */

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