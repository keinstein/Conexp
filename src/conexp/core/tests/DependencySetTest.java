/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.DependencySet;
import conexp.core.associations.tests.AssociationsBuilder;
import junit.framework.TestCase;


public class DependencySetTest extends TestCase {
    public static void testEquals() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(3);
        DependencySet one = new DependencySet(supplier);
        DependencySet two = new DependencySet(supplier);
        assertEquals(one, two);

        assertEquals("Object one should not be equal to null", false, one.equals(null));
        assertEquals("DependencySet can be equal only to objects of class DependencySet",
                false, one.equals(new Object()));
        MockAttributeInformationSupplier otherSupplier = new MockAttributeInformationSupplier(2);
        DependencySet three = new DependencySet(otherSupplier);
        assertFalse(one.equals(three));


        one.addDependency(AssociationsBuilder.makeAssociationRule(new int[]{0, 0, 0}, 10,
                new int[]{1, 1, 1}, 8));
        assertEquals(false, one.equals(two));
    }

    public static void testHashCode() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(3);
        DependencySet one = new DependencySet(supplier);
        DependencySet two = new DependencySet(supplier);
        assertEquals(one.hashCode(), two.hashCode());

    }
}
