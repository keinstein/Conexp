/*
 * User: Serhiy Yevtushenko
 * Date: May 8, 2002
 * Time: 1:52:12 AM
 */
package conexp.frontend.components;

import conexp.frontend.AttributeMask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttributeMaskChangeController implements PropertyChangeListener{
    private LatticeComponent latticeComponent;

    public AttributeMaskChangeController(LatticeComponent latticeComponent) {
        this.latticeComponent = latticeComponent;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(AttributeMask.ATTRIBUTE_SELECTION_CHANGED.equals(evt.getPropertyName())){
            latticeComponent.calculateAndLayoutPartialLattice();
        }
    }
}
