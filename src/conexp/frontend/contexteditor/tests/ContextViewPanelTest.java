/*
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 */
package conexp.frontend.contexteditor.tests;

import conexp.core.tests.SetBuilder;
import conexp.frontend.contexteditor.ContextViewPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ContextViewPanelTest extends TestCase {
    private static final Class THIS = ContextViewPanelTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testResources() {
        ContextViewPanel panel = new ContextViewPanel(SetBuilder.makeContext(new int[][]{{0, 1}}));
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(panel.getResources(), panel.getActionChain());
    }

}
