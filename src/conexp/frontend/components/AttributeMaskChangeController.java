/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.frontend.EntitiesMask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttributeMaskChangeController implements PropertyChangeListener {
    private LatticeComponent latticeComponent;

    public AttributeMaskChangeController(LatticeComponent latticeComponent) {
        this.latticeComponent = latticeComponent;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (EntitiesMask.ENTITIES_SELECTION_CHANGED.equals(evt.getPropertyName())) {
            latticeComponent.calculateAndLayoutPartialLattice();
        }
    }
}
