package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.noderadiusstrategy.OwnAttributesVolumeNodeRadiusCalcStrategy;
import conexp.core.tests.SetBuilder;
import conexp.core.Lattice;
import conexp.core.LatticeElement;

public class OwnAttributesVolumeNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {
    static final double EPSILON = 0.01;

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return makeNativeStrategy(new LatticePainterDrawParams());
    }

    private OwnAttributesVolumeNodeRadiusCalcStrategy makeNativeStrategy(DrawParameters opt) {
        return new OwnAttributesVolumeNodeRadiusCalcStrategy(opt);
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return makeNativeStrategy(BasicDrawParams.getInstance());
    }

    public void testCalculationOfTheValues(){
        OwnAttributesVolumeNodeRadiusCalcStrategy strategy = makeNativeStrategy(BasicDrawParams.getInstance());
        Lattice lattice = SetBuilder.makeLattice(new int[][]{
                { 0},
                { 0},
                { 1}
        }
        );
        strategy.setConceptSet(lattice);
        strategy.initCalc();
        LatticeElement one = lattice.getOne();
        LatticeElement zero = lattice.getZero();
        assertEquals(1, zero.getOwnAttrCnt());
        assertEquals(0, one.getOwnAttrCnt());

        assertEquals(0., strategy.calculateRatio(makeConceptNodeQuery(lattice, one)),EPSILON);
        assertEquals(1.0, strategy.calculateRatio(makeConceptNodeQuery(lattice, zero)), EPSILON);
    }

    private ConceptNodeQuery makeConceptNodeQuery(Lattice lattice, LatticeElement element) {
        return ConceptNodeQuery.createNodeQuery(lattice, element);
    }
}
