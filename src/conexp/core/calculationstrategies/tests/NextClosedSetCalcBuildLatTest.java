package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Creation date: (12.07.01 16:16:04)
 * @author sergey
 */
public class NextClosedSetCalcBuildLatTest extends LatticeBuildingDepthSearchCalculatorTest {
    private static final Class THIS = NextClosedSetCalcBuildLatTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new NextClosedSetCalculator();
    }


    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new NextClosedSetLatticeBuilderCallback((Lattice) lat));
    }

}