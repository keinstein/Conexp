/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ConceptIntegralStabilityNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(ConceptPointStabilityNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(ConceptStabilityToDesctructionNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(MaxNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(NodeRadiusStrategyModelTest.class);
        suite.addTestSuite(ObjectVolumeNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(OwnAttributesVolumeNodeRadiusCalcStrategyTest.class);
        suite.addTestSuite(OwnObjectsVolumeNodeRadiusCalcStrategyTest.class);
        return suite;
    }
}
