/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

public interface IValueModel {
    /**
     * The addPropertyChangeListener method was generated to support the propertyChange field.
     */
    void addPropertyChangeListener(java.beans.PropertyChangeListener listener);

    /**
     * The removePropertyChangeListener method was generated to support the propertyChange field.
     */
    void removePropertyChangeListener(java.beans.PropertyChangeListener listener);

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 21:57:17)
     *
     * @param newPropertyChange java.beans.PropertyChangeSupport
     */
    void setPropertyChange(java.beans.PropertyChangeSupport newPropertyChange);

    String getPropertyName();
}
