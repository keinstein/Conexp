/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.latticeeditor.BaseLatticePainterPane;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import junit.framework.TestCase;

public class BaseLatticePainterPaneTest extends TestCase {
    static class TestableLatticePainterPane extends BaseLatticePainterPane {
        public TestableLatticePainterPane() {
            super();
        }

        public void init() {
            super.init();
        }

    }

    public void testCorrectHandlingOfOptionsChangeListeners() {
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent(
                new int[][]{{0, 1}});
        final LatticePainterOptions latticeCanvasScheme = component.getDrawing().getPainterOptions();
        TestableLatticePainterPane canvas = new TestableLatticePainterPane();
        canvas.init();
        assertEquals(0, latticeCanvasScheme.getPropertyChangeListenersCount());
        canvas.setLatticeSupplier(component);
        assertEquals(1, latticeCanvasScheme.getPropertyChangeListenersCount());
        LatticeComponent other = ComponentsObjectMother.makeLatticeComponent(new int[][]{{1}});
        canvas.setLatticeSupplier(other);
        assertEquals(0, latticeCanvasScheme.getPropertyChangeListenersCount());
    }

}
