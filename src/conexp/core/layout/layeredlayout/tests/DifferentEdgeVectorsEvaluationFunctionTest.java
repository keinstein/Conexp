package conexp.core.layout.layeredlayout.tests;

import junit.framework.TestCase;
import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.DifferentEdgeVectorsEvaluationFunction;
import conexp.core.layout.layeredlayout.LatticeBasedEvaluationFunctionBase;
import conexp.core.layout.layeredlayout.tests.MapBasedConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.tests.SetBuilder;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class DifferentEdgeVectorsEvaluationFunctionTest extends TestCase {

    public void testEvaluationFunction() {
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(
                lattice, TestDataHolder.FULL_RELATION_NOMINAL_3,
                TestDataHolder.SIMMETRIC_LAYOUT_NOMINAL_3
        );
        LatticeBasedEvaluationFunctionBase evaluationFunction = new DifferentEdgeVectorsEvaluationFunction();
        evaluationFunction.setLattice(lattice);
        evaluationFunction.setConceptCoordinateMapper(mapper);

        assertEquals(-3, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
        mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(
                lattice, TestDataHolder.FULL_RELATION_NOMINAL_3,
                new double[][]{
                    {0, 0},
                    {-1, 1},
                    {0, 1},
                    {1, 1},
                    {1.5, 2}
               }
        );
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(-6, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }

}
