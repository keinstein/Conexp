/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;


public class BoundedIntValue extends conexp.util.valuemodels.VetoableValueModelDecorator implements IIntValueModel {

    public final int minVal;
    public final int maxVal;

    private IIntValueModel getIntValueModel() {
        return (IIntValueModel) valueModel;
    }


    private BoundedIntValue(IIntValueModel valueModel, int minVal, int maxVal) throws
            IllegalArgumentException {
        super(valueModel);
        if (minVal > maxVal) {
            throw new IllegalArgumentException("minValue=[" + minVal + "] should be less then maxValue=[" + maxVal + "]");
        }
        this.minVal = minVal;
        this.maxVal = maxVal;
        checkValue(getIntValueModel().getValue());
        this.valueModel = valueModel;
    }


    public BoundedIntValue(String propName, int val, int minVal, int maxVal) throws
            IllegalArgumentException {
        this(new IntValueModel(propName, val), minVal, maxVal);
    }


    public void setValue(int newValue) throws java.beans.PropertyVetoException {
        try {
            checkValue(newValue);
        } catch (IllegalArgumentException e) {
            throw new java.beans.PropertyVetoException(e.getMessage(), null);
        }
        getIntValueModel().setValue(newValue);
    }

    private void checkValue(int newValue) {
        if (newValue < minVal) {
            throw new IllegalArgumentException(
                    valueModel.getPropertyName() + ": should be not less then " + minVal);
        }
        if (newValue > maxVal) {
            throw new IllegalArgumentException(
                    valueModel.getPropertyName() + ": should be not greater then " + maxVal);
        }
    }

    public int maxCharsLength() {
        return Math.max(Integer.toString(minVal).length(), Integer.toString(maxVal).length());
    }

    public String[] makeStringArrayOfValueDescription() {
        int cnt = maxVal - minVal + 1;
        String[] description = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            description[i] = "" + (minVal + i);
        }
        return description;
    }

    public int getIndexOfValue() {
        return getIntValueModel().getValue() - minVal;
    }

    public void setIndexOfValue(int index) throws java.beans.PropertyVetoException {
        setValue(index + minVal);
    }

    public int getValue() {
        return getIntValueModel().getValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoundedIntValue)) {
            return false;
        }

        final BoundedIntValue boundedIntValue = (BoundedIntValue) obj;

        if (maxVal != boundedIntValue.maxVal) {
            return false;
        }
        if (minVal != boundedIntValue.minVal) {
            return false;
        }

        return doEquals(boundedIntValue);
    }

    public int hashCode() {
        int result;
        result = minVal;
        result = 29 * result + maxVal;
        return result;
    }

    public String toString() {
        return "BoundedIntValue{" +
                "minVal=" + minVal +
                ", maxVal=" + maxVal +
                ", value =" + getValue() +
                "}";
    }
}
