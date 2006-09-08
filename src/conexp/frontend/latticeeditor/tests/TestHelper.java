/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import junit.framework.Assert;
import util.gui.GraphicObjectsFactory;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;



public class TestHelper {

    private TestHelper() {
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

    public static LatticePainterPanel makeTestableLatticePainterPanel(final Lattice lattice) {
        MockLatticeDrawingProvider supplier = makeTestableLatticeProvider(lattice);
        final LatticePainterPanel latticePainterPanel = LatticePainterPanel.createLatticePainterPanel(supplier);
        return latticePainterPanel;
    }

    public static MockLatticeDrawingProvider makeTestableLatticeProvider(final Lattice lattice) {
        MockLatticeDrawingProvider supplier = new MockLatticeDrawingProvider();
        supplier.setLattice(lattice);
        return supplier;
    }
}
