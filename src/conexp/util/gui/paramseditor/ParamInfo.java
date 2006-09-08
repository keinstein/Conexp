/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;


public interface ParamInfo {
    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 23:25:09)
     *
     * @return java.lang.String
     */
    String getLabel();

    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 23:25:52)
     *
     * @return javax.swing.table.TableCellEditor
     */
    javax.swing.table.TableCellEditor getTableParamEditor();

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 23:35:15)
     *
     * @return javax.swing.table.TableCellRenderer
     */
    javax.swing.table.TableCellRenderer getParamRenderer();

    javax.swing.JComponent makeUsualParamEditor();

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:03:56)
     *
     * @return java.lang.Object
     */
    Object getValue();
}
