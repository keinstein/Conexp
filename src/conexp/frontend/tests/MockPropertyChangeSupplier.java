/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.tests;

import util.PropertyChangeSupplier;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MockPropertyChangeSupplier implements PropertyChangeSupplier {
    private PropertyChangeSupport support;

    private synchronized PropertyChangeSupport getSupport() {
        if (null == support) {
            support = new PropertyChangeSupport(this);
        }
        return support;
    }

    public boolean hasListeners(String propertyName) {
        return getSupport().hasListeners(propertyName);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        getSupport().removePropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSupport().removePropertyChangeListener(propertyName, listener);
    }

    public synchronized void firePropertyChange(String propertyName) {
        getSupport().firePropertyChange(makePropertyChangeEvent(propertyName));
    }

    public PropertyChangeEvent makePropertyChangeEvent(String propertyName) {
        return new PropertyChangeEvent(this, propertyName, null, null);
    }

}
