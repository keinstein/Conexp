/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DrawStrategiesContext;
import conexp.frontend.latticeeditor.DrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.LatticeCanvasDrawStrategiesContext;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import junit.framework.TestCase;

public class LatticeCanvasDrawStrategiesContextTest extends TestCase {
    private DrawStrategiesContext drawStrategiesContext;

    public void testMakeCopy() {

        DrawStrategiesModelsFactory factory = new DefaultDrawStrategiesModelsFactory(BasicDrawParams.getInstance());
        drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(factory, null);
        DrawStrategiesContext other = drawStrategiesContext.makeCopy(null);
        assertNotNull(other);
        assertEquals("draw strategies should be equal", drawStrategiesContext, other);

    }
}
