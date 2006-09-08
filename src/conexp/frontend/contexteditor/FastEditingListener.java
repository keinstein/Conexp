/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.contexteditor;

import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class FastEditingListener implements ActionListener {
    private Boolean value;
    private ContextTable contextTable;

    public FastEditingListener(ContextTable contextTable, Boolean value) {
        this.contextTable = contextTable;
        this.value = value;
    }

    public void actionPerformed(ActionEvent e) {
        int editingRow = contextTable.getEditingRow();
        int editingColumn = contextTable.getEditingColumn();
        int modelColumn = contextTable.convertColumnIndexToModel(editingColumn);
        if (contextTable.getContextTableModel().inCrossArea(editingRow, modelColumn)) {
            contextTable.getContextTableModel().setValueAt(value, editingRow, modelColumn);
            TableCellEditor cellEditor = contextTable.getCellEditor();
            if (cellEditor != null) {
                contextTable.removeEditor();
            }
            int nextColumn = editingColumn + 1;
            int nextRow = editingRow;
            if (nextColumn >= contextTable.getColumnCount()) {
                nextColumn = getFirstRelationColumn();
                nextRow++;
                if (nextRow >= contextTable.getRowCount()) {
                    nextRow = getFirstRelationRow();
                }
            }
            contextTable.changeSelection(nextRow, nextColumn, false, false);
        }
    }

    private static int getFirstRelationRow() {
        return 1;
    }

    private static int getFirstRelationColumn() {
        return 1;
    }
}
