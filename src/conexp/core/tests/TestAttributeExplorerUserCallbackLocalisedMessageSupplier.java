/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



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
