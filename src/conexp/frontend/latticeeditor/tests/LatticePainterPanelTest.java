/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class LatticePainterPanelTest extends junit.framework.TestCase {
    LatticePainterPanel pan;

    static class MockLatticeSupplier implements LatticeDrawingProvider {
        public MockLatticeSupplier() {

        }

        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        public PropertyChangeSupport getPropertyChangeSupport() {
            return propertyChangeSupport;
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            getPropertyChangeSupport().addPropertyChangeListener(listener);
        }

        public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
            getPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            getPropertyChangeSupport().removePropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
            getPropertyChangeSupport().removePropertyChangeListener(propertyName, listener);
        }

        Lattice lattice;

        public void setLattice(Lattice lattice) {
            this.lattice = lattice;
        }

        public Lattice getLattice() {
            return lattice;
        }

        LatticeDrawing drawing = new LatticeDrawing();

        public LatticeDrawing getDrawing() {
            return drawing;
        }
    }

    MockLatticeSupplier supplier;

    protected void setUp() {
        supplier = new MockLatticeSupplier();
        supplier.setLattice(SetBuilder.makeLattice(new int[][]{{0}}));
        pan = new LatticePainterPanel(supplier);
    }

    public static Test suite() {
        return new TestSuite(LatticePainterPanelTest.class);
    }

    public void testPaint() {
        pan.getScreenImage();
    }

    public void testInitPaint() {
        try {
            pan.initPaint();
        } catch (Throwable t) {
            fail("failed to	initPaint");
        }

    }

    public void testSetLatticeDrawing() {
        try {
            LatticeDrawing drawing = new LatticeDrawing();
            drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{0}}));
            pan.setConceptSetDrawing(drawing);
        } catch (Throwable t) {
            fail("failed to	execute setLattice");
        }
    }


    public void testToolbarInResources() {
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(pan.getResources(), pan.getActionChain());
    }
}
