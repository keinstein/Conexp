/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.bitset.tests;

import conexp.core.BinaryRelation;
import conexp.core.ContextFactory;
import conexp.core.Set;
import conexp.core.bitset.BitSetFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class BitSetFactoryTest extends TestCase {

    private ContextFactory factory;

    protected void setUp() {
        factory = BitSetFactory.getInstance();
    }

    public static Test suite() {
        return new TestSuite(BitSetFactoryTest.class);
    }

    public void testRelation() {
        try {
            factory.createRelation(-1, 2);
            fail("Created relation with negative size");
        } catch (IndexOutOfBoundsException exc) {
        }

        try {
            factory.createRelation(2, -1);
            fail("Created set with negative size");
        } catch (IndexOutOfBoundsException exc) {
        }
        try {
            factory.createRelation(0, 1);
        } catch (IndexOutOfBoundsException exc) {
            assertTrue(true);
        }

        try {
            factory.createRelation(1, 0);
        } catch (IndexOutOfBoundsException exc) {
            assertTrue(true);
        }

        BinaryRelation tmp = factory.createRelation(1, 2);
        assertNotNull(tmp);

    }

    public void testSet() {
        try {
            factory.createSet(-1);
            fail("Created set with negative size");
        } catch (IndexOutOfBoundsException exc) {

        }
        Set temp = factory.createSet(0);
        assertNotNull("Failed to create set with zero length", temp);

        temp = factory.createSet(20);
        assertNotNull("Failed to create set with nonzero length", temp);
    }


}
