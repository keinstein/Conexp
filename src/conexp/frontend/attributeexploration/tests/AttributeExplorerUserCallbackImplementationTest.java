/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.attributeexploration.tests;

import conexp.core.tests.TestAttributeExplorerUserCallbackLocalisedMessageSupplier;
import conexp.frontend.attributeexploration.AttributeExplorationUserCallbackImplementation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AttributeExplorerUserCallbackImplementationTest extends TestCase {
    private static final Class THIS = AttributeExplorerUserCallbackImplementationTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testMessageSupplier() {
        AttributeExplorationUserCallbackImplementation callbackImpl = new AttributeExplorationUserCallbackImplementation(null);
        TestAttributeExplorerUserCallbackLocalisedMessageSupplier.testLocalizedMessageSupplier(callbackImpl.getLocalizedMessageSupplier());
    }

}
