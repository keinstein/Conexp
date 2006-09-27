/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AllAttribsLabelingStrategyTest.class);
        suite.addTestSuite(AllObjectsLabelingStrategyTest.class);
        suite.addTestSuite(AllAttribsMultiLineLabelingStrategyTest.class);
        suite.addTestSuite(AllObjectsMultiLineLabelingStrategyTest.class);

        suite.addTestSuite(AttributesLabelingStrategyModelTest.class);
        suite.addTestSuite(ObjectsLabelingStrategyModelTest.class);
        suite.addTestSuite(OwnAttribsCountLabelingStrategyTest.class);
        suite.addTestSuite(OwnObjectsCountLabelingStrategyTest.class);
        suite.addTestSuite(ObjectsCountLabelingStrategyTest.class);

        return suite;
    }
}
