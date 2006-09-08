/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import conexp.util.gui.strategymodel.StrategyValueItem;

import javax.swing.JComboBox;

public class StrategyValueItemParamInfo extends AbstractParamInfo {
    private final StrategyValueItem valueModel;

    /**
     * StrategyValueItemParamInfo constructor comment.
     *
     * @param label java.lang.String
     */
    public StrategyValueItemParamInfo(String label, StrategyValueItem value) {
        super(label);
        valueModel = value;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:35:34)
     *
     * @return javax.swing.table.TableCellRenderer
     */
    public javax.swing.table.TableCellRenderer getParamRenderer() {
        return null;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:35:45)
     *
     * @return java.lang.Object
     */
    public Object getValue() {
        return valueModel.getValueDescription();
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:35:45)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected javax.swing.table.TableCellEditor makeEditor() {
        return new javax.swing.DefaultCellEditor(makeStrategyCombo(valueModel));
    }

    private static JComboBox makeStrategyCombo(StrategyValueItem strategy) {
        JComboBox combo = new JComboBox(strategy.getDescription());
        combo.addActionListener(strategy);
        return combo;
    }

    public void setValue(Object obj) throws IllegalArgumentException {
        if (!(obj instanceof Integer)) {
            throw new IllegalArgumentException("Accept only Integer" + obj);
        }
        int newValue = ((Integer) obj).intValue();
        valueModel.setValue(newValue);
    }

}
