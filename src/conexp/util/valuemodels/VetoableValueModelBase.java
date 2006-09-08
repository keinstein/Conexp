/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;


public class VetoableValueModelBase extends ValueModelBase implements IVetoableValueModel {
    private transient VetoableChangeSupport vetoPropertyChange;

    /**
     * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    public synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
        getVetoPropertyChange().addVetoableChangeListener(listener);
    }


    /**
     * Accessor for the vetoPropertyChange field.
     */
    protected VetoableChangeSupport getVetoPropertyChange() {
        if (vetoPropertyChange == null) {
            vetoPropertyChange = new VetoableChangeSupport(this);
        }
        ;
        return vetoPropertyChange;
    }

    /**
     * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    public synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
        getVetoPropertyChange().removeVetoableChangeListener(listener);
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 21:58:41)
     *
     * @param newVetoPropertyChange java.beans.VetoableChangeSupport
     */
    public void setVetoPropertyChange(VetoableChangeSupport newVetoPropertyChange) {
        vetoPropertyChange = newVetoPropertyChange;
    }


    /**
     * BoundedIntValue constructor comment.
     */
    protected VetoableValueModelBase(String propName) throws
            IllegalArgumentException {
        super(propName);
    }

}
