/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



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

    public static void testMessageSupplier() {
        AttributeExplorationUserCallbackImplementation callbackImpl = new AttributeExplorationUserCallbackImplementation(null);
        TestAttributeExplorerUserCallbackLocalisedMessageSupplier.testLocalizedMessageSupplier(callbackImpl.getLocalizedMessageSupplier());
    }

}
