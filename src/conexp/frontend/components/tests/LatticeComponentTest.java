/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.components.tests;

import conexp.core.tests.SetBuilder;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.components.LatticeComponent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.testing.SimpleMockPropertyChangeListener;

public class LatticeComponentTest extends TestCase {
    private static final Class THIS = LatticeComponentTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    LatticeComponent latticeComponent;

    protected void setUp() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                                                   {1, 0, 0}}));
    }


    public void testLatticeCalculateLattice() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(LatticeCalculator.LATTICE_DRAWING_CHANGED);
        listener.setExpected(1);
        latticeComponent.addPropertyChangeListener(LatticeCalculator.LATTICE_DRAWING_CHANGED, listener);
        latticeComponent.calculateLattice();
        listener.verify();
    }

    public void testLatticeClearLattice() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(LatticeCalculator.LATTICE_CLEARED);
        listener.setExpected(1);
        latticeComponent.addPropertyChangeListener(LatticeCalculator.LATTICE_CLEARED, listener);
        latticeComponent.clearLattice();
        listener.verify();
    }

    public void testCalcLattice() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                                                   {0, 1, 1}}));

        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
    }

    public void testCalcLattice2() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                                                   {0, 1, 1}}));
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
    }

    public void testCalcPartialLattice(){
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                                                   {1, 0, 1},
                                                                                   {1, 1, 0}}));
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
        latticeComponent.getAttributeMask().setAttributeSelected(0, false);
        latticeComponent.calculatePartialLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());

    }

}
