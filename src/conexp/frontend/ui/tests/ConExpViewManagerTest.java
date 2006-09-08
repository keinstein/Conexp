/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ui.tests;



import com.mockobjects.ExpectationCounter;
import conexp.frontend.LatticeAndEntitiesMaskSplitPane;
import conexp.frontend.ToolbarComponentDecorator;
import conexp.frontend.View;
import conexp.frontend.ViewChangeListener;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.ui.ConExpViewManager;
import conexp.frontend.ui.ViewInfo;
import junit.framework.TestCase;

import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ConExpViewManagerTest extends TestCase {

    class LatticeViewInfo extends ViewInfo {
        LatticeSupplier supplier;
        ActionMap actionMap = new ActionMap();

        public LatticeViewInfo(LatticeSupplier latticeSupplier) {
            super("VIEW_LATTICE", "Lattice");
            this.supplier = latticeSupplier;
        }

        public View createView() {
            LatticeAndEntitiesMaskSplitPane latticeSplitPane = new LatticeAndEntitiesMaskSplitPane(supplier, actionMap);
            latticeSplitPane.restorePreferences();
            return new ToolbarComponentDecorator(latticeSplitPane, false);
        }
    }

    public void testViewManager() {
        ConExpViewManager viewManager = new ConExpViewManager();
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent();
        LatticeViewInfo docModel = new LatticeViewInfo(component);
        viewManager.activateView(docModel);
        View activeView = viewManager.getActiveView();
        assertNotNull(activeView);
        LatticeComponent other = ComponentsObjectMother.makeLatticeComponent(new int[][]{
                {0, 1}
        });
        LatticeViewInfo otherDocModel = new LatticeViewInfo(other);
        viewManager.activateView(otherDocModel);
        View otherActiveView = viewManager.getActiveView();
        assertNotNull(otherActiveView);
        assertNotSame("Different views should be created for two lattice components", activeView, otherActiveView);
        viewManager.activateView(docModel);
        assertSame(activeView, viewManager.getActiveView());
    }

    public void testRemoveView() {
        ConExpViewManager viewManager = new ConExpViewManager();
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent();
        LatticeViewInfo docModel = new LatticeViewInfo(component);
        viewManager.activateView(docModel);
        assertNotNull(viewManager.getActiveView());
        assertTrue(viewManager.hasViewForModel(docModel));
        assertEquals(1, viewManager.getPlacesCount());
        viewManager.removeView(docModel);
        assertFalse(viewManager.hasViewForModel(docModel));
        assertNull(viewManager.getActiveView());
        assertEquals(0, viewManager.getPlacesCount());
    }

    public void testFiringViewChangeListener() {
        ConExpViewManager viewManager = new ConExpViewManager();
        final ExpectationCounter counter = new ExpectationCounter("Expected view changes");
        viewManager.addViewChangeListener(new ViewChangeListener() {
            public void viewChanged(JComponent oldView, JComponent newView) {
                counter.inc();
            }

            public void cleanUp() {
            }
        });
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent();
        LatticeViewInfo docModel = new LatticeViewInfo(component);
        counter.setExpected(1);
        viewManager.activateView(docModel);
        counter.verify();
    }


    static class TestViewInfo extends ViewInfo {
        ExpectationCounter counter = new ExpectationCounter("Expected initialUpdate");

        public TestViewInfo() {
            super("TEST_PLACE", "TestCaption");
        }

        public void setExpected(int i) {
            counter.setExpected(i);
        }

        public void verify() {
            counter.verify();
        }

        public View createView() {
            class TestView extends JPanel implements View {
                public void initialUpdate() {
                    counter.inc();
                }
            }
            return new TestView();
        }
    }

    public void testInitialUpdate() {
        TestViewInfo viewInfo = new TestViewInfo();
        viewInfo.setExpected(1);
        ConExpViewManager viewManager = new ConExpViewManager();
        viewManager.activateView(viewInfo);
        viewInfo.verify();
    }

    static class TestConExpViewManager extends ConExpViewManager {
        public JTabbedPane getTabPane() {
            return super.getTabPane();
        }
    }

    ;

    public void testOptionChangeOnActiveTabChange() {
        TestViewInfo testViewInfo = new TestViewInfo();
        testViewInfo.setExpected(1);
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent();
        LatticeViewInfo latticeViewInfo = new LatticeViewInfo(component);


        final ExpectationCounter counter = new ExpectationCounter("Expected view changes");

        TestConExpViewManager viewManager = new TestConExpViewManager();
        viewManager.addViewChangeListener(new ViewChangeListener() {
            public void viewChanged(JComponent oldView, JComponent newView) {
                counter.inc();
            }

            public void cleanUp() {
            }
        });

        counter.setExpected(1);
        viewManager.activateView(testViewInfo);
        testViewInfo.verify();
        counter.verify();

        counter.setExpected(1);
        viewManager.activateView(latticeViewInfo);
        counter.verify();
        assertEquals(1, viewManager.getTabPane().getSelectedIndex());

        counter.setExpected(1);
        viewManager.getTabPane().setSelectedIndex(0);

        counter.verify();

    }
}
