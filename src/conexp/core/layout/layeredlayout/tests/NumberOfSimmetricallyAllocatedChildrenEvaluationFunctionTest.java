package conexp.core.layout.layeredlayout.tests;

import junit.framework.TestCase;
import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.NumberOfSimmetricallyAllocatedChildrenEvaluationFunction;
import conexp.core.tests.SetBuilder;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class NumberOfSimmetricallyAllocatedChildrenEvaluationFunctionTest extends TestCase {

    //and what about parents ??? :-)
    public void testNumberOfSimmetricallyAllocatedChildren() {
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(
                lattice, TestDataHolder.FULL_RELATION_NOMINAL_3,
                TestDataHolder.SIMMETRIC_LAYOUT_NOMINAL_3
        );
        NumberOfSimmetricallyAllocatedChildrenEvaluationFunction evaluationFunction = new NumberOfSimmetricallyAllocatedChildrenEvaluationFunction();
        evaluationFunction.setLattice(lattice);
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(8, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }
}
