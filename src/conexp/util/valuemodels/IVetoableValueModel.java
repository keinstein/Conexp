/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public interface IVetoableValueModel extends IValueModel {
    /**
     * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    void addVetoableChangeListener(VetoableChangeListener listener);

    /**
     * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
     */
    void removeVetoableChangeListener(VetoableChangeListener listener);

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 21:58:41)
     *
     * @param newVetoPropertyChange java.beans.VetoableChangeSupport
     */
    void setVetoPropertyChange(VetoableChangeSupport newVetoPropertyChange);
}
