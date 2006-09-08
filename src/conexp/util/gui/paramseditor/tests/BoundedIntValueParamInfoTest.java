/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor.tests;

import conexp.util.valuemodels.BoundedIntValue;
import junit.framework.TestCase;

public class BoundedIntValueParamInfoTest extends TestCase {

    public static void testEditor() {
        BoundedIntValue val = new BoundedIntValue("test", 10, 0, 80);
        conexp.util.gui.paramseditor.BoundedIntValueParamInfo paramInf = new conexp.util.gui.paramseditor.BoundedIntValueParamInfo("testLabel", val);

        conexp.util.gui.paramseditor.ParamEditorTable table = new conexp.util.gui.paramseditor.ParamEditorTable();
        table.getParamsModel().addParam(paramInf);

        javax.swing.table.TableCellEditor editor = paramInf.getTableParamEditor();
        Object value = paramInf.getValue();
        java.awt.Component comp = editor.getTableCellEditorComponent(table, value, true, 0, 1);
        conexp.util.gui.IntValueWholeNumberField f = (conexp.util.gui.IntValueWholeNumberField) comp;
        assertEquals(10, f.getValue());
    }
}
