package conexp.util.gui.paramseditor;

import util.gui.JTableX;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class ParamEditorTable extends JTableX {
    /**
     * ParamEditorTable constructor comment.
     */
    public ParamEditorTable() {
        super(new ParamsTableModel());
        setCellEditorMapper(getParamsModel());
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        getTableHeader().setReorderingAllowed(false);
        setBackground(javax.swing.UIManager.getColor("control"));
    }

    /**
     * Insert the method's description here.
     * Creation date: (26.03.01 22:41:50)
     * @return conexp.util.gui.paramseditor.ParamsTableModel
     */
    public ParamsTableModel getParamsModel() {
        return (ParamsTableModel) getModel();
    }


    public static boolean canGetGoodTip(Object tipProvider) {
        if (null == tipProvider) {
            return false;
        }
        String tip = tipProvider.toString();
        if (null == tip) {
            return false;
        }
        if ("".equals(tip.trim())) {
            return false;
        }
        return true;
    }

    public java.awt.Point getToolTipLocation(java.awt.event.MouseEvent event) {
        int row = rowAtPoint(event.getPoint());
        int col = columnAtPoint(event.getPoint());

        Object o = getValueAt(row, col);
        if (!canGetGoodTip(o)) {
            return null;
        }

        java.awt.Point pt = getCellRect(row, col, true).getLocation();
        pt.translate(-1, -2);
        return pt;
    }

    public String getToolTipText(MouseEvent event) {
        Point p = event.getPoint();

        // Locate the renderer under the event location
        int col = columnAtPoint(p);
        int row = rowAtPoint(p);

        Object o = getValueAt(row, col);
        if (!canGetGoodTip(o)) {
            return null;
        }

        return o.toString().trim();
    }
}