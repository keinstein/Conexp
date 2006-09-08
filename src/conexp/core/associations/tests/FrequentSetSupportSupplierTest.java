/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.associations.ContextFrequentSetSupportSupplier;
import conexp.core.associations.FrequentSetSupportSupplier;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class FrequentSetSupportSupplierTest extends TestCase {
    private static final Class THIS = FrequentSetSupportSupplierTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public static void testSupports() {
        ExtendedContextEditingInterface cxt = SetBuilder.makeContext(new int[][]{{1, 1, 1, 1},
                {1, 1, 1, 0},
                {1, 0, 0, 0}});
        FrequentSetSupportSupplier fsSuppSupplier =
                new ContextFrequentSetSupportSupplier(cxt);

        assertEquals(3, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{0, 0, 0, 0})));
        assertEquals(3, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 0, 0, 0})));
        assertEquals(2, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 1, 0, 0})));
        assertEquals(2, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 1, 1, 0})));
        assertEquals(2, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 0, 1, 0})));
        assertEquals(1, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{0, 0, 0, 1})));
        assertEquals(1, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{0, 0, 1, 1})));
        assertEquals(1, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 0, 1, 1})));
        assertEquals(1, fsSuppSupplier.supportForSet(SetBuilder.makeSet(new int[]{1, 1, 1, 1})));
    }
}
