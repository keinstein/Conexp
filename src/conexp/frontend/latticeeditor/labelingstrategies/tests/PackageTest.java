/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

//todo: rename to AllTests
public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AllAttribsLabelingStrategyTest.class);
        suite.addTestSuite(AllObjectsLabelingStrategyTest.class);

        suite.addTestSuite(AttributesLabelingStrategyModelTest.class);
        suite.addTestSuite(ObjectsLabelingStrategyModelTest.class);
        suite.addTestSuite(OwnObjectsCountLabelingStrategyTest.class);
        suite.addTestSuite(ObjectsCountLabelingStrategyTest.class);

        return suite;
    }
}
