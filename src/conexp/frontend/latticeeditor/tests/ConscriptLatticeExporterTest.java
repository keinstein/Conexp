package conexp.frontend.latticeeditor.tests;

import junit.framework.TestCase;
import conexp.frontend.latticeeditor.ConscriptLatticeExporter;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/12/2003
 * Time: 15:17:52
 */
public class ConscriptLatticeExporterTest extends TestCase {
    public void testAcceptanceOfExtension() {
        ConscriptLatticeExporter exporter = new ConscriptLatticeExporter(null);
        assertTrue(exporter.accepts("C:\\test.csc"));
        assertFalse(exporter.accepts("C:\\work.gif"));
    }
}
