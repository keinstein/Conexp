package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Creation date: (12.07.01 16:09:29)
 * @author Serhiy Yevtushenko
 */
public class DepthSearchCalcLatticeBuildingTest extends LatticeBuildingDepthSearchCalculatorTest {
    private static final Class THIS = DepthSearchCalcLatticeBuildingTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculator();
    }

    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new conexp.core.enumcallbacks.ConceptSetCallback(lat));
        ((DepthSearchCalculator) calcStrategy).setLattice((Lattice) lat);
    }

}