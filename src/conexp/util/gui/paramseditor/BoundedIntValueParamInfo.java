/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import conexp.util.valuemodels.BoundedIntValue;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class BoundedIntValueParamInfo extends IntValueParamInfo {

    private BoundedIntValue getBoundedIntValue() {
        return (BoundedIntValue) valueModel;
    }


    /**
     * BoundedIntValueParamInfo constructor comment.
     *
     * @param label java.lang.String
     */
    public BoundedIntValueParamInfo(String label, BoundedIntValue value) {
        super(label, value);
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:01:26)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected javax.swing.table.TableCellEditor makeEditor() {
        return new IntValueCellEditor(new conexp.util.gui.IntValueWholeNumberField(valueModel, getBoundedIntValue().maxCharsLength()));
    }


    public JComponent makeUsualParamEditor() {
        JComboBox comboBox = new JComboBox(getBoundedIntValue().makeStringArrayOfValueDescription());
        comboBox.setSelectedIndex(getBoundedIntValue().getIndexOfValue());
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox src = (JComboBox) e.getSource();
                try {
                    getBoundedIntValue().setIndexOfValue(src.getSelectedIndex());
                } catch (PropertyVetoException ex) {
                    src.setSelectedIndex(getBoundedIntValue().getIndexOfValue());
                }
            }
        });
        return comboBox;
    }
}
