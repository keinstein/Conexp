/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import conexp.core.DependencySet;
import conexp.core.associations.tests.ObjectMother;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DependencySetTest extends TestCase {
    public void testEquals() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(3);
        DependencySet one = new DependencySet(supplier);
        DependencySet two = new DependencySet(supplier);
        assertEquals(one, two);

        assertEquals(false, one.equals(null));
        assertEquals(false, one.equals(new Object()));

        one.addDependency(
                ObjectMother.makeAssociationRule(
                        new int[]{0, 0, 0}, 10,
                        new int[]{1, 1, 1}, 8)
        );
        assertEquals(false, one.equals(two));
    }

    public void testHashCode() {
        MockAttributeInformationSupplier supplier = new MockAttributeInformationSupplier(3);
        DependencySet one = new DependencySet(supplier);
        DependencySet two = new DependencySet(supplier);
        assertEquals(one.hashCode(), two.hashCode());

    }
}
