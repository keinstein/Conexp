/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import conexp.util.valuemodels.IIntValueModel;

public class IntValueParamInfo extends AbstractParamInfo {
    protected final IIntValueModel valueModel;

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:35:43)
     *
     * @return javax.swing.table.TableCellRenderer
     */
    public javax.swing.table.TableCellRenderer getParamRenderer() {
        return null;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:04:11)
     *
     * @return java.lang.Object
     */
    public java.lang.Object getValue() {
        return new Integer(valueModel.getValue());
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:01:26)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected javax.swing.table.TableCellEditor makeEditor() {
        return new IntValueCellEditor(new conexp.util.gui.IntValueWholeNumberField(valueModel, 6));
    }

    /**
     * BoundedIntValueParamInfo constructor comment.
     *
     * @param label java.lang.String
     */
    public IntValueParamInfo(String label, IIntValueModel value) {
        super(label);
        this.valueModel = value;
    }
}
