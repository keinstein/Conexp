/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

import util.StringUtil;


public class ValueModelBase implements IValueModel {

    private transient java.beans.PropertyChangeSupport propertyChange;

    protected final String propertyName;

    protected ValueModelBase(String propName) throws IllegalArgumentException {
        propName = StringUtil.safeTrim(propName);
        if ("".equals(propName)) {
            throw new IllegalArgumentException("Property name can't be null or empty");
        }
        this.propertyName = propName;
    }


    /**
     * The addPropertyChangeListener method was generated to support the propertyChange field.
     */
    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        getPropertyChange().addPropertyChangeListener(listener);
    }


    /**
     * Accessor for the propertyChange field.
     */
    protected java.beans.PropertyChangeSupport getPropertyChange() {
        if (propertyChange == null) {
            propertyChange = new java.beans.PropertyChangeSupport(this);
        }
        ;
        return propertyChange;
    }


    /**
     * The hasListeners method was generated to support the propertyChange field.
     */
    public synchronized boolean hasListeners(String propertyName) {
        return getPropertyChange().hasListeners(propertyName);
    }


    /**
     * The removePropertyChangeListener method was generated to support the propertyChange field.
     */
    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        getPropertyChange().removePropertyChangeListener(listener);
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 21:57:17)
     *
     * @param newPropertyChange java.beans.PropertyChangeSupport
     */
    public void setPropertyChange(java.beans.PropertyChangeSupport newPropertyChange) {
        propertyChange = newPropertyChange;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
