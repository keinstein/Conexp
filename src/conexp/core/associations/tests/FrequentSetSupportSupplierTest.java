package conexp.core.associations.tests;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for SimpleFrequentSetSupportTest
 */

public class FrequentSetSupportSupplierTest extends TestCase {
    private static final Class THIS = FrequentSetSupportSupplierTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testSupports() {
        ExtendedContextEditingInterface cxt = SetBuilder.makeContext(new int[][]{{1, 1, 1, 1},
                                                         {1, 1, 1, 0},
                                                         {1, 0, 0, 0}});
        conexp.core.associations.FrequentSetSupportSupplier fsSuppSupplier =
                new conexp.core.associations.ContextFrequentSetSupportSupplier(cxt);

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