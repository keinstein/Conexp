package conexp.frontend.contexteditor.tests;

import junit.framework.TestCase;
import conexp.frontend.contexteditor.ContextTable;
import conexp.core.Context;
import conexp.core.tests.SetBuilder;

import java.util.List;
import java.util.Arrays;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class ContextTableTest extends TestCase {
    public void testPasteAllowed(){
        Context cxt = SetBuilder.makeContext(new int[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        });
        ContextTable table = new ContextTable(cxt);
        int[] selRows = {0, 1};
        int[] selCols = {0, 1};
        String copied = table.buildStringRepresentation(
             selRows.length, selCols.length, selRows, selCols
        );
        assertTrue(table.contentCanBePasted(copied, 0, 0));
        assertFalse(table.contentCanBePasted(copied, 1, 0));
        assertFalse(table.contentCanBePasted(copied, 0, 1));
        assertFalse(table.contentCanBePasted(copied, 1, 1));

        selRows = new int[]{1, 2};
        selCols = new int[]{1, 2};
        copied = table.buildStringRepresentation(
             selRows.length, selCols.length, selRows, selCols
        );
        assertFalse(table.contentCanBePasted(copied, 0, 0));
        assertTrue(table.contentCanBePasted(copied, 1, 0));
        assertTrue(table.contentCanBePasted(copied, 0, 1));
        assertTrue(table.contentCanBePasted(copied, 1, 1));

        assertFalse(table.contentCanBePasted(copied, 3, 1));
        assertFalse(table.contentCanBePasted(copied, 1, 3));

    }

    public void testSplit() throws Exception {
        List expected = Arrays.asList(new String[]{"1", "2"});
        assertEquals(expected, ContextTable.split("1\t2", '\t'));
        expected = Arrays.asList(new String[]{"1"});
        assertEquals(expected, ContextTable.split("1", '\t'));

        expected = Arrays.asList(new String[]{"", "1"});
        assertEquals(expected, ContextTable.split("\t1",'\t'));
    }

}
