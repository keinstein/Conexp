package conexp.frontend.latticeeditor.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import junit.framework.Assert;
import util.gui.GraphicObjectsFactory;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class TestHelper{
    public static LatticeComponent makeTestableLatticeComponent(Context cxt) {
        LatticeComponent component = new LatticeComponent(cxt);
        component.setLayoutEngine(new SimpleLayoutEngine());
        return component;
    }

    public static void checkCoordsForIntent(double x, double y, LatticeDrawing drawing, Lattice lattice, int[] intent) {
        Assert.assertEquals(GraphicObjectsFactory.makePoint2D(x, y),  drawing.getFigureForConcept(lattice.findConceptWithIntent(SetBuilder.makeSet(intent))).getCenter());
    }

}
