package conexp.core.layout.layeredlayout.tests;

import junit.framework.TestCase;
import conexp.core.layout.layeredlayout.LengthOfEdgesEvaluationFunction;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class LengthOfEdgesEvaluationFunctionTest extends TestCase {
    public void testLengthOfEdges(){
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice,
                TestDataHolder.FULL_RELATION_NOMINAL_3, TestDataHolder.SYMMETRIC_LAYOUT_NOMINAL_3);

        LengthOfEdgesEvaluationFunction function = new LengthOfEdgesEvaluationFunction();
        function.setLattice(lattice);
        function.setConceptCoordinateMapper(mapper);

        assertEquals(-(2+4*Math.sqrt(2)),function.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }
}
