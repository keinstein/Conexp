/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;


public class BoundedDoubleValue extends conexp.util.valuemodels.ValueModelBase {
    private double value;
    public final double minVal;
    public final double maxVal;


    private int resolution = 100;


    /**
     * BoundedIntValue constructor comment.
     */
    public BoundedDoubleValue(String propName, double val, double minVal, double maxVal, int resolution) throws
            IllegalArgumentException {
        super(propName);
        if (minVal > maxVal) {
            throw new IllegalArgumentException("minValue=[" + minVal + "] should be less then maxValue=[" + maxVal + "]");
        }
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.value = ensureInRange(val);
        this.resolution = resolution;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:32:00)
     */
    private double ensureInRange(double val) {
        if (val < minVal) {
            val = minVal;
        }
        if (val > maxVal) {
            val = maxVal;
        }
        return val;
    }

    /**
     * Insert the method's description here.
     * Creation date: (31.03.01 0:00:58)
     *
     * @return int
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     *
     * @return int
     */
    public double getValue() {
        return value;
    }

    /**
     * Insert the method's description here.
     * Creation date: (31.03.01 0:00:58)
     *
     * @param newResolution int
     */
    public void setResolution(int newResolution) {
        if (newResolution > 0) {
            resolution = newResolution;
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     *
     * @param newValue int
     */
    public void setValue(double newValue) {
        if (newValue == value) {
            return;
        }
        newValue = ensureInRange(newValue);

        Double oldValueObj = new Double(value), newValueObj = new Double(newValue);
        value = newValue;
        getPropertyChange().firePropertyChange(propertyName, oldValueObj, newValueObj);
    }

    /**
     * Insert the method's description here.
     * Creation date: (31.03.01 0:08:41)
     *
     * @return double
     */
    public double tickVal() {
        return (maxVal - minVal) / resolution;
    }
}
