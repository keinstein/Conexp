/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor;

import java.awt.event.ActionListener;


public class ButtonParamInfo extends AbstractParamInfo {
    private final java.lang.String buttonLabel;
    private static util.gui.celleditors.JButtonRenderer sButtonRenderer = new util.gui.celleditors.JButtonRenderer();
    private final java.awt.event.ActionListener buttonAction;

    /**
     * ButtonParamInfo constructor comment.
     *
     * @param label java.lang.String
     */
    public ButtonParamInfo(String label, String buttonText, ActionListener actionListener) {
        super(label);
        buttonLabel = buttonText;
        buttonAction = actionListener;
    }


    /**
     * Insert the method's description here.
     * Creation date: (17.06.01 0:53:05)
     *
     * @return javax.swing.table.TableCellRenderer
     */
    public javax.swing.table.TableCellRenderer getParamRenderer() {
        return sButtonRenderer;
    }


    /**
     * Insert the method's description here.
     * Creation date: (17.06.01 0:53:05)
     *
     * @return java.lang.Object
     */
    public Object getValue() {
        return buttonLabel;
    }


    /**
     * Insert the method's description here.
     * Creation date: (17.06.01 0:53:05)
     *
     * @return javax.swing.table.TableCellEditor
     */
    protected javax.swing.table.TableCellEditor makeEditor() {
        return new util.gui.celleditors.JButtonEditor(buttonAction);
    }
}
