package conexp.core.tests;

import conexp.core.ContextFunctions;
import conexp.core.ExtendedContextEditingInterface;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for ContextTest
 */

public class ContextFunctionsTest extends TestCase {
    private static final Class THIS = ContextFunctionsTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testStability() {
        ExtendedContextEditingInterface cxt = SetBuilder.makeContext(new int[][]{{1, 0, 0},
                                                         {0, 1, 1},
                                                         {0, 1, 1},
                                                         {1, 1, 0},
                                                         {1, 1, 1},
                                                         {1, 1, 1}});
        assertEquals(1, ContextFunctions.stability(SetBuilder.makeSet(new int[]{1, 0, 0}),cxt));
        assertEquals(2, ContextFunctions.stability(SetBuilder.makeSet(new int[]{0, 1, 1}), cxt));
        assertEquals(1, ContextFunctions.stability(SetBuilder.makeSet(new int[]{0, 1, 0}), cxt));
        assertEquals(2, ContextFunctions.stability(SetBuilder.makeSet(new int[]{1, 1, 1}), cxt));
    }
}