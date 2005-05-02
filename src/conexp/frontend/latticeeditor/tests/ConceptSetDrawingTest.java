package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 5/4/2005
 * Time: 22:15:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConceptSetDrawingTest extends TestCase {

    protected abstract ConceptSetDrawing getDrawing();

    public void testGetPainterOptionsType() {
        assertTrue(getDrawing().getPainterOptions() instanceof LatticePainterOptions);
    }
}
