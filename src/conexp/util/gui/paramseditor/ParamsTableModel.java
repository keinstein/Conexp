/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ParamsTableModel extends AbstractTableModel implements util.gui.TableCellEditorMapper {
    private ArrayList params = new ArrayList();

    /**
     * ParamsTableModel constructor comment.
     */
    public ParamsTableModel() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (26.03.01 21:57:42)
     *
     * @param param conexp.util.gui.paramseditor.ParamInfo[]
     */
    public void addParam(ParamInfo param) {
        int rowsBefore = params.size();
        params.add(param);
        fireTableRowsInserted(rowsBefore, rowsBefore + 1);
    }

    /**
     * Insert the method's description here.
     * Creation date: (26.03.01 21:57:42)
     *
     * @param paramArr conexp.util.gui.paramseditor.ParamInfo[]
     */
    public void addParams(ParamInfo[] paramArr) {
        int rowsBefore = params.size();
        for (int i = 0; i < paramArr.length; i++) {
            params.add(paramArr[i]);
        }
        fireTableRowsInserted(rowsBefore, rowsBefore + paramArr.length);
    }

    public void clear() {
        params.clear();
        fireTableStructureChanged();
    }

    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 22:12:15)
     *
     * @param row int
     * @param col int
     * @return javax.swing.table.TableCellEditor
     */
    public javax.swing.table.TableCellEditor getCellEditor(javax.swing.JTable table, int row, int col) {
        if (col == 1) {
            return getParamInfo(row).getTableParamEditor();
        }
        return null;
    }

    public javax.swing.table.TableCellRenderer getCellRenderer(javax.swing.JTable tab, int row, int col) {
        if (col != 1) {
            return null;
        }
        return getParamInfo(row).getParamRenderer();
    }

    public Class getColumnClass(int colIndex) {
        if (0 == colIndex) {
            return String.class;
        }
        return Object.class;
    }

    public int getColumnCount() {
        return 2;
    }

    private ParamInfo getParamInfo(int row) {
        return (ParamInfo) params.get(row);
    }

    public int getRowCount() {
        return params.size();
    }

    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return getParamInfo(row).getLabel();
            case 1:
                return getParamInfo(row).getValue();
            default:
                return null;
        }
    }

    public boolean isCellEditable(int rowIndex, int colIndex) {
        if (0 == colIndex) {
            return false;
        }
        return true;
    }

    public void setParams(ParamInfo[] paramArr) {
        clear();
        addParams(paramArr);
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Parameter";
            case 1:
                return "Value";
            default:
                return super.getColumnName(column);
        }
    }
}
