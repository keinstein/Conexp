/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.util.gui;

import conexp.util.valuemodels.IIntValueModel;
import util.gui.fields.WholeNumberField;


public class IntValueWholeNumberField extends WholeNumberField {
    private final IIntValueModel valueModel;

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 21:19:04)
     */
    public void updateValue() throws java.beans.PropertyVetoException {
        valueModel.setValue(getValue());
    }

    /**
     * BoundedWholeNumberField constructor comment.
     * @param value int
     * @param columns int
     */
    public IntValueWholeNumberField(IIntValueModel value, int columns) {
        super(value.getValue(), columns);
        this.valueModel = value;
    }
}
