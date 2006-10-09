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
import conexp.frontend.latticeeditor.Highlighter;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import junit.framework.TestCase;

public class LatticeCanvasDrawStrategiesContextTest extends TestCase {
    private DrawStrategiesContext drawStrategiesContext;

    protected void setUp() {
        DrawStrategiesModelsFactory factory = new DefaultDrawStrategiesModelsFactory(BasicDrawParams.getInstance());
        drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(factory, null);
    }

    public void testMakeCopy() {
        DrawStrategiesContext other = drawStrategiesContext.makeCopy(null);
        assertNotNull(other);
        assertEquals("draw strategies should be equal", drawStrategiesContext, other);
    }

    public void testHighlighter(){
        Highlighter highlighter = drawStrategiesContext.getHighlighter();
        assertNotNull(highlighter);
        assertTrue(highlighter instanceof Highlighter);
        assertTrue(highlighter.hasConceptHighlighStrategy());
    }
}
