/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(AllAttribsLabelingStrategyTest.suite());
        suite.addTest(AllObjectsLabelingStrategyTest.suite());

        suite.addTest(AttributesLabelingStrategyModelTest.suite());
        suite.addTest(ObjectsLabelingStrategyModelTest.suite());
        suite.addTest(OwnObjectsCountLabelingStrategyTest.suite());
        suite.addTest(ObjectsCountLabelingStrategyTest.suite());

        return suite;
    }
}
