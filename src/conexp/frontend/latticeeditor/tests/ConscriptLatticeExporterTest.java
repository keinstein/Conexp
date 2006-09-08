/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.ConscriptLatticeExporter;
import junit.framework.TestCase;


public class ConscriptLatticeExporterTest extends TestCase {
    public static void testAcceptanceOfExtension() {
        ConscriptLatticeExporter exporter = new ConscriptLatticeExporter(null);
        assertTrue(exporter.accepts("C:\\test.csc"));
        assertFalse(exporter.accepts("C:\\work.gif"));
    }
}
