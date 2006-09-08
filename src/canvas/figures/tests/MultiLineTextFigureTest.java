/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas.figures.tests;



import canvas.figures.MultiLineTextFigure;
import junit.framework.TestCase;

import java.awt.Font;
import java.awt.font.FontRenderContext;

public class MultiLineTextFigureTest extends TestCase {
    MultiLineTextFigure figure;
    //todo:sye - remove duplication in font render context creation
    public static final FontRenderContext DEFAULT_FONTRENDERCONTEXT = new FontRenderContext(null, true, false);

    protected void setUp() throws Exception {
        super.setUp();
        figure = new MultiLineTextFigure();
    }


    public void testNewSize() {
        assertTrue(figure.isContentDirty());
        figure.newSize(DEFAULT_FONTRENDERCONTEXT);
        assertFalse("recalculation size should clear the content dirty flag",
                figure.isContentDirty());
    }


    public void testSettingTextMakesContentDirty() {
        madeContentClear();
        assertFalse(figure.isContentDirty());
        figure.setText("Abc");
        assertTrue("Setting text should make the content dirty", figure.isContentDirty());
    }

    private void madeContentClear() {
        figure.newSize(DEFAULT_FONTRENDERCONTEXT);
    }

    public void testSettingFontMakesContentDirty() {
        madeContentClear();
        assertFalse(figure.isContentDirty());
        figure.setFont(new Font("dialog", Font.PLAIN, 14));
        assertTrue("Setting font should make content of the figure dirty", figure.isContentDirty());
    }
}
