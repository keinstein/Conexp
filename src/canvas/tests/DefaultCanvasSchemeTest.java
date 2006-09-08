/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas.tests;



import canvas.CanvasScheme;
import canvas.DefaultCanvasScheme;
import junit.framework.TestCase;
import util.testing.TestUtil;

public class DefaultCanvasSchemeTest extends TestCase {
    DefaultCanvasScheme defaultCanvasScheme;

    protected void setUp() throws Exception {
        defaultCanvasScheme = new DefaultCanvasScheme();
    }

    public void testMakeCopy() {
        CanvasScheme other = defaultCanvasScheme.makeCopy();
        assertEquals(defaultCanvasScheme, other);
    }

    public void testEqualsAndHashCode() {
        DefaultCanvasScheme other = new DefaultCanvasScheme();
        TestUtil.testEqualsAndHashCode(defaultCanvasScheme, other);
    }
}
