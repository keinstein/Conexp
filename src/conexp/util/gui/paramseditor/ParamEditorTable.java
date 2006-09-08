/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import util.gui.JTableX;

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
     *
     * @return conexp.util.gui.paramseditor.ParamsTableModel
     */
    public ParamsTableModel getParamsModel() {
        return (ParamsTableModel) getModel();
    }


}
