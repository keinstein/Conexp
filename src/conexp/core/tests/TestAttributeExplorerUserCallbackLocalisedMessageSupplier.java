/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.tests;

import conexp.core.LocalizedMessageSupplier;
import junit.framework.TestCase;

public class TestAttributeExplorerUserCallbackLocalisedMessageSupplier extends TestCase {
    public TestAttributeExplorerUserCallbackLocalisedMessageSupplier(String name) {
        super(name);
    }

    public static void testLocalizedMessageSupplier(LocalizedMessageSupplier supplier) {
        assertNotNull(supplier.getMessage("AttributeExplorer.Error.CounterExampleDoesntContainWholePremise"));
        assertNotNull(supplier.getMessage("AttributeExplorer.Error.CounterExampleContainWholeConclusion"));
        assertNotNull(supplier.getMessage("AttributeExplorer.Error.CounterExampleContradictEarlierAcceptedImplications"));
    }

}
