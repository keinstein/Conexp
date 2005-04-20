package conexp.frontend.latticeeditor.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 16/4/2005
 * Time: 1:51:54
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.*;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.DrawParameters;

public class LatticePainterDrawParamsTest extends TestCase {
    LatticePainterDrawParams latticePainterDrawParams;

    public void testMakeCopy() throws Exception {
        LatticePainterDrawParams one = new LatticePainterDrawParams();
        DrawParameters two = one.makeCopy();
        assertEquals(one, two);
    }
}