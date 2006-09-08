package conexp.util.gui.paramseditor;

import conexp.util.valuemodels.BooleanValueModel;
import util.BooleanUtil;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 28/7/2003
 * Time: 16:03:19
 */

public class BooleanParamInfo extends AbstractParamInfo {

    private BooleanValueModel valueModel;

    public BooleanParamInfo(String label, BooleanValueModel valueModel) {
        super(label);
        this.valueModel = valueModel;
    }

    protected TableCellEditor makeEditor() {
        JCheckBox editor = new JCheckBox();
        editor.setHorizontalAlignment(JLabel.CENTER);
        editor.setSelected(valueModel.getValue());
        editor.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                valueModel.setValue(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        return new DefaultCellEditor(editor);
    }

    public TableCellRenderer getParamRenderer() {
        return new BooleanCellRenderer();
    }

    public Object getValue() {
        return BooleanUtil.valueOf(valueModel.getValue());
    }
}
