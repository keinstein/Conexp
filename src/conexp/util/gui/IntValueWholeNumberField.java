package conexp.util.gui;

import conexp.util.valuemodels.IIntValueModel;
import util.gui.fields.WholeNumberField;


/**
 * Insert the type's description here.
 * Creation date: (02.02.01 23:40:25)
 * @author
 */
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