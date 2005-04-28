package conexp.frontend.latticeeditor.tests;

/**
 * User: sergey
 * Date: 17/4/2005
 * Time: 13:23:29
 *
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DrawStrategiesContext;
import conexp.frontend.latticeeditor.DrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.LatticeCanvasDrawStrategiesContext;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import junit.framework.TestCase;

public class LatticeCanvasDrawStrategiesContextTest extends TestCase {
    DrawStrategiesContext drawStrategiesContext;

    public void testMakeCopy() {

        DrawStrategiesModelsFactory factory = new DefaultDrawStrategiesModelsFactory(BasicDrawParams.getInstance());
        drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(factory, null);
        DrawStrategiesContext other = drawStrategiesContext.makeCopy(null);
        assertNotNull(other);
        assertEquals("draw strategies should be equal",drawStrategiesContext,other);

    }
}