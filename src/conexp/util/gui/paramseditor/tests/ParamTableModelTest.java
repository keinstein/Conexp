/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor.tests;

import conexp.util.gui.paramseditor.BoundedIntValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsTableModel;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.testing.MockTableModelListener;

public class ParamTableModelTest extends TestCase {
    private static final Class THIS = ParamTableModelTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    private ParamsTableModel tableModel;

    public void testParamLabels() {
        assertEquals(2, tableModel.getColumnCount());
        assertEquals(2, tableModel.getRowCount());
        assertEquals("one", tableModel.getValueAt(0, 0));
        assertEquals("two", tableModel.getValueAt(1, 0));

        for (int i = tableModel.getRowCount(); --i >= 0;) {
            assertEquals(false, tableModel.isCellEditable(i, 0));
            assertTrue(tableModel.isCellEditable(i, 1));
        }

        assertEquals(String.class, tableModel.getColumnClass(0));
    }

    protected void setUp() {
        tableModel = new ParamsTableModel();
        tableModel.addParams(new ParamInfo[]{
                new BoundedIntValueParamInfo("one", new conexp.util.valuemodels.BoundedIntValue("one", 1, 0, 2)),
                new BoundedIntValueParamInfo("two", new conexp.util.valuemodels.BoundedIntValue("two", 1, -1, 3))});
    }

    public void testClear() {
        MockTableModelListener mockListener = new MockTableModelListener();
        mockListener.setExpectStructureChanged();
        tableModel.addTableModelListener(mockListener);
        tableModel.clear();
        mockListener.verify();

    }
}
