/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout.tests;

import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.NumberOfSimmetricallyAllocatedChildrenEvaluationFunction;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public class NumberOfSimmetricallyAllocatedChildrenEvaluationFunctionTest extends TestCase {

    //and what about parents ??? :-)
    public static void testNumberOfSimmetricallyAllocatedChildrenForThreeChildCase() {
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, TestDataHolder.FULL_RELATION_NOMINAL_3,
                TestDataHolder.SYMMETRIC_LAYOUT_NOMINAL_3);
        NumberOfSimmetricallyAllocatedChildrenEvaluationFunction evaluationFunction = new NumberOfSimmetricallyAllocatedChildrenEvaluationFunction();
        evaluationFunction.setLattice(lattice);
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(8, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }

    public static void testNumberOfSimmetricallyAllocatedChildrenForTwoChildCase() {
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_2);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, TestDataHolder.FULL_RELATION_NOMINAL_2,
                TestDataHolder.SYMMETRIC_LAYOUT_NOMINAL_2);
        NumberOfSimmetricallyAllocatedChildrenEvaluationFunction evaluationFunction = new NumberOfSimmetricallyAllocatedChildrenEvaluationFunction();
        evaluationFunction.setLattice(lattice);
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(4, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);

        mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, TestDataHolder.FULL_RELATION_NOMINAL_2,
                TestDataHolder.ASYMMETRIC_LAYOUT_NOMINAL_2);
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(2, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }

}
