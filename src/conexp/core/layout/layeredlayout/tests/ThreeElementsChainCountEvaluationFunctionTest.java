/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;

import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.ThreeElementsChainCountEvaluationFunction;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class ThreeElementsChainCountEvaluationFunctionTest extends TestCase {
    public static void testThreeElementChainCount() {
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, TestDataHolder.FULL_RELATION_NOMINAL_3,
                TestDataHolder.SYMMETRIC_LAYOUT_NOMINAL_3);
        ThreeElementsChainCountEvaluationFunction evaluationFunction = new ThreeElementsChainCountEvaluationFunction();
        evaluationFunction.setLattice(lattice);
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(1, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }
}
