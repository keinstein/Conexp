/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.layeredlayout.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunctionTest.class);
        suite.addTestSuite(BacktrackingAlgorithmTest.class);
        suite.addTestSuite(DifferentEdgeVectorsEvaluationFunctionTest.class);
        suite.addTestSuite(DirectionVectorEvaluationResultsPairTest.class);
        suite.addTestSuite(LayeredLayouterTest.class);
        suite.addTestSuite(LatticePictureWidthEvaluationFunctionTest.class);
        suite.addTestSuite(LengthOfEdgesEvaluationFunctionTest.class);
        suite.addTestSuite(NumberOfSimmetricallyAllocatedChildrenEvaluationFunctionTest.class);
        suite.addTestSuite(ThreeElementsChainCountEvaluationFunctionTest.class);
        return suite;
    }

}
