package conexp.frontend.latticeeditor.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.layout.MinIntersectionLayouterProvider;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.DrawStrategiesContext;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.Assert;
import util.gui.GraphicObjectsFactory;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class TestHelper {
    public static LatticeComponent makeTestableLatticeComponent(Context cxt) {
        Preferences preferences = Preferences.userNodeForPackage(TestHelper.class);
        try {
            preferences.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        LatticeComponent component = new LatticeComponent(cxt);
        LatticePainterOptions painterOptions = component.getDrawing().getPainterOptions();
        painterOptions.setPreferences(preferences);
        painterOptions.doStorePreferences();
        DrawStrategiesContext drawStrategiesContext = painterOptions.getDrawStrategiesContext();
        drawStrategiesContext.setPreferences(preferences);
        drawStrategiesContext.doStorePreferences();
        LatticePainterDrawParams editableDrawingOptions = component.getDrawing().getLatticeDrawingOptions().getEditableDrawingOptions();
        editableDrawingOptions.setPreferences(preferences);
        editableDrawingOptions.doStorePreferences();
        component.setLayouterProvider(new MinIntersectionLayouterProvider());
        component.setLayoutEngine(new SimpleLayoutEngine());
        //setting default (not user dependent) preferences for drawing




        return component;
    }

    public static void checkCoordsForIntent(double x, double y, LatticeDrawing drawing, Lattice lattice, int[] intent) {
        Assert.assertEquals(GraphicObjectsFactory.makePoint2D(x, y), drawing.getFigureForConcept(lattice.findConceptWithIntent(SetBuilder.makeSet(intent))).getCenter());
    }

    /**
     * @param preferences
     * @todo move to the utilities package
     */
    public static void printOutPreferences(Preferences preferences) {
        try {
            String[] keys = preferences.keys();

            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                System.out.println(key + " " + preferences.get(key, "EMPTY_VALUE"));
            }
            preferences.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

}
