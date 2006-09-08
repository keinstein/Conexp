/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui;

import conexp.util.valuemodels.IIntValueModel;
import util.gui.fields.WholeNumberField;


public class IntValueWholeNumberField extends WholeNumberField {
    private final IIntValueModel valueModel;

    public void updateValue() throws java.beans.PropertyVetoException {
        valueModel.setValue(getValue());
    }

    public IntValueWholeNumberField(IIntValueModel value, int columns) {
        super(value.getValue(), columns);
        this.valueModel = value;
    }
}
