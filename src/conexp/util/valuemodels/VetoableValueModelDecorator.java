/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.util.valuemodels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;


public class VetoableValueModelDecorator implements IVetoableValueModel {

    protected IVetoableValueModel valueModel;


    public VetoableValueModelDecorator(IVetoableValueModel valueModel) {
        this.valueModel = valueModel;
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        valueModel.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        valueModel.removeVetoableChangeListener(listener);
    }

    public void setVetoPropertyChange(VetoableChangeSupport newVetoPropertyChange) {
        valueModel.setVetoPropertyChange(newVetoPropertyChange);
    }

    public String getPropertyName() {
        return valueModel.getPropertyName();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        valueModel.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        valueModel.removePropertyChangeListener(listener);
    }

    public void setPropertyChange(PropertyChangeSupport newPropertyChange) {
        valueModel.setPropertyChange(newPropertyChange);
    }
}
