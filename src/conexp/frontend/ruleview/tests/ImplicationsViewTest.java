/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.ruleview.tests;

import conexp.frontend.ruleview.ImplicationsView;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ImplicationsViewTest extends TestCase {
    private static final Class THIS = ImplicationsViewTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testResources() {
        ImplicationsView ruleView = new ImplicationsView(new MockDependencySetSupplier(), null);
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(ruleView.getResources(), ruleView.getActionChain());
    }

}
