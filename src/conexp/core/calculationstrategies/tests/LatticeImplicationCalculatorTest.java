/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.Context;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.calculationstrategies.LatticeImplicationCalculator;
import conexp.core.tests.SetBuilder;

public class LatticeImplicationCalculatorTest extends ImplicationCalculatorTest {

    protected ImplicationCalcStrategy makePreparedCalculator(Context cxt) {
        Lattice lat = SetBuilder.makeLattice(cxt);
        LatticeImplicationCalculator calc = new LatticeImplicationCalculator();
        calc.setLattice(lat);
        return calc;
    }

    public static void testIsUnionEqualsTo() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{{0, 0, 1},
                {0, 1, 0},
                {1, 0, 0}});


        LatticeImplicationCalculator calc = new LatticeImplicationCalculator();
        calc.setLattice(lat);

        assertTrue(calc.isUnionEqualsTo(lat.getOne(), lat.getOne(), lat.getOne()));

        LatticeElement other = SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 0, 1});

        assertTrue(calc.isUnionEqualsTo(lat.getOne(), other, other));
        assertTrue(calc.isUnionEqualsTo(other, lat.getOne(), other));

        LatticeElement incomparableWithOther = SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 1, 0});
        assertTrue(calc.isUnionEqualsTo(other, incomparableWithOther, lat.getZero()));


        lat = SetBuilder.makeLattice(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 1}
        });


        assertTrue(calc.isUnionEqualsTo(SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 0, 1}),
                SetBuilder.findLatticeElementWithIntent(lat, new int[]{1, 0, 0}),
                lat.getZero()));

        assertEquals(false, calc.isUnionEqualsTo(SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 0, 1}),
                SetBuilder.findLatticeElementWithIntent(lat, new int[]{0, 1, 0}),
                lat.getZero()));

    }


}
