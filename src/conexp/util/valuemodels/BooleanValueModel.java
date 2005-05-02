package conexp.util.valuemodels;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 28/7/2003
 * Time: 16:06:47
 */

public class BooleanValueModel extends ValueModelBase {

    private boolean value;

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

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BooleanValueModel)) return false;

        final BooleanValueModel other = (BooleanValueModel) obj;

        if(!propertyName.equals(other.propertyName)){
            return false;
        }
        if (value != other.value){
            return false;
        }

        return true;
    }

    public int hashCode() {
        return 29*propertyName.hashCode()+(value ? 1 : 0);
    }

    public String toString() {
        return "BooleanValueModel{" +
                "["+propertyName+"]"+
                "value=" + value +
                "}";
    }
}
