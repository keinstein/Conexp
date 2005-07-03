package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.latticeeditor.LatticeDrawing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * User: sergey
 * Date: 6/5/2005
 * Time: 10:52:18
 */
class MockLatticeDrawingProvider implements LatticeDrawingProvider {
    public MockLatticeDrawingProvider() {

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
