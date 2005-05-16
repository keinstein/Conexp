/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components;

import conexp.frontend.EntitiesMask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EntityMaskChangeController implements PropertyChangeListener {
    private LatticeSupplierAndCalculator latticeComponent;

    public EntityMaskChangeController(LatticeSupplierAndCalculator latticeComponent) {
        this.latticeComponent = latticeComponent;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (EntitiesMask.ENTITIES_SELECTION_CHANGED.equals(evt.getPropertyName())) {
            latticeComponent.calculateAndLayoutPartialLattice();
        }
    }
}
