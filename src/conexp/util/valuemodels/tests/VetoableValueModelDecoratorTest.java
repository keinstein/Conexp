/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.util.valuemodels.tests;



import conexp.util.valuemodels.IntValueModel;
import conexp.util.valuemodels.VetoableValueModelDecorator;
import junit.framework.TestCase;
import util.testing.TestUtil;

public class VetoableValueModelDecoratorTest extends TestCase {

    public static void testEquals() {
        IntValueModel one = new IntValueModel("prop", 2);
        VetoableValueModelDecorator first = new VetoableValueModelDecorator(one);
        VetoableValueModelDecorator second = new VetoableValueModelDecorator(one);
        TestUtil.testEqualsAndHashCode(first, second);

        IntValueModel two = new IntValueModel("prop", 2);
        VetoableValueModelDecorator third = new VetoableValueModelDecorator(two);

        TestUtil.testEqualsAndHashCode(first, third);
    }
}
