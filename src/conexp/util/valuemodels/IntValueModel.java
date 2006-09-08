/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

import java.beans.PropertyVetoException;


public class IntValueModel extends conexp.util.valuemodels.VetoableValueModelBase implements IIntValueModel {
    private int value;

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     *
     * @return int
     */
    public int getValue() {
        return value;
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     *
     * @param newValue int
     */
    public void setValue(int newValue) throws java.beans.PropertyVetoException {
        if (newValue == value) {
            return;
        }
        doSetValue(newValue);
    }

    /**
     * BoundedIntValue constructor comment.
     */
    public IntValueModel(String propName, int val) throws
            IllegalArgumentException {
        super(propName);
        this.value = val;
    }


    /**
     * Insert the method's description here.
     * Creation date: (18.04.01 22:52:04)
     *
     * @param newValue int
     */
    private void doSetValue(int newValue) throws PropertyVetoException {
        getVetoPropertyChange().fireVetoableChange(propertyName, value, newValue);
        int oldValue = value;
        value = newValue;
        getPropertyChange().firePropertyChange(propertyName, oldValue, value);
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntValueModel)) {
            return false;
        }

        final IntValueModel intValueModel = (IntValueModel) obj;

        if (value != intValueModel.value) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntValueModel{" +
                "value=" + value +
                "}";
    }
}
