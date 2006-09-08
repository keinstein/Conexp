/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import canvas.CanvasScheme;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.TestCase;

import java.awt.image.BufferedImage;


public class LatticePainterOptionsTest extends TestCase {
    private LatticePainterOptions latticePainterOptions;

    protected void setUp() {
        latticePainterOptions = new LatticePainterOptions(new LatticePainterDrawParams());
    }

    public void testSmallGridSize() {
        latticePainterOptions.getSmallGridSize();
    }

    public void testGetLabelsFont() {
        latticePainterOptions.getLabelsFont(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB).getGraphics());
    }


    public void testMakeCopy() {
        CanvasScheme other = latticePainterOptions.makeCopy();
        assertTrue(latticePainterOptions.isEqualByContent((LatticePainterOptions) other));
    }
}
