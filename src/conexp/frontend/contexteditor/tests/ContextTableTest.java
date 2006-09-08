/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.contexteditor.tests;

import conexp.core.Context;
import conexp.core.tests.SetBuilder;
import conexp.frontend.contexteditor.ContextTable;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;



public class ContextTableTest extends TestCase {
    private ContextTable table;
    private Context cxt;

    public void testPasteAllowed() {
        int[] selRows = {0, 1};
        int[] selCols = {0, 1};
        String copied = table.buildStringRepresentation(selRows.length, selCols.length, selRows, selCols);
        assertTrue(table.contentCanBePasted(copied, 0, 0));
        assertFalse(table.contentCanBePasted(copied, 1, 0));
        assertFalse(table.contentCanBePasted(copied, 0, 1));
        assertFalse(table.contentCanBePasted(copied, 1, 1));

        selRows = new int[]{1, 2};
        selCols = new int[]{1, 2};
        copied = table.buildStringRepresentation(selRows.length, selCols.length, selRows, selCols);
        assertFalse(table.contentCanBePasted(copied, 0, 0));
        assertTrue(table.contentCanBePasted(copied, 1, 0));
        assertTrue(table.contentCanBePasted(copied, 0, 1));
        assertTrue(table.contentCanBePasted(copied, 1, 1));

        assertFalse(table.contentCanBePasted(copied, 3, 1));
        assertFalse(table.contentCanBePasted(copied, 1, 3));

    }

    protected void setUp() {
        cxt = SetBuilder.makeContext(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        });
        table = new ContextTable(cxt);
    }

    public static void testSplit() {
        List expected = Arrays.asList(new String[]{"1", "2"});
        assertEquals(expected, ContextTable.split("1\t2", '\t'));
        expected = Arrays.asList(new String[]{"1"});
        assertEquals(expected, ContextTable.split("1", '\t'));

        expected = Arrays.asList(new String[]{"", "1"});
        assertEquals(expected, ContextTable.split("\t1", '\t'));
    }

    public void testCorrectnessOfCompressedView() {
        assertEquals(false, table.getCompressView().getValue());
        assertEquals(ContextTable.USUAL_WIDTH, table.getColumnModel().getColumn(1).getPreferredWidth());
        table.getCompressView().setValue(true);
        assertEquals(ContextTable.COMPRESSED_WIDTH, table.getColumnModel().getColumn(1).getPreferredWidth());
        cxt.removeObject(0);
        assertEquals(ContextTable.COMPRESSED_WIDTH, table.getColumnModel().getColumn(1).getPreferredWidth());
    }

}
