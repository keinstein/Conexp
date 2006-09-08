/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.utils.tests;

import conexp.core.Set;
import conexp.core.utils.PowerSetIterator;
import junit.framework.TestCase;
import util.collection.CollectionFactory;

public class PowerSetIteratorTest extends TestCase {
    public static void testGeneratePowerSet() {
        PowerSetIterator iterator = new PowerSetIterator(1);
        assertEquals(2, calcPowerSetSize(iterator));
    }

    public static void testGeneratePowerSetForCase2() {
        PowerSetIterator iterator = new PowerSetIterator(2);
        assertEquals(4, calcPowerSetSize(iterator));
    }

    private static int calcPowerSetSize(PowerSetIterator iterator) {
        java.util.Set results = CollectionFactory.createDefaultSet();
        while (iterator.hasNext()) {
            Set nextSet = iterator.nextSet();
            results.add(nextSet);
        }
        return results.size();
    }

    public static void testGeneratePowerSetForCase3() {
        PowerSetIterator iterator = new PowerSetIterator(7);
        assertEquals(128, calcPowerSetSize(iterator));
    }

    public static void testGeneratePowerSetForCaseOfEmptySet() {
        PowerSetIterator iterator = new PowerSetIterator(0);
        assertEquals(1, calcPowerSetSize(iterator));
    }

}
