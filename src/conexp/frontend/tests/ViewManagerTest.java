package conexp.frontend.tests;

import conexp.frontend.View;
import conexp.frontend.ViewFactory;
import conexp.frontend.ui.ViewManager;
import conexp.frontend.ui.ViewManagerException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.swing.*;

/**
 * JUnit test case for CompositeFileFilterTest
 */

public class ViewManagerTest extends TestCase {
    private static final Class THIS = ViewManagerTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    protected conexp.frontend.ui.ViewManager viewManager;

    protected void setUp() throws Exception {
        viewManager = makeViewManager();
        ViewFactory factory = new ViewFactory() {
            public View makeView(String name) {
                return null;
            }
        };
        viewManager.setViewFactory(factory);

    }

    public void testActivation() {
        registerView("Context", "CONTEXT_EDITOR");
        registerView("Lattice", "LINE_DIAG_EDITOR");

        expectFailingActivation("Rules", "Can't activate unregistered views");
        expectFailingActivation("Context", "Can't activate views, which aren't created by factory");

        setContextEditorFactory();
        expectFailingActivation("Lattice", "Can't activate views, which aren't created by factory");

        expectSuccessfulActivation("Context");
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 14:53:35)
     */
    public void testRegistrationManager() {
        final String viewName = "Context";
        registerView(viewName, "CONTEXT_EDITOR");
        try {
            viewManager.registerView(viewName, "Caption", "CONTEXT_EDITOR");
            fail("Views with same name can't be added");
        } catch (ViewManagerException ex) {
        }

    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 14:53:35)
     */
    public void testRemoval() {
        setContextEditorFactory();

        final String viewName = "Context";
        registerView(viewName, "CONTEXT_EDITOR");

        expectSuccessfulActivation(viewName);

        viewManager.removeView(viewName);
        assertEquals(null, viewManager.getView(viewName));

    }

    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 21:05:12)
     */
    protected void expectFailingActivation(String activatedView, String errMessage) {
        try {
            viewManager.activateView(activatedView);
            fail(errMessage);
        } catch (ViewManagerException ex) {
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 21:10:46)
     */
    protected void expectSuccessfulActivation(String viewName) {
        try {
            viewManager.activateView(viewName);
            assertNotNull(viewManager.getView(viewName));
            assertEquals(viewManager.getView(viewName), viewManager.getActiveView());
        } catch (ViewManagerException ex) {
            fail("should activate view");
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 22:28:12)
     */
    protected ViewManager makeViewManager() {
        return new ViewManager();
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 21:13:36)
     */
    protected void registerView(String viewName, String viewType) {
        try {
            viewManager.registerView(viewName, viewType, "Caption");
        } catch (ViewManagerException ex) {
            fail("Shouldn't got exception after registering");
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.01 21:01:59)
     */
    protected void setContextEditorFactory() {
        ViewFactory factory = new ViewFactory() {
            public View makeView(String type) {
                if ("CONTEXT_EDITOR".equals(type)) {
                    class DummyView extends JPanel implements View {
                        public void initialUpdate() {

                        }
                    }
                    ;
                    return new DummyView();
                }
                return null;
            }
        };
        viewManager.setViewFactory(factory);
    }
}