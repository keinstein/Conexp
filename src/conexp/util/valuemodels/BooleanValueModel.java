package conexp.util.valuemodels;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 28/7/2003
 * Time: 16:06:47
 */

public class BooleanValueModel extends ValueModelBase {

    boolean value;

    public BooleanValueModel(String propName) {
        this(propName, false);
    }

    public BooleanValueModel(String propName, boolean value) {
        super(propName);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        if (this.value == value) {
            return;
        }
        boolean oldValue = this.value;
        this.value = value;
        getPropertyChange().firePropertyChange(getPropertyName(), oldValue, this.value);
    }

}
