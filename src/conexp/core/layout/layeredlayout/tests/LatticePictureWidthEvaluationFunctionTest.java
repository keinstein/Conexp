package conexp.core.layout.layeredlayout.tests;

import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.LatticePictureWidthEvaluationFunction;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class LatticePictureWidthEvaluationFunctionTest extends TestCase {
    public void testLatticeWidth(){
        Lattice lattice = SetBuilder.makeLattice(TestDataHolder.FULL_RELATION_NOMINAL_3);
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice,
                TestDataHolder.FULL_RELATION_NOMINAL_3, TestDataHolder.SYMMETRIC_LAYOUT_NOMINAL_3);

        LatticePictureWidthEvaluationFunction function = new LatticePictureWidthEvaluationFunction(lattice, mapper);

        assertEquals(-2, function.getEvaluationForLattice(), TestDataHolder.PRECISION);

    }
}
