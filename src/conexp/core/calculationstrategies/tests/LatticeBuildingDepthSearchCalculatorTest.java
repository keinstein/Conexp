package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.BinaryRelation;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.core.compareutils.LatticeComparator;
import conexp.core.tests.SetBuilder;

public abstract class LatticeBuildingDepthSearchCalculatorTest extends CalcStrategyTest {

    protected Lattice getLattice() {
        return (Lattice) conceptSet;
    }

    /**
     * Insert the method's description here.
     * Creation date: (01.07.01 23:14:26)
     */
    protected void buildIntentsSetAndFillExpectationSet(BinaryRelation rel, ExpectationSet expSet,
                                                        ExpectationSet expSetExtents, int expectedEdgeCount) {
        super.buildIntentsSetAndFillExpectationSet(rel, expSet, expSetExtents, expectedEdgeCount);
        compareExpEdgeCount(rel, getLattice(), expectedEdgeCount);
    }

    protected void compareExpEdgeCount(BinaryRelation rel, Lattice lat, int expectedEdgeCount) {
        LatticeComparator comp = new LatticeComparator(conexp.core.tests.SetBuilder.makeLattice(rel), lat);
        if (!comp.equal || expectedEdgeCount != lat.edgeCount()) {
            comp.dumpDifferencesToSout();
            assertEquals(expectedEdgeCount, lat.edgeCount());
            assertTrue("compare of lattice failed ", false);
        }
    }

    protected void doTestCalcStrategyForExpectedSizeAndEdgeCount(int[][] input, int expectedSize, int expEdgeCount) {
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        Lattice lattice = makeLattice();
        setupStrategy(lattice);
        getLatticeCalcStrategy().buildLattice();
        assertEquals(expectedSize, lattice.conceptsCount());
        assertEquals(expEdgeCount, lattice.edgeCount());
    }

    protected void generateIntents() {
        getLatticeCalcStrategy().buildLattice();
    }

    protected LatticeCalcStrategy getLatticeCalcStrategy() {
        return ((LatticeCalcStrategy) calcStrategy);
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.07.01 8:18:57)
     */
    protected ConceptsCollection makeConceptCollection() {
        return makeLattice();
    }

    protected Lattice makeLattice() {
        return new Lattice();
    }


    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 16:08:42)
     * @param lat conexp.core.Lattice
     */
    protected abstract void setupStrategy(ConceptsCollection lat);
}