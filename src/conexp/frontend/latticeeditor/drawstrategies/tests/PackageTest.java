/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.drawstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

//todo: rename to AllTests

public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultDrawStrategiesModelsFactoryTest.class);
        suite.addTestSuite(DefaultLabelingStrategiesModelsFactoryTest.class);
        return suite;
    }
}
