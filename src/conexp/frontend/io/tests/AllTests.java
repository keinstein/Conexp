/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.io.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ConExpXMLReaderWriterTest.class);
        suite.addTestSuite(ConImpContextReaderTest.class);
        suite.addTestSuite(ConImpContextReaderWriterTest.class);
        suite.addTestSuite(LatticeCSCExporterTest.class);
        suite.addTestSuite(TabSeparatedReaderWriterTest.class);

        suite.addTest(conexp.frontend.io.csv.tests.AllTests.suite());
        suite.addTest(conexp.frontend.io.objattrlist.tests.AllTests.suite());
        return suite;
    }
}
