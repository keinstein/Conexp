/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;


import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;

public abstract class AbstractParamInfo implements ParamInfo {
    private final java.lang.String label;

    private TableCellEditor editor;

    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 23:29:48)
     *
     * @param label java.lang.String
     */
    protected AbstractParamInfo(String label) {
        this.label = label;
    }

    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 23:27:48)
     *
     * @return java.lang.String
     */
    public String getLabel() {
        return label;
    }

    /**
     * Insert the method's description here.
     * Creation date: (28.03.01 23:27:48)
     *
     * @return javax.swing.table.TableCellEditor
     */
    public javax.swing.table.TableCellEditor getTableParamEditor() {
        if (null == editor) {
            editor = makeEditor();
        }
        return editor;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29.03.01 0:00:08)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected abstract TableCellEditor makeEditor();

    public JComponent makeUsualParamEditor() {
        util.Assert.isTrue(false, "Not supported yet");
        return null;
    }


}
