/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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
        suite.addTest(ConExpXMLReaderWriterTest.suite());
        suite.addTest(ConImpContextReaderWriterTest.suite());
        suite.addTest(ConImpContextReaderTest.suite());
        return suite;
    }
}
