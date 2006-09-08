/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.paramseditor.tests;

import conexp.util.gui.paramseditor.ParamEditorTable;
import conexp.util.gui.paramseditor.ParamsTableModel;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParamEditorTableTest extends TestCase {
    private static final Class THIS = ParamEditorTableTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public static void testCellMapper() {
        ParamEditorTable table = new ParamEditorTable();
        assertNotNull(table.getCellEditorMapper());
        assertTrue(table.getCellEditorMapper() instanceof ParamsTableModel);
    }


    public static void testCanGetGoodTip() {
        assertEquals(false, ParamEditorTable.canGetGoodTip(null));
        assertEquals(false, ParamEditorTable.canGetGoodTip(new Object() {
            public String toString() {
                return null;
            }
        }));
        assertEquals(false, ParamEditorTable.canGetGoodTip(new Object() {
            public String toString() {
                return "";
            }
        }));

        assertEquals(false, ParamEditorTable.canGetGoodTip(new Object() {
            public String toString() {
                return "   ";
            }
        }));
    }
}
