/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.layeredlayout.tests;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;

public class AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunctionTest extends TestCase {
    public static void testEvaluationFunction() {
        int[][] arrRelation = new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        Lattice lattice = SetBuilder.makeLattice(arrRelation);

        LatticeElement first = lattice.findElementWithIntent(SetBuilder.makeSet(arrRelation[0]));
        LatticeElement second = lattice.findElementWithIntent(SetBuilder.makeSet(arrRelation[1]));
        LatticeElement third = lattice.findElementWithIntent(SetBuilder.makeSet(arrRelation[2]));
        LatticeElement[][] layer = new LatticeElement[][]{
                {first, second, third}
        };

        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, arrRelation,
                new double[][]{
                        {1, 2},
                        {0, 2},
                        {1, 2}
                });
        AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction evaluationFunction = new
                AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction(mapper, layer);

        assertEquals(-1, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
        mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, arrRelation,
                new double[][]{
                        {1, 2},
                        {2, 2},
                        {3, 2}
                });
        evaluationFunction.setConceptCoordinateMapper(mapper);
        assertEquals(0, evaluationFunction.getEvaluationForLattice(), TestDataHolder.PRECISION);
    }

}
