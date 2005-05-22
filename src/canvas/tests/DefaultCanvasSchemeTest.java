package canvas.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 16/4/2005
 * Time: 0:22:39
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.*;
import canvas.DefaultCanvasScheme;
import canvas.CanvasScheme;
import util.testing.TestUtil;

public class DefaultCanvasSchemeTest extends TestCase {
    DefaultCanvasScheme defaultCanvasScheme;

    protected void setUp() throws Exception {
        defaultCanvasScheme = new DefaultCanvasScheme();
    }

    public void testMakeCopy(){
        CanvasScheme other = defaultCanvasScheme.makeCopy();
        assertEquals(defaultCanvasScheme, other);
    }

    public void testEqualsAndHashCode(){
        DefaultCanvasScheme other = new DefaultCanvasScheme();
        TestUtil.testEqualsAndHashCode(defaultCanvasScheme, other);
    }
}